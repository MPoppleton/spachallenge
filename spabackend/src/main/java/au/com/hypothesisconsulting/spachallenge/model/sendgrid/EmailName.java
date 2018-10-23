package au.com.hypothesisconsulting.spachallenge.model.sendgrid;

import com.fasterxml.jackson.annotation.JsonInclude;

public class EmailName {

    public String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
