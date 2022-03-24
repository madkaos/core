package com.madkaos.core.messaging;

import com.google.gson.Gson;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.messaging.packets.FriendAcceptedPacket;
import com.madkaos.core.messaging.packets.FriendRequestPacket;
import com.madkaos.core.messaging.packets.MessagePacket;
import com.madkaos.core.messaging.packets.PlayerPunishPacket;
import com.madkaos.core.messaging.packets.PlayerRefreshPacket;
import com.madkaos.core.messaging.packets.ReportPacket;

import org.bukkit.scheduler.BukkitRunnable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MessageBroker {
    private MadKaosCore plugin;
    private MessageProcessor processor;
    private Gson gson;

    private Jedis publisher;
    private Jedis hooker;
    private JedisPubSub pubsub;

    public MessageBroker(MadKaosCore plugin, String redisURI) {
        this.plugin = plugin;
        this.gson = new Gson();
        this.processor = new MessageProcessor(plugin);

        this.hooker = new Jedis(redisURI);
        this.publisher = new Jedis(redisURI);
    }

    public MessageBroker start() {        
        this.pubsub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                processMessage(channel, message);
            }
            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                plugin.getLogger().info("Message Broker suscribed to " + channel + " channel");
            }
        };

        new Thread(new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    hooker.subscribe(pubsub, 
                        Channel.FRIEND_ACCEPTED_CHANNEL,
                        Channel.FRIEND_REQUEST_CHANNEL,
                        Channel.MESSAGE_CHANNEL,
                        Channel.PLAYER_REFRESH_CHANNEL,
                        Channel.REPORT_CHANNEL,
                        Channel.PUNISHMENT_CHANNEL
                    );
                } catch (Exception _ignored) {}
            }
        }).start();

        return this;
    }

    public void stop() {
        this.pubsub.unsubscribe(
            Channel.FRIEND_ACCEPTED_CHANNEL,
            Channel.FRIEND_REQUEST_CHANNEL,
            Channel.MESSAGE_CHANNEL,
            Channel.PLAYER_REFRESH_CHANNEL,
            Channel.REPORT_CHANNEL,
            Channel.PUNISHMENT_CHANNEL
        );
        this.publisher.disconnect();
        this.hooker.disconnect();
    }

    private void processMessage(String channel, String message) {
        switch(channel) {
            case Channel.FRIEND_ACCEPTED_CHANNEL:
                this.processor.process(gson.fromJson(message, FriendAcceptedPacket.class));
                break;
            case Channel.FRIEND_REQUEST_CHANNEL:
                this.processor.process(gson.fromJson(message, FriendRequestPacket.class));
                break;
            case Channel.MESSAGE_CHANNEL:
                this.processor.process(gson.fromJson(message, MessagePacket.class));
                break;
            case Channel.PLAYER_REFRESH_CHANNEL:
                this.processor.process(gson.fromJson(message, PlayerRefreshPacket.class));
                break;
            case Channel.REPORT_CHANNEL:
                this.processor.process(gson.fromJson(message, ReportPacket.class));
                break;
            case Channel.PUNISHMENT_CHANNEL:
                this.processor.process(gson.fromJson(message, PlayerPunishPacket.class));
                break;
        }
    }

    public void publish(IPacket packet) {
        this.publisher.publish(packet.getChannel(), gson.toJson(packet));
    }
}
