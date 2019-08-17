package org.wb.reactive.web.domain.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.wb.reactive.web.event.ProfileCreatedEvent;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.repository.ProfileRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service("profileService")
public class ProfileService {

    private final ApplicationEventPublisher publisher;
    private final ProfileRepository profileRepository;

    ProfileService(ApplicationEventPublisher publisher, ProfileRepository profileRepository) {
        this.publisher = publisher;
        this.profileRepository = profileRepository;
    }

    public Flux<Profile> all() {
        return this.profileRepository.findAll();
    }

    public Mono<Profile> get(String id) {
        return this.profileRepository.findById(id);
    }

    public Mono<Profile> update(String id, String email) {
        return this.profileRepository
            .findById(id)
            .map(p -> {
                p.setEmail(email);
                return p;
            }).flatMap(this.profileRepository::save);
    }

    public Mono<Profile> delete(String id) {
        return this.profileRepository
            .findById(id)
            .flatMap(p -> this.profileRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Profile> create(Profile profile) {
        return this.profileRepository
            .save(profile)
            .doOnSuccess(p -> this.publisher.publishEvent(new ProfileCreatedEvent(p)));
    }
}