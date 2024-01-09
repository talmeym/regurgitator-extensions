/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonPrintProcessor implements ValueProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            objectMapper.writer().writeValue(outputStream, value);
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RegurgitatorException("Error writing value out as Json", e);
        }
    }
}
