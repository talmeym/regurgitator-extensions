/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.*;

import java.util.List;

import static uk.emarte.regurgitator.core.Log.getLog;

public class JsonParameter extends ParameterExtractor {
    private final Log log = getLog(this);
    private final ValueSource valueSource;
    private final JsonPathProcessor jsonPathProcessor;

    public JsonParameter(Object id, ParameterPrototype prototype, String context, ValueSource valueSource, JsonPathProcessor jsonPathProcessor, List<ValueProcessor> processors, boolean optional) {
        super(id, prototype, context, processors, optional);
        this.jsonPathProcessor = jsonPathProcessor;
        this.valueSource = valueSource;
    }

    @Override
    public Object extractValue(Message message) throws RegurgitatorException {
        Object value = jsonPathProcessor.process(valueSource.getValue(message, log), message);
        log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '{}'", prototype.getName());
        return value;
    }
}
