package co.urbi.http;

import com.fasterxml.jackson.databind.JsonNode;

import static co.urbi.json.JSON.fromJson;

public abstract class AbstractJsonResponseHandler<T> extends AbstractRawResponseHandler<T> {

    @Override
    public T handleResponse(Response response) {

        String body = response.getBody();

        JsonNode json = fromJson(body);

        return handleResponseInternal(json);

    }

    protected abstract T handleResponseInternal(JsonNode response);

}