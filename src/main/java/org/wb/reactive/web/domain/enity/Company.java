package org.wb.reactive.web.domain.enity;

import org.springframework.data.mongodb.core.mapping.Field;

public class Company {

    @Field("name")
    private String name;
    @Field("catch_ph")
    private String catchPhrase;
    @Field("bs")
    private String bs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
}
