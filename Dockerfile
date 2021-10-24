FROM openjdk:8
ADD target/BookManagement.jar BookManagement.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","BookManagement.jar"]