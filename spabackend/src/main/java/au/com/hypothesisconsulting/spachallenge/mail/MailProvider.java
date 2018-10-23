package au.com.hypothesisconsulting.spachallenge.mail;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public interface MailProvider {

    HttpResponse sendMail(String subject, String body, String from, List<String> to, List<String> cc, List<String> bcc) throws MailSendFailException;

    default HttpResponse sendMail(String subject, String body, String from, String[] to, String[] cc, String[] bcc) throws MailSendFailException {
        List<String> ccList = null;
        List<String> bccList = null;
        if (cc != null) {
            ccList = Arrays.asList(cc);
        }
        if (bcc != null) {
            bccList = Arrays.asList(bcc);
        }

        return this.sendMail(subject, body, from, Arrays.asList(to), ccList, bccList);
    }

}
