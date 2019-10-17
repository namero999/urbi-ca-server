package co.urbi.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.*;

public class JSON {

    private static ObjectMapper MAPPER;

    public static void init(ObjectMapper mapper) {

        mapper.configure(ACCEPT_CASE_INSENSITIVE_ENUMS, true);

        mapper.configure(SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.configure(ORDER_MAP_ENTRIES_BY_KEYS, true);

        mapper.configure(FAIL_ON_EMPTY_BEANS, true);
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, true);

        mapper.configure(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        mapper.setSerializationInclusion(NON_NULL);
        mapper.setSerializationInclusion(NON_EMPTY);
        mapper.setSerializationInclusion(NON_ABSENT);

        MAPPER = mapper;

    }

    @SneakyThrows
    public static String toJson(Object value) {
        return MAPPER.writeValueAsString(value);
    }

    @SneakyThrows
    public static String toPrettyJson(Object value) {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    @SneakyThrows
    public static JsonNode fromJson(String content) {
        return content == null ? MissingNode.getInstance() : MAPPER.readTree(content);
    }

    @SneakyThrows
    public static <T> T fromJson(String content, Class<T> valueType) {
        return content == null ? null : MAPPER.readValue(content, valueType);
    }

    public static <T> T convert(Object fromValue, Class<T> toValueType) {
        return MAPPER.convertValue(fromValue, toValueType);
    }

}