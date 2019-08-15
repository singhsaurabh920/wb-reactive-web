package org.wb.reactive.web.event;

import org.springframework.context.ApplicationEvent;
import org.wb.reactive.web.domain.enity.Profile;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(Profile source) {
        super(source);
    }
}
