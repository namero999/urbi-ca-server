package co.urbi.http;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;

@UtilityClass
public class HttpConfig {

    public static final SSLContext DEFAULT_SSL_CONTEXT = getDefaultSslContext();
    public static final HttpClient DEFAULT_HTTP_CLIENT = getDefaultClient();
    public static final RequestConfig DEFAULT_HTTP_REQUEST_CONFIG = getDefaultRequestConfig();
    public static final int REQUEST_TIMEOUT_MILLIS = 25_000; // clients time out after 30 seconds, so try to reply before they leave us

    //--------------------------------------------------------------------------
    // Default client
    //--------------------------------------------------------------------------

    @SneakyThrows
    private static SSLContext getDefaultSslContext() {

        return SSLContexts.custom()
                          .loadTrustMaterial((chain, authType) -> true)
                          .build();

    }

    private static HttpClient getDefaultClient() {
        return createClient(getDefaultRequestConfig(), 100, 10);
    }

    private static RequestConfig getDefaultRequestConfig() {
        return createRequestConfig(REQUEST_TIMEOUT_MILLIS);
    }

    //--------------------------------------------------------------------------
    // Common
    //--------------------------------------------------------------------------

    private static HttpClient createClient(RequestConfig requestConfig,
                                           int maxConnTotal,
                                           int maxConnPerRoute) {

        return HttpClients.custom()
                          .setDefaultRequestConfig(requestConfig)
                          .setSSLSocketFactory(new SSLConnectionSocketFactory(DEFAULT_SSL_CONTEXT, new NoopHostnameVerifier()))
                          .setMaxConnTotal(maxConnTotal)
                          .setMaxConnPerRoute(maxConnPerRoute)
                          .disableCookieManagement()
                          .disableAutomaticRetries()
                          .build();

    }

    private static RequestConfig createRequestConfig(int timeoutMillis) {

        return RequestConfig.custom()
                            .setConnectionRequestTimeout(timeoutMillis) // Time to borrow a connection from the pool
                            .setConnectTimeout(timeoutMillis)           // Time to establish a connection
                            .setSocketTimeout(timeoutMillis)            // Time to wait for data
                            .build();

    }


}