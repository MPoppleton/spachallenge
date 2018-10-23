package au.com.hypothesisconsulting.spachallenge.model.sendgrid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "to", "cc", "bcc" })
public class Personalizations {

    public List<EmailName> to;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<EmailName> cc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<EmailName> bcc;

    public String subject;

    public void addToTo(String email) {
        this.addToTo(email, null);
    }

    public void addToTo(String email, String name) {
        EmailName emailName = new EmailName();
        emailName.setEmail(email);
        emailName.setName(name);
        if (this.to == null) {
            to = new ArrayList<>();
        }
        to.add(emailName);
    }

    public void addToCC(String email) {
        this.addToCC(email, null);
    }

    public void addToCC(String email, String name) {
        EmailName emailName = new EmailName();
        emailName.setEmail(email);
        emailName.setName(name);
        if (this.cc == null) {
            cc = new ArrayList<>();
        }
        cc.add(emailName);
    }

    public void addToBCC(String email) {
        this.addToBCC(email, null);
    }

    public void addToBCC(String email, String name) {
        EmailName emailName = new EmailName();
        emailName.setEmail(email);
        emailName.setName(name);
        if (this.bcc == null) {
            bcc = new ArrayList<>();
        }
        bcc.add(emailName);
    }

    public List<EmailName> getTo() {
        return to;
    }

    public void setTo(List<EmailName> to) {
        this.to = to;
    }

    public List<EmailName> getCc() {
        return cc;
    }

    public void setCc(List<EmailName> cc) {
        this.cc = cc;
    }

    public List<EmailName> getBcc() {
        return bcc;
    }

    public void setBcc(List<EmailName> bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
