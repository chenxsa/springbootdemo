package com.xgstudio.springbootdemo.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Timestamp.valueOf(p.getValueAsString());
    }
}
