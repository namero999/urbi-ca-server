package co.urbi.http;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import static co.urbi.json.JSON.fromJson;
import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
@NoArgsConstructor
@ToString(exclude = { "rawResponse" })
public class Response<T> {

    private HttpResponse rawResponse;

    private int statusCode;

    private boolean bodyRetrieved = false;
    private String body;
    private JsonNode cachedJsonNode;
    private T cachedJsonObject;

    @Setter
    private T result;

    @Setter
    private Throwable handlingException;

    public Response(@NonNull HttpResponse httpResponse) {
        this.statusCode = httpResponse.getStatusLine().getStatusCode();
        this.rawResponse = httpResponse;
    }

    @SneakyThrows
    public String getBody() {
        if (!bodyRetrieved) {
            body = rawResponse.getEntity() == null ? null : EntityUtils.toString(rawResponse.getEntity(), UTF_8);
            bodyRetrieved = true;
        }
        return body;
    }

    public JsonNode json() {
        if (cachedJsonNode == null)
            cachedJsonNode = fromJson(getBody());
        return cachedJsonNode;
    }

    public T json(Class<T> type) {
        if (cachedJsonObject == null)
            cachedJsonObject = fromJson(getBody(), type);
        return cachedJsonObject;
    }

}