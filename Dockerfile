FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 as builder
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN sbt clean assembly


FROM openjdk:8-jre-alpine as runtime
COPY --from=builder /app/source/target/scala-2.13/hello-world-assembly-1.0.jar /app/app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/app/app.jar"]