FROM openjdk:8-jre

ENV USER jupiter-demo
RUN useradd -m ${USER}

RUN mkdir /app
WORKDIR /app

COPY target/jupiter-demo-*.jar /app/jupiter-demo.jar

USER ${USER}
ENTRYPOINT ["/usr/bin/java","-Duser.timezone=CET", "-cp", "/app/jupiter-demo.jar"]
CMD ["-Dloader.main=com.dasburo.sample.jupiter.demo.DemoApplication", "org.springframework.boot.loader.PropertiesLauncher"]