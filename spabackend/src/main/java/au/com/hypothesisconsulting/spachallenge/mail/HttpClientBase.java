package au.com.hypothesisconsulting.spachallenge.mail;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;

public class HttpClientBase {

    private SSLContext sslContext;
    private HttpClient httpClient;

    public HttpClientBase() throws Exception {
        this.sslContext = new SSLContextBuilder()
                                     .loadTrustMaterial(null, ((x509Certificates, authType) -> true)).build();
        this.httpClient = HttpClients.custom()
                                     .setSSLContext(this.sslContext)
                                     .setSSLHostnameVerifier(new NoopHostnameVerifier())
                                     .build();
    }



}
