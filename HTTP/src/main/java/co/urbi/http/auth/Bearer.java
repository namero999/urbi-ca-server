package co.urbi.http.auth;

import org.apache.http.client.methods.HttpRequestBase;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

/**
 * Bearer authentication with Access Token
 */
public class Bearer implements Authentication {

    private final String accessToken;

    public Bearer(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void accept(HttpRequestBase request) {
        request.addHeader(AUTHORIZATION, "Bearer " + accessToken);
    }

}