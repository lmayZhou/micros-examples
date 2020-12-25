package com.lmaye.ms.starter.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * -- Redis Auto Configuration
 *
 * @author lmay.Zhou
 * @date 2020/9/23 17:10 星期三
 * @since Email: lmay_zlm@meten.com
 */
@Configuration
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class RedisAutoConfiguration {
    /**
     * Redis Properties
     */
    private final RedisProperties redisProperties;

    public RedisAutoConfiguration(final RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    /**
     * Redisson 客户端
     *
     * @return RedissonClient
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        long timeout = 3000;
        if (!Objects.isNull(redisProperties.getTimeout())) {
            timeout = redisProperties.getTimeout().toMillis();
        }
        if (!Objects.isNull(redisProperties.getSentinel())) {
            List<String> nodes = redisProperties.getSentinel().getNodes();
            config.useSentinelServers().setMasterName(redisProperties.getSentinel().getMaster())
                    .setPingConnectionInterval(1000).addSentinelAddress(convert(nodes))
                    .setDatabase(redisProperties.getDatabase()).setConnectTimeout((int) timeout)
                    .setPassword(redisProperties.getPassword());
        } else if (!Objects.isNull(redisProperties.getCluster())) {
            List<String> nodes = redisProperties.getCluster().getNodes();
            config.useClusterServers().addNodeAddress(convert(nodes)).setPingConnectionInterval(1000)
                    .setConnectTimeout((int) timeout).setPassword(redisProperties.getPassword());
        } else {
            String prefix = "redis://";
            if (redisProperties.isSsl()) {
                prefix = "rediss://";
            }
            config.useSingleServer().setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout((int) timeout).setDatabase(redisProperties.getDatabase())
                    .setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 转换
     *
     * @param nodeList 节点
     * @return String[]
     */
    private String[] convert(List<String> nodeList) {
        List<String> nodes = new ArrayList<>(nodeList.size());
        for (String node : nodeList) {
            if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
                nodes.add("redis://" + node);
            } else {
                nodes.add(node);
            }
        }
        return nodes.toArray(new String[0]);
    }
}