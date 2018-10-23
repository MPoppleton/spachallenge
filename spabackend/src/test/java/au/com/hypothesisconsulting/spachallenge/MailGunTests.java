package au.com.hypothesisconsulting.spachallenge;

import au.com.hypothesisconsulting.spachallenge.config.MailGunConfig;
import au.com.hypothesisconsulting.spachallenge.mail.MailGunSender;
import au.com.hypothesisconsulting.spachallenge.mail.MailSendFailException;
import au.com.hypothesisconsulting.spachallenge.mail.SendGridSender;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailGunTests {

    @Autowired
    MailGunConfig mailGunConfig;

    @Autowired
    MailGunSender mailGunSender;

    @Test
    public void sendMailTest() throws MailSendFailException {
        mailGunSender = new MailGunSender();
        mailGunSender.setMailGunConfig(mailGunConfig);

        List<String> emailsTo = new ArrayList<>();
        List<String> emailsCC = new ArrayList<>();
        List<String> emailsBCC = new ArrayList<>();
        emailsTo.add("mtpoppleton@gmail.com");

        HttpResponse response = mailGunSender.sendMail("subject", "body", "mtpoppleton@gmail.com", emailsTo, emailsCC, emailsBCC);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

}
