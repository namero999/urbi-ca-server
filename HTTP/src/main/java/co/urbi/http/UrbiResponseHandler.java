package co.urbi.http;

import org.apache.http.client.ResponseHandler;

public interface UrbiResponseHandler<T> extends ResponseHandler<Response<T>> {
}