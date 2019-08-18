package org.wb.reactive.web.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wb.reactive.web.domain.enity.Profile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileAction {

    private String action;
    private Profile profile;
    private Date timestamp;

    public ProfileAction(String action, Profile profile) {
        this.action=action;
        this.profile=profile;
        this.timestamp=new Date();
    }
}
