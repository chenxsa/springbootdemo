package com.xgstudio.springbootdemo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

/**
 * 序列化
 * @author chenxsa
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    /**
     * 将日期变成yyyy-MM-dd
     * @param value
     * @param gen
     * @param serializers
     * @throws IOException
     */
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(Constants.DATE_FORMATTER));
    }
}

