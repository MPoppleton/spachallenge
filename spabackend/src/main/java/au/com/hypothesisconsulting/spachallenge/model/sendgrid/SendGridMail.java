package au.com.hypothesisconsulting.spachallenge.model.sendgrid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder( {"personalizations", "from", "reply_to", "content" })
public class SendGridMail {

    // Required
    public List<Personalizations> personalizations = new ArrayList<>();
    // Required
    public EmailName from = new EmailName();

    // Optional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "reply_to")
    public EmailName replyTo;

    // Required
    public List<Content> content;

    public List<Personalizations> getPersonalizations() {
        return personalizations;
    }

    public void setPersonalizations(List<Personalizations> personalizations) {
        this.personalizations = personalizations;
    }

    public EmailName getFrom() {
        return from;
    }

    public void setFrom(EmailName from) {
        this.from = from;
    }

    public EmailName getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(EmailName replyTo) {
        this.replyTo = replyTo;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

}
