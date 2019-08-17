package org.wb.reactive.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.repository.ProfileRepository;
import org.wb.reactive.web.utils.RandomNumberUtils;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.Random;

@Log4j2
@Component
@org.springframework.context.annotation.Profile("Sample")
class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final ProfileRepository repository;

    private final String URl="https://jsonplaceholder.typicode.com/users";

    private  WebClient webClient = WebClient.create(URl);

    public SampleDataInitializer(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
       repository
            .deleteAll()
               .thenMany(webClient.get().retrieve().bodyToFlux(Profile.class))
               .map(profile -> {
                   profile.setId(null);
                   profile.setUsername(profile.getUsername().toLowerCase());
                   profile.setPassword(profile.getUsername().toLowerCase());
                   profile.setPhone("99"+RandomNumberUtils.generateRandomBetween(10000000,99999999));
                   profile.getAddress().setCountry("USA");
                   profile.getAddress().setState("California");
                   profile.setAdded(new Date());
                   profile.setUpdated(new Date());
                   return profile;
               })
               .flatMap(profile -> repository.save(profile))
               .subscribe(profile -> log.info("Saving " + profile.toString()));
    }
}