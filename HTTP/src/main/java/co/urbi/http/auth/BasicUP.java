package co.urbi.http.auth;

import org.apache.http.client.methods.HttpRequestBase;

import static co.urbi.http.U.encodeBase64;
import static org.apache.http.HttpHeaders.AUTHORIZATION;

/**
 * Basic authentication with Username/Password
 */
public class BasicUP implements Authentication {

    private final String username;
    private final String password;

    public BasicUP(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void accept(HttpRequestBase request) {
        request.addHeader(AUTHORIZATION, getHeaderValue());
    }

    private String getHeaderValue() {
        return "Basic " + encodeBase64(username + ":" + password);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please provide the following arguments: username and password");
            return;
        }
        BasicUP basicUP = new BasicUP(args[0], args[1]);
        System.out.println(AUTHORIZATION + " : " + basicUP.getHeaderValue());
    }

}