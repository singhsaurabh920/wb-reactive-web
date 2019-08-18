package org.wb.reactive.web.domain.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.wb.reactive.web.constant.Action;
import org.wb.reactive.web.event.ProfileEvent;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.repository.ProfileRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.wb.reactive.web.event.ProfileEvent.createProfileEvent;

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

    public Mono<Profile> create(Profile profile) {
        return this.profileRepository
                .save(profile)
                .doOnSuccess(p -> this.publisher.publishEvent(createProfileEvent(Action.CREATE,p)));
    }

    public Mono<Profile> update(String id, Profile newProfile) {
        return this.profileRepository
            .findById(id)
            .map(p -> {
                p.setUpdated(new Date());
                p.setName(newProfile.getName());
                p.setEmail(newProfile.getEmail());
                p.setPhone(newProfile.getPhone());
                p.setWebsite(newProfile.getWebsite());
                p.setCompany(newProfile.getCompany());
                p.setAddress(newProfile.getAddress());
                p.setUsername(newProfile.getUsername());
                p.setPassword(newProfile.getPassword());
                return p;
            }).flatMap(profile-> {
                  return this.profileRepository
                     .save(profile)
                     .doOnSuccess(p -> this.publisher.publishEvent(createProfileEvent(Action.UPDATE,p)));
            });

    }

    public Mono<Profile> delete(String id) {
        return this.profileRepository
            .findById(id)
            .flatMap(profile -> {
                return this.profileRepository
                   .deleteById(profile.getId()).thenReturn(profile)
                   .doOnSuccess(p -> this.publisher.publishEvent(createProfileEvent(Action.DELETE,p)));
            });
    }
}