FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/circuit-mock-*.jar app.jar
COPY target/agent/applicationinsights-agent-*.jar agent.jar
ENTRYPOINT ["java", "-javaagent:agent.jar", "-jar", "app.jar"]