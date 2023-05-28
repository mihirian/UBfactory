package com.example.ubfactory.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class RedisService
{
    //@Autowired
    private  JedisPool jedisPool;
    private  ObjectMapper objectMapper;
    private final String lockKeyPrefix = "STOCK_LOCK_";
    private final String minutePattern = "yyyy-MM-dd-HH-mm";
    public static final int MILLISECONDS_TO_SLEEP_FOR_LOCK_RETRY = 1000;
   // RedisService() {
   //     this.objectMapper = new ObjectMapper();
   // }
    public RedisService()
    {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            // Default : 8, consider how many concurrent connections into Redis you will need under load
            poolConfig.setMaxTotal(128);

            this.jedisPool = new JedisPool(poolConfig, "localhost", 6379);
            this.objectMapper = new ObjectMapper();
        } catch (Throwable throwable) {
            //throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a new key-value pair into the Memorystore Redis cache
    public void populateCache(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    // Add a new key-value pair into the Memorystore Redis cache with a TTL expiry
    public void populateCache(String key, String value, int ttlInSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, ttlInSeconds, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    // Add an Object as serialised JSON into the Memorystore Redis cache
    public void populateCache(String key, Object obj) {
        try (Jedis jedis = jedisPool.getResource()) {
            String objectAsString = objectMapper.writeValueAsString(obj);
            jedis.set(key, objectAsString);
        } catch (Throwable e) {
            //throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add an Object as serialised JSON into the Memorystore Redis cache with a TTL expiry
    public void populateCache(String key, Object obj, int ttlInSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            objectMapper.registerModule(new JavaTimeModule());
            String objectAsString = objectMapper.writeValueAsString(obj);
            jedis.setex(key, ttlInSeconds, objectAsString);
        } catch (Throwable e) {
            //throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve the value for a given key from the Memorystore Redis cache
    public String lookupCache(String key) {
        objectMapper.registerModule(new JavaTimeModule());
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Throwable e) {
            throw e;
        }
    }

    // Retrieve and deserialise an Object for a given key from the Memorystore Redis cache
    public <T> T lookupCache(String key, Class<T> obj) {
        String objectAsString = "";
        objectMapper.registerModule(new JavaTimeModule());
        try (Jedis jedis = jedisPool.getResource()) {
            objectAsString = jedis.get(key);
            return objectMapper.readValue(objectAsString, obj);
        } catch (Throwable e) {
            //throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    public String getLockKeyName(String key) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(minutePattern);
        String timestampInMinutes = simpleDateFormat.format(new Date());
        return lockKeyPrefix.concat(key).concat("_").concat(timestampInMinutes);
    }

    public boolean createLock(String lockKey) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setnx(lockKey, "") == 1;   // 1 is if setnx is able to create key
        } catch (Throwable e) {
            // throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
    }

    public boolean createLock(String lockKey, final int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            var resp = jedis.setnx(lockKey, "") == 1;   // 1 is if setnx is able to create key
            jedis.expire(lockKey, seconds);
            return resp;
        } catch (Throwable e) {
            // throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
    }

    // Retrieve and deserialise list of Objects for a given key from the Memorystore Redis cache if exists
    public <T> List<T> lookupListCache(String key, Class<T> elementClass) {
        String objectAsString = "";
        try (Jedis jedis = jedisPool.getResource()) {
            objectAsString = jedis.get(key);
            if (objectAsString == null) {
                return null;
            }
            return jsonArrayToList(objectAsString, elementClass);
        } catch (Throwable e) {
            //throw new ApplicationException(ResponseCode.MEMORYSTORE_REDIS_CACHE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    public <T> List<T> jsonArrayToList(String json, Class<T> elementClass) throws JsonProcessingException {
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return objectMapper.readValue(json, listType);
    }

    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        } catch (Throwable e) {
            throw e;
        }
    }

    public boolean delete(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            var res = jedis.del(key);
            return res != null && res == 1;
        } catch (Throwable e) {
            throw e;
        }
    }

    public long deleteKeysByPattern(String pattern) {
        Set<String> matchingKeys = new HashSet<>();
        ScanParams params = new ScanParams();
        params.match(pattern + "*");
        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";
            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();
                nextCursor = scanResult.getCursor();
                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));
            if (matchingKeys.size() == 0) {
                return 0;
            }
            var res = jedis.del(matchingKeys.toArray(new String[matchingKeys.size()]));
            return res;
        } catch (Throwable e) {
            throw e;
        }
    }

    public String getFromCache(String key)
    {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Throwable e) {
            throw e;
        }
    }

   /* public static Object getObjectFromCache(String key, Class<?> objectType) throws JsonProcessingException {
        try (Jedis jedis = jedisPool.getResource()) {
            String objectAsString = jedis.get(key);
            if (objectAsString != null) {
                return objectMapper.readValue(objectAsString, objectType);
            }
        } catch (Throwable e) {
            System.out.println("Error reading from Redis cache: " + e.getMessage());
            throw e;
        }
        return null;
    }

    */

    private String serialize(Object value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }

    private <T> T deserialize(String value, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value, clazz);
    }


}

