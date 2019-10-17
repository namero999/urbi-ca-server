package co.urbi.backend.web;

import co.urbi.backend.model.ValidationPayload;
import org.springframework.stereotype.Service;

@Service
public class DLValidationService {

    public boolean checkValidity(ValidationPayload payload) {
        return true;
    }

}