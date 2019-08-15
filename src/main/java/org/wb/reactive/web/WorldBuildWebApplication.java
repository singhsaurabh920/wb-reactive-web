package org.wb.reactive.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = {"org.wb.reactive.web.domain.repository"})
public class WorldBuildWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldBuildWebApplication.class, args);
    }
}
