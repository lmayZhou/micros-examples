FROM java:8

MAINTAINER lmay Zhou <lmay@lmaye.com>
VOLUME /tmp
COPY ./target/ms-gateway-web.jar /app/
ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/ms-gateway-web.jar"]

EXPOSE 80
