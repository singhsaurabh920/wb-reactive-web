package org.wb.reactive.web.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.wb.reactive.web.event.ProfileEvent;
import org.wb.reactive.web.event.ProfileCreatedEventPublisher;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Log4j2
@Configuration
class WebSocketConfiguration {

    @Bean
    Executor executor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    Flux<ProfileEvent>  profileCreatedEvents(ProfileCreatedEventPublisher profileCreatedEventPublisher){
       return Flux.create(profileCreatedEventPublisher).share();
    }
    @Bean
    HandlerMapping handlerMapping(WebSocketHandler wsh) {
        return new SimpleUrlHandlerMapping() {
            {
                setUrlMap(Collections.singletonMap("/ws/profiles", wsh));
                setOrder(10);
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler(ObjectMapper objectMapper, Flux<ProfileEvent>  profileCreatedEvents ) {
        return session -> {
            Flux<WebSocketMessage> messageFlux = profileCreatedEvents.map(evt -> {
                    try {
                        return objectMapper.writeValueAsString(evt.getSource());
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).map(session::textMessage);
            return session.send(messageFlux);
        };
    }

}