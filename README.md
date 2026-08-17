# Redisson Distributed Lock Demo

A demo project of how to implement a distributed lock using Redisson

## Prerequisites

* Maven
* Java 17
* Redis

## How to run this project
1. Clone this project on IntelliJ IDEA.
2. Start your local Redis server.
3. Run `\src\main\java\com\example\service1\Service1Application.java` and `\src\main\java\com\example\service2\Service2Application.java` respectively.
4. Open Postman. Send a POST request to `http://localhost:8081/lock` and `http://localhost:8082/lock` respectively.

## Result
### Service1
```
[15:16:11.933802700] Service1 tried to acquire Lock test-lock
[15:16:11.939308600] Lock acquired: test-lock
[15:16:21.940516800] Work done inside the lock.
[15:16:21.953032600] Service1 released lock: test-lock
```

### Service2
```
[15:16:13.615224400] Service2 tried to acquire Lock test-lock
[15:16:21.956033700] Lock acquired: test-lock
[15:16:31.966156] Work done inside the lock.
[15:16:31.973885100] Service2 released lock: test-lock
```

In this example, Service2 tried to acquire Lock `test-lock` while Service1 was possessing it, and it acquired the lock after Service1 released it, which proves that Redisson Distributed Lock works.