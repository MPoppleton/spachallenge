package au.com.hypothesisconsulting.spachallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MailGunConfig {

    @Value("${mailgun.endpoint}")
    public String endpoint;

    @Value("${mailgun.public.key}")
    public String publicKey;

    @Value("${mailgun.private.key}")
    public String privateKey;

}
