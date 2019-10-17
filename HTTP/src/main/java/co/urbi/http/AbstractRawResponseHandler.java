package co.urbi.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

public abstract class AbstractRawResponseHandler<T> implements UrbiResponseHandler<T> {

    @Override
    public Response<T> handleResponse(HttpResponse rawResponse) {

        Response<T> response = new Response<>(rawResponse);

        try {

            if (response.getStatusCode() >= 400)
                handleError(response);

            T result = handleResponse(response);

            return response.setResult(result);

        } catch (Throwable t) {
            return response.setHandlingException(t);
        }

    }

    protected abstract T handleResponse(Response response);

    protected void handleError(Response response) {
        throw response.getStatusCode() == 401 ? new AuthorizationException() : new Provider4xx5xxException(response.getStatusCode(), response.getBody());
    }

}