FROM eclipse-temurin:17.0.6_10-jre-alpine
WORKDIR /tmp
COPY target/poc-product-service-0.0.1-SNAPSHOT.jar poc-product-service.jar
EXPOSE 8010
CMD [ "java", "-jar",  "poc-product-service.jar"]