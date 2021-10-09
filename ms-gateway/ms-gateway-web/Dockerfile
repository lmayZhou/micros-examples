FROM java:8

MAINTAINER lmay Zhou <lmay@lmaye.com>
VOLUME /tmp
COPY ./build/libs/ms-gateway-web-1.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/ms-gateway-web-1.0.1-SNAPSHOT.jar"]

EXPOSE 80
