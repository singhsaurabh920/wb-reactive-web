package org.wb.reactive.web.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.wb.reactive.web.domain.enity.Profile;
@Data
public class ProfileEvent extends ApplicationEvent {

    public ProfileEvent(ProfileAction source) {
        super(source);
    }

    public static ProfileEvent createProfileEvent(String action,Profile profile){
        return  new ProfileEvent(new ProfileAction(action,profile));
    }
}
