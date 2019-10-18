package co.urbi.backend.service;

import co.urbi.http.AbstractJsonResponseHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DLValidationResponseHandler extends AbstractJsonResponseHandler<Boolean> {

    public static final DLValidationResponseHandler VALIDATION_RESPONSE_HANDLER = new DLValidationResponseHandler();

    @Override
    protected Boolean handleResponseInternal(JsonNode response) {
        return response.get("valid").asBoolean();
    }

}