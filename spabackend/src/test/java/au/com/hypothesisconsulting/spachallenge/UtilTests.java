package au.com.hypothesisconsulting.spachallenge;

import au.com.hypothesisconsulting.spachallenge.model.sendgrid.Content;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.EmailName;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.Personalizations;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.SendGridMail;
import au.com.hypothesisconsulting.spachallenge.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilTests {

    final String SG_JSON = "{\r\n" +
            "  \"personalizations\" : [ {\r\n" +
            "    \"to\" : [ {\r\n" +
            "      \"email\" : \"mtpoppleton@gmail.com\"\r\n" +
            "    } ],\r\n" +
            "    \"subject\" : \"subject\"\r\n" +
            "  } ],\r\n" +
            "  \"from\" : {\r\n" +
            "    \"email\" : \"mtpoppleton@gmail.com\"\r\n" +
            "  },\r\n" +
            "  \"reply_to\" : {\r\n" +
            "    \"email\" : \"mtpoppleton@gmail.com\"\r\n" +
            "  },\r\n" +
            "  \"content\" : [ {\r\n" +
            "    \"type\" : \"text/plain\",\r\n" +
            "    \"value\" : \"bodybody\"\r\n" +
            "  } ]\r\n" +
            "}";

    @Test
    public void testToJson() throws JsonProcessingException {
        String subject = "subject";
        List<String> to = Arrays.asList("mtpoppleton@gmail.com");
        String from = "mtpoppleton@gmail.com";

        SendGridMail sendGridMail = new SendGridMail();
        sendGridMail.personalizations = new ArrayList<>();
        Personalizations personalizations = new Personalizations();
        personalizations.subject = subject;
        personalizations.to = new ArrayList<>();
        sendGridMail.personalizations.add(personalizations);

        to.forEach(personalizations::addToTo);
        sendGridMail.from = new EmailName();
        sendGridMail.from.email = from;
        sendGridMail.replyTo = new EmailName();
        sendGridMail.replyTo.email = from;

        sendGridMail.content = new ArrayList<>();
        Content content = new Content();
        content.value = "bodybody";
        sendGridMail.content.add(content);

        Assert.assertEquals(SG_JSON, JsonUtil.toJson(sendGridMail));
    }

    @Test
    public void testFromJson() throws IOException {
        SendGridMail mail = JsonUtil.fromJson(SG_JSON, SendGridMail.class);

        Assert.assertEquals(1, mail.personalizations.size());
        Assert.assertEquals("mtpoppleton@gmail.com", mail.from.email);
        Assert.assertEquals("mtpoppleton@gmail.com", mail.replyTo.email);
    }



}
