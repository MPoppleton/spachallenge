package au.com.hypothesisconsulting.spachallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class SendGridConfig {

    @Value("${sendgrid.apikey}")
    public String apiKey;

    @Value("${sendgrid.endpoint}")
    public String endpoint;

}
