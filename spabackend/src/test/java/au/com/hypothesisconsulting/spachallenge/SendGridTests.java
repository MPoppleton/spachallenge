package au.com.hypothesisconsulting.spachallenge;

import au.com.hypothesisconsulting.spachallenge.config.SendGridConfig;
import au.com.hypothesisconsulting.spachallenge.mail.MailSendFailException;
import au.com.hypothesisconsulting.spachallenge.mail.SendGridSender;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(locations = "classpath:application.properties")
public class SendGridTests {

    @Autowired
    SendGridConfig config;

    @Autowired
    SendGridSender sendGridSender;

    @Test
    public void sendMailTest() throws MailSendFailException {
        sendGridSender = new SendGridSender();
        sendGridSender.setSendGridConfig(config);

        List<String> emailsTo = new ArrayList<>();
        List<String> emailsCC = new ArrayList<>();
        List<String> emailsBCC = new ArrayList<>();
        emailsTo.add("mtpoppleton@gmail.com");

        HttpResponse response = sendGridSender.sendMail("subject", "body", "mtpoppleton@gmail.com", emailsTo, emailsCC, emailsBCC);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 202);
    }

    @Test
    public void sendMailTestArray() throws MailSendFailException {
        sendGridSender = new SendGridSender();
        sendGridSender.setSendGridConfig(config);

        String[] emailTo = new String[1];
        emailTo[0] = "mtpoppleton@gmail.com";

        HttpResponse response = sendGridSender.sendMail("subject", "body", "test@test.com", emailTo,null, null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 202);
    }

    @Test
    public void sendMailCCTest() throws MailSendFailException {
        sendGridSender = new SendGridSender();
        sendGridSender.setSendGridConfig(config);

        List<String> emailsTo = new ArrayList<>();
        List<String> emailsCC = new ArrayList<>();
        List<String> emailsBCC = new ArrayList<>();
        emailsTo.add("mtpoppleton@gmail.com");
        emailsCC.add("test@gmail.com");
        emailsBCC.add("test1@gmail.com");

        HttpResponse response = sendGridSender.sendMail("subject", "body", "mtpoppleton@gmail.com", emailsTo, emailsCC, emailsBCC);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 202);
    }

    @Test
    public void sendMailCCFail() throws MailSendFailException {
        sendGridSender = new SendGridSender();
        sendGridSender.setSendGridConfig(config);

        List<String> emailsTo = new ArrayList<>();
        List<String> emailsCC = new ArrayList<>();
        emailsTo.add("mtpoppleton@gmail.com");
        emailsTo.add("mtpoppleton@gmail.com");

        HttpResponse response = sendGridSender.sendMail("subject", "body", "mtpoppleton@gmail.com", emailsTo, emailsCC, null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
    }


}
