package org.wb.reactive.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.service.ProfileService;
import org.wb.reactive.web.endpoint.config.ProfileEndpointConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@org.springframework.context.annotation.Profile("!functional")
public class ProfileRestController {
    private static final Logger logger = LogManager.getLogger(ProfileRestController.class);

    private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    private final ProfileService profileService;

    ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping
    Flux<Profile> getAll() {
        return this.profileService.all();
    }

    @PostMapping
    Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        return this.profileService.create(profile)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());

    }

    @GetMapping("/{id}")
    Publisher<ResponseEntity<Profile>> find(@PathVariable("id") String id) {
        return this.profileService.get(id)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }

    @DeleteMapping("/{id}")
    Publisher<ResponseEntity<Profile>> delete(@PathVariable String id) {
        return this.profileService.delete(id)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }

    @PutMapping("/{id}")
    Publisher<ResponseEntity<Profile>> update(@PathVariable String id, @RequestBody Profile profile) {
        return Mono.just(profile)
                .flatMap(p -> this.profileService.update(id, p.getEmail()))
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }
}
