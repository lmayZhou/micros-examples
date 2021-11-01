FROM java:8

MAINTAINER lmay Zhou <lmay@lmaye.com>
VOLUME /tmp
COPY ./target/ms-service-file.jar /app/
ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/ms-service-file.jar"]

EXPOSE 8001
