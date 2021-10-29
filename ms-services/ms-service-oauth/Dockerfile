FROM java:8

MAINTAINER lmay Zhou <lmay@lmaye.com>
VOLUME /tmp
COPY ./target/ms-service-oauth.jar /app/
ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/ms-service-oauth.jar"]

EXPOSE 8002
