package co.urbi.http.auth;

import org.apache.http.client.methods.HttpRequestBase;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

/**
 * Basic authentication with Access Token
 */
public class BasicAT implements Authentication {

    private final String accessToken;

    public BasicAT(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void accept(HttpRequestBase request) {
        request.addHeader(AUTHORIZATION, "Basic " + accessToken);
    }

}