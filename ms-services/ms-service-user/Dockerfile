FROM java:8

MAINTAINER lmay Zhou <lmay@lmaye.com>
VOLUME /tmp
COPY ./target/ms-service-user.jar /app/
ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/ms-service-user.jar"]

EXPOSE 8003
