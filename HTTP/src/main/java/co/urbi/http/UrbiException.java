package co.urbi.http;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

import static java.lang.String.format;

@Data
public abstract class UrbiException extends RuntimeException {

    public UrbiException() {
        super();
    }

    public UrbiException(String message) {
        super(message);
    }

    public UrbiException(String message, Throwable cause) {
        super(message, cause);
    }

    @JsonIgnore
    public abstract int getStatusCode();

    public Set<String> getRequiredFields() {
        return null;
    }

    public boolean isStatusFromProvider() {
        return false;
    }

    public String getResponseFromProvider() {
        return null;
    }

    public Object[] getErrorFormatArgs() {
        return null;
    }

    @Override
    public String toString() {
        return format("UrbiException(message=%s)", getMessage());
    }

}