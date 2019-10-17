package co.urbi.http;

import co.urbi.http.auth.AuthHeader;
import co.urbi.http.auth.Authentication;
import co.urbi.http.auth.BasicUP;
import co.urbi.http.auth.Bearer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static co.urbi.http.HttpConfig.DEFAULT_HTTP_CLIENT;
import static co.urbi.json.JSON.toJson;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpHeaders.ACCEPT_ENCODING;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_OCTET_STREAM;
import static org.apache.http.entity.ContentType.create;

public class Request {

    public static final ContentType APPLICATION_FORM_URLENCODED_UTF8 = create("application/x-www-form-urlencoded", UTF_8);
    public static final ContentType APPLICATION_JSON_UTF8 = create("application/json", UTF_8);

    private HttpClient httpClient = DEFAULT_HTTP_CLIENT;

    @Getter
    private HttpRequestBase rawRequest;

    @Getter
    private String uri;
    @Getter
    private Params params;
    @Getter
    private Object body;

    @Getter
    private Headers headers;
    private ContentType contentType;
    private Authentication authentication;

    ///// MAIN API

    public static Request get(String uri) {
        return new Request(new HttpGet(), uri);
    }

    public static Request get(String uri, Params params) {
        return get(uri).params(params);
    }

    public static Request getJson(String uri) {
        return get(uri).contentType(APPLICATION_JSON_UTF8);
    }

    public static Request post(String uri) {
        return new Request(new HttpPost(), uri).contentType(APPLICATION_FORM_URLENCODED_UTF8);
    }

    public static Request postJson(String uri) {
        return post(uri).contentType(APPLICATION_JSON_UTF8);
    }

    public static Request put(String uri) {
        return new Request(new HttpPut(), uri);
    }

    public static Request putJson(String uri) {
        return put(uri).contentType(APPLICATION_JSON_UTF8);
    }

    public static Request patch(String uri) {
        return new Request(new HttpPatch(), uri);
    }

    public static Request patchJson(String uri) {
        return patch(uri).contentType(APPLICATION_JSON_UTF8);
    }

    public static Request delete(String uri) {
        return new Request(new HttpDelete(), uri);
    }

    /////

    private Request(HttpRequestBase rawRequest, String uri) {
        this.rawRequest = rawRequest;
        this.uri = uri;
    }

    public Request withClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public Request params(Params params) {
        this.params = params;
        return this;
    }

    public Request body(Object body) {
        this.body = body;
        return this;
    }

    public Request headers(Headers headers) {
        this.headers = headers;
        return this;
    }

    public Request contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public Request auth(String headerName, String value) {
        this.authentication = new AuthHeader(headerName, value);
        return this;
    }

    public Request auth(Authentication authentication) {
        this.authentication = authentication;
        return this;
    }

    public Request bearer(String bearer) {
        this.authentication = new Bearer(bearer);
        return this;
    }

    public Request basic(String user, String pass) {
        this.authentication = new BasicUP(user, pass);
        return this;
    }

    @SneakyThrows
    public <T> T handle(UrbiResponseHandler<T> handler) {

        prepare();

        Response<T> response = null;
        Exception exception = null;

        response = httpClient.execute(rawRequest, handler);

        if (response.getHandlingException() != null)
            throw response.getHandlingException();

        return response.getResult();

    }

    public <T> Response<T> exec() {
        return exec(false);
    }

    public JsonNode json() {
        return exec(false).json();
    }

    @SneakyThrows
    public <T> T json(Class<T> type) {
        Response<T> exec = exec(false);
        return exec.json(type);
    }

    @SneakyThrows
    public <T> Response<T> exec(boolean stream) {

        prepare();

        Response<T> response = null;
        Exception exception = null;

        HttpResponse httpResponse = httpClient.execute(rawRequest/*, getContext()*/);

        response = new Response<>(httpResponse);

        if (!stream)
            response.getBody();

        return response;

    }

    private void prepare() {

        rawRequest.setHeaders(new Header[0]);

        rawRequest.setURI(buildQueryString(uri, params));

        // Headers
        if (headers != null && !headers.isEmpty())
            headers.forEach(rawRequest::setHeader);

        // Always set Accept-Encoding for compression
        rawRequest.setHeader(ACCEPT_ENCODING, "gzip");

        // Content Type
        if (contentType != null && (headers == null || !headers.containsKey(CONTENT_TYPE)))
            rawRequest.setHeader(CONTENT_TYPE, contentType.toString());

        // Authentication
        if (authentication != null)
            authentication.accept(rawRequest);

        // Body
        if (rawRequest instanceof HttpEntityEnclosingRequestBase)
            ((HttpEntityEnclosingRequestBase) rawRequest).setEntity(buildEntity());

    }

    @SneakyThrows
    private HttpEntity buildEntity() {

        if (body == null)
            return null;

        if (contentType == APPLICATION_FORM_URLENCODED_UTF8) {

            Params paramsBody = (Params) body;
            return new UrlEncodedFormEntity(paramsBody.entrySet().stream()
                                                      .map(e -> new BasicNameValuePair(e.getKey(), String.valueOf(e.getValue())))
                                                      .collect(toList()), UTF_8);

        } else if (contentType == APPLICATION_JSON_UTF8) {
            return new StringEntity(body instanceof String ? (String) body : toJson(body), UTF_8);
        } else if (contentType == APPLICATION_OCTET_STREAM) {
            return new ByteArrayEntity(Files.readAllBytes((Path) body), contentType);
        } else if (body instanceof String) {
            return new StringEntity((String) body, contentType);
        } else if (body instanceof byte[]) {
            return new ByteArrayEntity((byte[]) body, contentType);
        }
        return null;

    }

    @SneakyThrows
    private URI buildQueryString(String uri, Params params) {

        if (params == null || params.isEmpty()) {

            return URI.create(uri);

        } else {

            List<NameValuePair> nvps = params.entrySet().stream()
                                             .map(e -> new BasicNameValuePair(e.getKey(), String.valueOf(e.getValue())))
                                             .collect(toList());

            return new URIBuilder(uri).addParameters(nvps).build();

        }

    }

}