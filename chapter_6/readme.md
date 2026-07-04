### 一.redis 单节点 安装

docker run -d --name my-redis -p 6379:6379 redis
![img.png](images/mongo.png)

### 二.redis cluster 集群搭建

| VM  | IP              | 节点                        |
|-----|-----------------|-----------------------------|
| VM1 | 192.168.239.136 | master1 (6379), replica3 (6382) |
| VM2 | 192.168.239.137 | master2 (6380), replica1 (6383) |
| VM3 | 192.168.239.138 | master3 (6381), replica2 (6384) |

#### (1). 在每台机器上准备配置文件 以 VM1 为例，创建两个节点的目录和配置：
vm1:
``` 
mkdir -p /data/redis/6379/{conf,data}
mkdir -p /data/redis/6382/{conf,data}
在/data/redis/6379/conf/redis.conf上写入：
pport 6379
bind 0.0.0.0
protected-mode no
daemonize no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.239.136
cluster-announce-port 6379
cluster-announce-bus-port 16379
appendonly yes
dir /data

requirepass public_456
masterauth public_456

在/data/redis/6382/conf/redis.conf上写入
port 6382
bind 0.0.0.0
protected-mode no
daemonize no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.239.136
cluster-announce-port 6382
cluster-announce-bus-port 16382
appendonly yes
dir /data
requirepass public_456
masterauth public_456

```
vm2:
```
 mkdir -p /data/redis/6380/{conf,data}
 mkdir -p /data/redis/6383/{conf,data}
 
在/data/redis/6380/conf/redis.conf上写入：
port 6380
bind 0.0.0.0
protected-mode no
daemonize no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.239.137
cluster-announce-port 6380
cluster-announce-bus-port 16380
appendonly yes
dir /data

requirepass public_456
masterauth public_456

在/data/redis/6383/conf/redis.conf上写入
port 6383
bind 0.0.0.0
protected-mode no
daemonize no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.239.137
cluster-announce-port 6383
cluster-announce-bus-port 16383
appendonly yes
dir /data

requirepass public_456
masterauth public_456
```
vm3:
```
mkdir -p /data/redis/6381/{conf,data}
 mkdir -p /data/redis/6384/{conf,data}
 
 在/data/redis/6381/conf/redis.conf上写入
 port 6381
bind 0.0.0.0
protected-mode no
daemonize no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.239.138
cluster-announce-port 6381
cluster-announce-bus-port 16381
appendonly yes
dir /data

requirepass public_456
masterauth public_456

在/data/redis/6384/conf/redis.conf上写入
 port 6384
bind 0.0.0.0
protected-mode no
daemonize no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.239.138
cluster-announce-port 6384
cluster-announce-bus-port 16384
appendonly yes
dir /data

requirepass public_456
masterauth public_456
```

#### (2)启动容器
vm1
```
systemctl stop firewalld
docker run -d --name redis-6379 \
  --network host \
  --restart always \
  -v /data/redis/6379/conf/redis.conf:/etc/redis/redis.conf \
  -v /data/redis/6379/data:/data \
  redis:7.2 redis-server /etc/redis/redis.conf

docker run -d --name redis-6382 \
  --network host \
  --restart always \
  -v /data/redis/6382/conf/redis.conf:/etc/redis/redis.conf \
  -v /data/redis/6382/data:/data \
  redis:7.2 redis-server /etc/redis/redis.conf
```
vm2
```
systemctl stop firewalld

docker run -d --name redis-6380 \
  --network host \
  --restart always \
  -v /data/redis/6380/conf/redis.conf:/etc/redis/redis.conf \
  -v /data/redis/6380/data:/data \
  redis:7.2 redis-server /etc/redis/redis.conf

docker run -d --name redis-6383 \
  --network host \
  --restart always \
  -v /data/redis/6383/conf/redis.conf:/etc/redis/redis.conf \
  -v /data/redis/6383/data:/data \
  redis:7.2 redis-server /etc/redis/redis.conf
```
vm3
```
systemctl stop firewalld

docker run -d --name redis-6381 \
  --network host \
  --restart always \
  -v /data/redis/6381/conf/redis.conf:/etc/redis/redis.conf \
  -v /data/redis/6381/data:/data \
  redis:7.2 redis-server /etc/redis/redis.conf

docker run -d --name redis-6384 \
  --network host \
  --restart always \
  -v /data/redis/6384/conf/redis.conf:/etc/redis/redis.conf \
  -v /data/redis/6384/data:/data \
  redis:7.2 redis-server /etc/redis/redis.conf
```
#### (3)创建集群
```
docker exec -it redis-6379 redis-cli -a public_456 --no-auth-warning --cluster create \
  192.168.239.136:6379 \
  192.168.239.137:6380 \
  192.168.239.138:6381 \
  192.168.239.137:6383 \
  192.168.239.138:6384 \
  192.168.239.136:6382 \
  --cluster-replicas 1
```

#### (4)验证
```
docker exec -it redis-6379 redis-cli -a public_456 --no-auth-warning -c -p 6379 cluster info
docker exec -it redis-6379 redis-cli -a public_456 --no-auth-warning -c -p 6379 cluster nodes
```
如果出现cluster_state: ok则表示创建成功
![](images/redis_cluster.png)

### 三，springboot接入redis cluster集群
接入代码详见 redis_cluster_demo模块
运行执行结果
![](images/2.png)


### 四，mongodb接入

docker run -d --name my-mongodb -p 27017:27017 \
-e MONGO_INITDB_ROOT_USERNAME=admin \
-e MONGO_INITDB_ROOT_PASSWORD=public_456 \
-v mongo-data:/data/db \
mongo:7![img.png](images/mongo.png)


### 五，使用redis进行session共享

代码在redis_cluster_demo模块，
1.设置idea
![](images/idea多开.png)
2.先配置application.ymal
server:
port: 8080
启动程序
3.修改application.ymal
server:
port: 8081
启动程序

4.部署nginx

mkdir -p ~/nginx/{conf,conf.d,html,logs}
修改宿主机的~/nginx/conf/nginx.conf
```
user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    tcp_nopush      on;
    tcp_nodelay     on;
    keepalive_timeout  65;
    gzip  on;

    # ------------------- 负载均衡上游服务器组 -------------------
    upstream test.com {
        server 192.168.66.130:8080 weight=1;
        server 192.168.66.130:8081 weight=1;
    }

    # ------------------- 反向代理 server 配置 -------------------
    server {
        listen       80;
        server_name  localhost;

        location / {
            proxy_pass http://test.com;
            proxy_redirect default;

            # 建议补充的代理请求头,避免后端拿不到真实客户端信息
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;

            # 超时时间(可根据后端服务情况调整)
            proxy_connect_timeout 30s;
            proxy_send_timeout    60s;
            proxy_read_timeout    60s;
        }

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   /usr/share/nginx/html;
        }
    }
}
```
docker run -d \
--name my-nginx \
-p 8080:80 \
-v ~/nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro \
-v ~/nginx/html:/usr/share/nginx/html:ro \
-v ~/nginx/logs:/var/log/nginx \
--restart=always \
nginx

session请求
![](images/set.png)
首次访问,被nginx反向代理到了192.168.66.130:8081
![](images/first1.png)
第二次访问反向代理到了 192.168.66.130:8081
![](images/second.png)
实现了 session共享