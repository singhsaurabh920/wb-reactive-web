package org.wb.reactive.web.endpoint.handler;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.wb.reactive.web.domain.enity.Profile;
import org.wb.reactive.web.domain.service.ProfileService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component("profileHandler")
public class ProfileHandler {

    private final ProfileService profileService;

    ProfileHandler(ProfileService profileService) {
        this.profileService = profileService;
    }

    public Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.profileService.get(id(r)));
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse(this.profileService.all());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse(this.profileService.delete(id(request)));
    }

    public  Mono<ServerResponse> updateById(ServerRequest request) {
        Flux<Profile> id = request
                .bodyToFlux(Profile.class)
                .flatMap(p -> this.profileService.update(id(request), p.getEmail()));
        return defaultReadResponse(id);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Profile> flux = request
                .bodyToFlux(Profile.class)
                .flatMap(p -> this.profileService.create(p.getEmail()));
        return defaultWriteResponse(flux);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Profile> profiles) {
        return Mono.from(profiles)
                .flatMap(p -> ServerResponse
                .created(URI.create("/profiles/" + p.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build());
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Profile> profiles) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(profiles, Profile.class);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }
}
