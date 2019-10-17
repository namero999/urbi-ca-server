package co.urbi.http.auth;

import lombok.AllArgsConstructor;
import org.apache.http.client.methods.HttpRequestBase;

@AllArgsConstructor
public class AuthHeader implements Authentication {

    private final String headerName;
    private final String value;

    @Override
    public void accept(HttpRequestBase request) {
        request.addHeader(headerName, value);
    }

}