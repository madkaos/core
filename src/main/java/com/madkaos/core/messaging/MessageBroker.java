package com.madkaos.core.messaging;

import com.google.gson.Gson;
import com.madkaos.core.MadKaosCore;
import com.madkaos.core.messaging.packets.FriendAcceptedPacket;
import com.madkaos.core.messaging.packets.FriendRequestPacket;
import com.madkaos.core.messaging.packets.MessagePacket;

import org.bukkit.scheduler.BukkitRunnable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MessageBroker {
    private MessageProcessor processor;
    private Gson gson;

    private Jedis publisher;
    private Jedis hooker;

    public MessageBroker(MadKaosCore plugin, String redisURI) {
        this.gson = new Gson();
        this.processor = new MessageProcessor(plugin);

        this.hooker = new Jedis(redisURI);
        this.publisher = new Jedis(redisURI);

        new Thread(new BukkitRunnable() {
            @Override
            public void run() {
                hooker.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        processPubsubMessage(channel, message);
                    }
                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        plugin.getLogger().info("Message Broker suscribed to " + channel + " channel");
                    }
                }, 
                    Channel.MESSAGE_CHANNEL,
                    Channel.FRIEND_REQUEST_CHANNEL,
                    Channel.FRIEND_ACCEPTED_CHANNEL
                );
            }
        }).start();
    }

    public void stop() {
        this.publisher.close();
        this.hooker.close();
    }

    private void processPubsubMessage(String channel, String message) {
        // Process message to player packet
        if (channel.equals(Channel.MESSAGE_CHANNEL)) {
            this.processor.processMessagePacket(
                gson.fromJson(message, MessagePacket.class)
            );
        }
        
        else if (channel.equals(Channel.FRIEND_REQUEST_CHANNEL)) {
            this.processor.processFriendRequestPacket(
                gson.fromJson(message, FriendRequestPacket.class)
            );
        }

        else if (channel.equals(Channel.FRIEND_ACCEPTED_CHANNEL)) {
            this.processor.processFriendAcceptedPacket(
                gson.fromJson(message, FriendAcceptedPacket.class)
            );
        }
    }

    private void publishPacket(String channel, String rawPacket) {
        this.publisher.publish(channel, rawPacket);
    }

    public void publishMessagePacket(MessagePacket packet) {
        this.publishPacket(Channel.MESSAGE_CHANNEL, gson.toJson(packet));
    }

    public void publishFriendRequestPacket(FriendRequestPacket packet) {
        this.publishPacket(Channel.FRIEND_REQUEST_CHANNEL, gson.toJson(packet));
    }

    public void publishFriendAcceptPacket(FriendAcceptedPacket packet) {
        this.publishPacket(Channel.FRIEND_ACCEPTED_CHANNEL, gson.toJson(packet));
    }
}
