package com.skillbox.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;


import java.util.Date;


import static java.lang.System.out;

public class RedisStorage {

    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";
    private boolean autoInit;

    public RedisStorage() {
    }

    public RedisStorage(boolean autoInit) {
        if (autoInit) {
            this.init();
        }
    }

    public void init() {

        if (!autoInit) {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:6379");
            try {
                redisson = Redisson.create(config);
            } catch (RedisConnectionException Exc) {
                out.println("Не удалось подключиться к Redis");
                out.println(Exc.getMessage());
            }
            rKeys = redisson.getKeys();
            onlineUsers = redisson.getScoredSortedSet(KEY);
            rKeys.delete(KEY);
            autoInit = true;
        } else {
            out.println("Конфигурация подключения уже инициализированна");
        }
    }

    void shutdown() {
        redisson.shutdown();
    }

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    void loginPageVisit(int user_id) {
        //ZADD ONLINE_USERS
        onlineUsers.add(getTs(), String.valueOf(user_id));
    }


    // Удаляет
    void deleteOldEntries(int secondsAgo) {
        //ZREVRANGEBYSCORE ONLINE_USERS 0 <time_5_seconds_ago>
        onlineUsers.removeRangeByScore(0, true, getTs() - secondsAgo, true);
    }

    int calculateUsersNumber() {
        //ZCOUNT ONLINE_USERS
        return onlineUsers.count(Double.NEGATIVE_INFINITY, true, Double.POSITIVE_INFINITY, true);
    }
}
