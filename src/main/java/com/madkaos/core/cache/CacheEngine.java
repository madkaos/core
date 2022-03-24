package com.madkaos.core.cache;

import redis.clients.jedis.Jedis;

public class CacheEngine {
    private Jedis client;
    
    public CacheEngine(String redisURI) {
        this.client = new Jedis(redisURI);
    }

    String get(String player, String key) {
        return this.client.get("mk_" + player + "_" + key);
    }

    void set(String player, String key, int expiration, String value) {
        this.client.setex("mk_" + player + "_" + key, expiration, value);
    }

    public String getReplyTo(String player) {
        return this.get(player, "reply_to");
    }

    public void setReplyTo(String player, String replyTo) {
        this.set(player, "reply_to", 600, replyTo);
    }

    public void stop() {
        this.client.close();
    }
}
