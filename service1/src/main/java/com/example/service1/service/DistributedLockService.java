package com.example.service1.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DistributedLockService {

    private final RedissonClient redissonClient;

    public void doWorkWithLock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        boolean isLocked = false;

        try {
            // Try to get the lock (wait up to 5 seconds, auto-release after 10 seconds)
            isLocked = lock.tryLock(2, 10, TimeUnit.SECONDS);
            if (isLocked) {
                System.out.println("Lock acquired: " + lockName);

                // Critical section (e.g., updating a shared resource)
                Thread.sleep(10000);
                System.out.println("Work done inside lock.");

            } else {
                System.out.println("Could not acquire lock: " + lockName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock released: " + lockName);
            }
        }
    }
}