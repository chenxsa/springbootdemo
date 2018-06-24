package com.xgstudio.springbootdemo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

public class TimestampSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toLocalDateTime().format(Constants.TIME_FORMATTER));
    }
}
