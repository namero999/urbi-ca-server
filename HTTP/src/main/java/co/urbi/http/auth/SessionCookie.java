package co.urbi.http.auth;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Authentication based on a session cookie bearing a unique id.
 */
@RequiredArgsConstructor
public class SessionCookie implements Authentication {

    private final String cookieName;
    private final String sessionId;

    @Override
    public void accept(HttpRequestBase request) {
        request.setHeader(cookieName, sessionId);
    }

}