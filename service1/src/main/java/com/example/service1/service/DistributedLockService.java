package com.example.service1.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DistributedLockService {

    private final RedissonClient redissonClient;

    public void doWorkWithLock(String lockName) {
        RLock rLock = redissonClient.getLock(lockName);
        boolean isLocked = false;

        try {
            // Try to get the lock (wait up to 15 seconds, auto-release after 10 seconds)
            System.out.println("[" + LocalTime.now() + "] " + "Service1 tried to acquire Lock " + lockName);
            isLocked = rLock.tryLock(10, 8, TimeUnit.SECONDS);
            if (isLocked) {
                System.out.println("[" + LocalTime.now() + "] " + "Lock acquired: " + lockName);

                // Critical section (e.g., updating a shared resource)
                Thread.sleep(5000);
                System.out.println("[" + LocalTime.now() + "] " + "Work done inside the lock.");

            } else {
                System.out.println("[" + LocalTime.now() + "] " + "Could not acquire lock: " + lockName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (isLocked && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                System.out.println("[" + LocalTime.now() + "] " + "Service1 released lock: " + lockName);
            }
        }
    }
}