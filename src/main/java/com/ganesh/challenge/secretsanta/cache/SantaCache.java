package com.ganesh.challenge.secretsanta.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Cache that holds the computed secret santa for the entire family.
 *
 * @Date    18/10/2019
 * @author  ganesh mohan
 */
public final class SantaCache {

    /**
     * private constructor to restrict new instances
     */
    private SantaCache(){
    }

    // final instance of the in memory map based cache
    private final static Map<String, String> SANTA_CACHE = new HashMap<String, String>();
    private final static Logger logger = LoggerFactory.getLogger(SantaCache.class);


    public static String getMemberForSanta(String name){
        return SANTA_CACHE.get(name);
    }

    public static void putSantaAndMember(String santaName, String memberName){
         SANTA_CACHE.put(santaName,memberName);
         if(logger.isDebugEnabled()){
             logger.debug("Values saved in cache {} : {}",santaName, memberName);
         }
    }



}
