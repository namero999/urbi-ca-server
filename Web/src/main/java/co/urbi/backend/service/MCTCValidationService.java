package co.urbi.backend.service;

import co.urbi.backend.model.Params;
import co.urbi.backend.model.ValidationPayload;
import org.springframework.stereotype.Service;

import static co.urbi.backend.service.DLValidationResponseHandler.VALIDATION_RESPONSE_HANDLER;
import static co.urbi.http.Request.postJson;
import static co.urbi.json.JSON.convert;

@Service
public class MCTCValidationService {

    public boolean checkValidity(ValidationPayload payload) {

        Params params = convert(payload, Params.class);

        boolean valid = postJson("http://34.244.9.129:8090/validate").body(params).handle(VALIDATION_RESPONSE_HANDLER);

        return valid;

    }

}