package co.urbi.http.auth;

import org.apache.http.client.methods.HttpRequestBase;

import java.util.function.Consumer;

public interface Authentication extends Consumer<HttpRequestBase> {
}