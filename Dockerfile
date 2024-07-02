FROM openjdk:17-jdk
ADD /target/bank-info-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java","-jar", "backend.jar"]