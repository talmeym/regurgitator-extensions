/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import java.util.List;

import static com.emarte.regurgitator.core.Log.getLog;

public class JsonParameter extends ParameterExtractor {
    private final Log log = getLog(this);
    private final ValueSource valueSource;
    private final JsonPathProcessor jsonPathProcessor;

    public JsonParameter(Object id, ParameterPrototype prototype, String context, ValueSource valueSource, JsonPathProcessor jsonPathProcessor, List<ValueProcessor> processors) {
        super(id, prototype, context, processors);
        this.jsonPathProcessor = jsonPathProcessor;
        this.valueSource = valueSource;
    }

    @Override
    public Object extractValue(Message message) throws RegurgitatorException {
        Object value = jsonPathProcessor.process(valueSource.getValue(message, log), message);
        log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '{}'", getId());
        return value;
    }
}
