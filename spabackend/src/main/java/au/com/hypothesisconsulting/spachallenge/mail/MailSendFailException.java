package au.com.hypothesisconsulting.spachallenge.mail;

public class MailSendFailException  extends Exception {

    public MailSendFailException(String errorMessage) {
        super(errorMessage);
    }

}
