package com.gy.springbootinit.manager;

import com.gy.springbootinit.common.ErrorCode;
import com.gy.springbootinit.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 专门提供 RedisLimiter 限流基础服务的（提供了通用的能力,放其他项目都能用）
 */
@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     *
     * @param key 区分不同的限流器，比如不同的用户 id 应该分别统计
     */
    public void doRateLimit(String key) {
        // 创建一个名称为user_limiter的限流器，每秒最多访问 2 次
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        /**
         * RateType.OVERALL: 这意味着整个令牌桶（Token Bucket）的速率限制。在这种情况下，它限制了所有请求的速率，而不是单个用户的请求速率。
         * 2: 每秒允许2个请求。
         * 1: 允许的突发大小（Burst Size）。这表示，如果连续请求，最多只有1个请求可以立即被允许通过，即使之前的请求还没有完全通过。
         * RateIntervalUnit.SECONDS: 时间单位是秒。
         */
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        // 每当一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        // 如果没有令牌,还想执行操作,就抛出异常
        if (!canOp) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
    }
}