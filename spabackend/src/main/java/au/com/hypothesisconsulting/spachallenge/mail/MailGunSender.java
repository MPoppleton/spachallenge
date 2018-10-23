package au.com.hypothesisconsulting.spachallenge.mail;

import au.com.hypothesisconsulting.spachallenge.config.MailGunConfig;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailGunSender implements MailProvider {

    Logger logger = LoggerFactory.getLogger(MailGunSender.class);

    @Autowired
    MailGunConfig mailGunConfig;

    private SSLContext sslContext;
    private CloseableHttpClient httpClient;

    @Override
    public HttpResponse sendMail(String subject, String body, String from, List<String> to, List<String> cc, List<String> bcc) throws MailSendFailException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("api", mailGunConfig.privateKey);
        provider.setCredentials(AuthScope.ANY, credentials);

        try {
            logger.info("Attempting to send email");

            this.sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, ((x509Certificates, authType) -> true)).build();
            this.httpClient = HttpClients.custom()
                    .setSSLContext(this.sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .setDefaultCredentialsProvider(provider)
                    .build();

        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new MailSendFailException(e.getMessage());
        }

        URIBuilder builder = null;
        try {
            builder = new URIBuilder(mailGunConfig.endpoint);

            builder.setParameter("from", from);

            // To
            String toEmails = to.stream().collect(Collectors.joining(","));
            builder.setParameter("to", toEmails);

            // cc
            if (cc != null && !cc.isEmpty()) {
                String ccEmails = cc.stream().collect(Collectors.joining(","));
                builder.setParameter("cc", ccEmails);
            }

            // bcc
            if (bcc != null && !bcc.isEmpty()) {
                String bccEmails = bcc.stream().collect(Collectors.joining(","));
                builder.setParameter("bcc", bccEmails);
            }

            // Subject
            builder.setParameter("subject", subject);

            // body
            builder.setParameter("text", body);

            HttpPost httpPost = new HttpPost(builder.build());

            CloseableHttpResponse response = httpClient.execute(httpPost);
            response.close();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new MailSendFailException(response.getStatusLine().getReasonPhrase());
            }

            logger.info("Email successfully submitted to MailGun");
            return response;
        } catch (URISyntaxException | IOException e) {
            logger.error("Failed to send mail to MailGun: " + e);
            throw new MailSendFailException(e.getMessage());
        }
    }

    public MailGunConfig getMailGunConfig() {
        return mailGunConfig;
    }

    public void setMailGunConfig(MailGunConfig mailGunConfig) {
        this.mailGunConfig = mailGunConfig;
    }
}
