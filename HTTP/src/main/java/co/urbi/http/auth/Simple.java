package co.urbi.http.auth;

import org.apache.http.client.methods.HttpRequestBase;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

/**
 * Plain `Authorization` header with value
 */
public class Simple implements Authentication {

    private final String accessToken;

    public Simple(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void accept(HttpRequestBase request) {
        request.addHeader(AUTHORIZATION, accessToken);
    }

}