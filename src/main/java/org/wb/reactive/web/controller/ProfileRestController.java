package org.wb.reactive.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.service.ProfileService;
import org.wb.reactive.web.event.ProfileCreatedEvent;
import org.wb.reactive.web.event.ProfileCreatedEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/profiles")
@org.springframework.context.annotation.Profile("!functional")
public class ProfileRestController {
    private static final Logger logger = LogManager.getLogger(ProfileRestController.class);
    private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    private final ObjectMapper objectMapper;
    private final ProfileService profileService;
    private final Flux<ProfileCreatedEvent> profileCreatedEvents;

    ProfileRestController( ObjectMapper objectMapper,ProfileService profileService, Flux<ProfileCreatedEvent> profileCreatedEvents) {
        this.objectMapper = objectMapper;
        this.profileService = profileService;
        this.profileCreatedEvents = profileCreatedEvents;
    }

    @RequestMapping(value = "/event" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> event() {
        return this.profileCreatedEvents.map(pce -> {
            try {
                logger.info("Sending Sse:-"+pce.getSource());
                return objectMapper.writeValueAsString(pce.getSource());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @RequestMapping
    Flux<Profile> getAll() {
        return this.profileService.all();
    }

    @PostMapping
    Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        logger.info("Creating profile:-"+profile);
        return this.profileService.create(profile)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());

    }

    @GetMapping("/{id}")
    Publisher<ResponseEntity<Profile>> find(@PathVariable("id") String id) {
        return this.profileService.get(id)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Profile>> delete(@PathVariable String id) {
        return this.profileService.delete(id)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<Profile>> update(@PathVariable String id, @RequestBody Profile profile) {
        return Mono.just(profile)
                .flatMap(p -> this.profileService.update(id, p.getEmail()))
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }
}
