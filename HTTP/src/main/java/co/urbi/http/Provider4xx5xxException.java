package co.urbi.http;

import lombok.Getter;

@Getter
public class Provider4xx5xxException extends UrbiException {

    private final int statusCode;
    private String rawResponse;
    private Object[] errorFormatArgs;

    public Provider4xx5xxException(int statusCode) {
        this(statusCode, null);
    }

    public Provider4xx5xxException(int statusCode, String rawResponse) {
        this("The provider returned an error", statusCode, rawResponse);
    }

    public Provider4xx5xxException(String message, int statusCode, String rawResponse) {
        super(message);
        this.statusCode = statusCode;
        this.rawResponse = rawResponse;
    }

    public Provider4xx5xxException setErrorFormatArgs(Object... formatArgs) {
        this.errorFormatArgs = formatArgs;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Provider4xx5xxException(statusCode=%d, message=%s, rawResponse=%s)", statusCode, getMessage(), rawResponse);
    }

    @Override
    public boolean isStatusFromProvider() {
        return true;
    }

    @Override
    public String getResponseFromProvider() {
        return rawResponse;
    }

}