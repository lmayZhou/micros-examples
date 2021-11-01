FROM mysql:8.0.16

MAINTAINER lmay Zhou <lmay@lmaye.com>

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./ms.sql /docker-entrypoint-initdb.d
COPY ./nacos_db.sql /docker-entrypoint-initdb.d

