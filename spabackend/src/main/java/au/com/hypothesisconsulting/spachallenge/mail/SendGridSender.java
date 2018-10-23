package au.com.hypothesisconsulting.spachallenge.mail;

import au.com.hypothesisconsulting.spachallenge.util.JsonUtil;
import au.com.hypothesisconsulting.spachallenge.config.SendGridConfig;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.Content;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.EmailName;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.Personalizations;
import au.com.hypothesisconsulting.spachallenge.model.sendgrid.SendGridMail;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendGridSender implements MailProvider {

    @Autowired
    SendGridConfig sendGridConfig;

    private SSLContext sslContext;
    private CloseableHttpClient httpClient;

    @Override
    public HttpResponse sendMail(String subject, String body, String from, List<String> to, List<String> cc, List<String> bcc) throws MailSendFailException {

        try {
            this.sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, ((x509Certificates, authType) -> true)).build();
            this.httpClient = HttpClients.custom()
                    .setSSLContext(this.sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();

            HttpPost httpPost = new HttpPost(sendGridConfig.endpoint);
            httpPost.setHeader("Accept", "application/json");
            SendGridMail sendGridMail = new SendGridMail();

            Personalizations personalizations = new Personalizations();
            personalizations.subject = subject;
            to.forEach(personalizations::addToTo);

            if (cc != null) {
                cc.forEach(personalizations::addToCC);
            }
            if (bcc != null) {
                bcc.forEach(personalizations::addToBCC);
            }
            sendGridMail.personalizations.add(personalizations);

            sendGridMail.from = new EmailName();
            sendGridMail.from.email = from;

            sendGridMail.replyTo = new EmailName();
            sendGridMail.replyTo.email = from;

            sendGridMail.content = new ArrayList<>();
            Content content = new Content();
            content.value = body;
            sendGridMail.content.add(content);

            String emailJson = null;

            emailJson = JsonUtil.toJson(sendGridMail);

            HttpEntity sendEntity = new StringEntity(emailJson);
            httpPost.setEntity(sendEntity);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + sendGridConfig.apiKey);

            CloseableHttpResponse response =  httpClient.execute(httpPost);
            response.close();
            return response;
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new MailSendFailException(e.getMessage());
        }
    }

    public SendGridConfig getSendGridConfig() {
        return sendGridConfig;
    }

    public void setSendGridConfig(SendGridConfig sendGridConfig) {
        this.sendGridConfig = sendGridConfig;
    }
}
