# jupiter-demo

Demo project for the Media team to start using Jupiter for testing.

## Core Stack / Project Setup
* java 8
* [spring boot 2.2.3](https://spring.io/)
* [Jupiter - JUnit5](https://junit.org/junit5/docs/current/user-guide/)
* [H2 Im-Memory Database for the integration and unit tests](https://www.tutorialspoint.com/h2_database/index.htm)

## Maven plugin configuration
In order to have two separated test reports, one for the unit tests and the other one for the integration test, the maven-jacoco-plugin looks lik:

```
<plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.2</version>
                <executions>
                    <execution>
                        <id>pre-unit-test</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                        <configuration>
                            <destFile>${project.build.directory}/site/jacoco-ut.exec</destFile>
                        </configuration>
                    </execution>
                    <execution>
                        <id>post-unit-test</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                        <configuration>
                            <dataFile>${project.build.directory}/site/jacoco-ut.exec</dataFile>
                            <outputDirectory>${project.build.directory}/site/jacoco-ut</outputDirectory>
                        </configuration>
                    </execution>
                    <execution>
                        <id>pre-integration-test</id>
                        <phase>pre-integration-test</phase>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                        <configuration>
                            <destFile>${project.build.directory}/site/jacoco-it.exec</destFile>
                        </configuration>
                    </execution>
                    <execution>
                        <id>post-integration-test</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                        <configuration>
                            <dataFile>${project.build.directory}/site/jacoco-it.exec</dataFile>
                            <outputDirectory>${project.build.directory}/site/jacoco-it</outputDirectory>
                        </configuration>
                    </execution>
                    <execution>
                        <id>merge-results</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>merge</goal>
                        </goals>
                        <configuration>
                            <fileSets>
                                <fileSet>
                                    <directory>${project.build.directory}/site</directory>
                                    <includes>
                                        <include>*.exec</include>
                                    </includes>
                                </fileSet>
                            </fileSets>
                            <destFile>${project.build.directory}/site/jacoco.exec</destFile>
                        </configuration>
                    </execution>
                    <execution>
                        <id>post-merge-report</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                        <configuration>
                            <dataFile>${project.build.directory}/site/jacoco.exec</dataFile>
                            <outputDirectory>${project.build.directory}/site/jacoco</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

To execute the integration tests it is also necessary to configure them within the maven-surefire-plugin:
```
            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>com/dasburo/sample/jupiter/demo/integration/**</exclude>
                    </excludes>
                    <skipTests>${skip.surefire.tests}</skipTests>
                </configuration>
            </plugin> 
```

In order to use the jupiter engine to execute the test it is also necessary to configure the maven-surefire-plugin
```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.1</version>
                <configuration>
                    <useSystemClassLoader>false</useSystemClassLoader>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.junit.jupiter</groupId>
                        <artifactId>junit-jupiter-engine</artifactId>
                        <version>${junit.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
```

## Pipeline in gitlab-ci
```
stages:
  - build
  - test
  - java:qa
  - deploy
```

##How to start the application
```
mvn spring-boot:run
```

 