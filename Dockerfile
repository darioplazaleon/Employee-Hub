FROM amazoncorretto:22

COPY target/employes-sytem-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]