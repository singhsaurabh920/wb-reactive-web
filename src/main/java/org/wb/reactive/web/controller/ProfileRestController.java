package org.wb.reactive.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.service.ProfileService;
import org.wb.reactive.web.event.ProfileAction;
import org.wb.reactive.web.event.ProfileEvent;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/profiles")
@org.springframework.context.annotation.Profile("!functional")
public class ProfileRestController {
    private static final Logger logger = LogManager.getLogger(ProfileRestController.class);
    private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    private final ProfileService profileService;
    private final Flux<ProfileEvent> profileCreatedEvents;

    ProfileRestController( ProfileService profileService, Flux<ProfileEvent> profileCreatedEvents) {
        this.profileService = profileService;
        this.profileCreatedEvents = profileCreatedEvents;
    }

    @RequestMapping(value = "/event" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ProfileAction> event() {
        return this.profileCreatedEvents
                .flatMap(pce -> {
                    logger.info(pce.getSource());
                   return Flux.create(e->{
                      e.next((ProfileAction)pce.getSource());
                   });
                });
    }

    @RequestMapping
    Flux<Profile> getAll() {
        return this.profileService.all();
    }

    @GetMapping("/{id}")
    Publisher<ResponseEntity<Profile>> find(@PathVariable("id") String id) {
        return this.profileService.get(id)
                .map(p -> ResponseEntity.ok().contentType(mediaType).body(p));
    }

    @DeleteMapping("/{id}")
    Publisher<ResponseEntity<Profile>> delete(@PathVariable String id) {
        return this.profileService.delete(id)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).build());
    }

    @PostMapping
    Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        return this.profileService.create(profile)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).body(p));
    }

    @PutMapping("/{id}")
    Publisher<ResponseEntity<Profile>> update(@PathVariable String id, @RequestBody Profile profile) {
        return this.profileService.update(id, profile)
                .map(p -> ResponseEntity.ok().contentType(this.mediaType).body(p));
    }
}
