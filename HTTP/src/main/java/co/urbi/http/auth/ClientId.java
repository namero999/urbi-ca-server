package co.urbi.http.auth;

import org.apache.http.client.methods.HttpRequestBase;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

public class ClientId implements Authentication {

    private final String clientId;

    public ClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void accept(HttpRequestBase request) {
        request.addHeader(AUTHORIZATION, "ClientId " + clientId);
    }

}