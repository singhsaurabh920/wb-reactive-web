package org.wb.reactive.web.endpoint.handler;

import com.mongodb.Mongo;
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

    public Mono<ServerResponse> getById(ServerRequest request) {
        return defaultReadResponse(this.profileService.get(id(request)));
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse(this.profileService.all());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse(this.profileService.delete(id(request)));
    }

    public  Mono<ServerResponse> updateById(ServerRequest request) {
        Mono<Profile> id = request
                .bodyToMono(Profile.class)
                .flatMap(p -> this.profileService.update(id(request), p));
        return defaultWriteResponse(id);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Profile> mono = request
                .bodyToMono(Profile.class)
                .flatMap(p -> this.profileService.create(p));
        return defaultWriteResponse(mono);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Profile> profile) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(profile, Profile.class);
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Profile> profile) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(profile, Profile.class);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }
}
