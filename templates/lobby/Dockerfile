FROM eclipse-temurin:25-jre
WORKDIR /server
COPY . .
EXPOSE 25565
CMD ["java", "-jar", "-Xmx4G", "-Xms4G", "server.jar", "nogui"]