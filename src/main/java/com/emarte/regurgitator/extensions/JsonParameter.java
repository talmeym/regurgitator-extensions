package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import static com.emarte.regurgitator.core.Log.getLog;

public class JsonParameter extends ParameterExtractor {
	private final Log log = getLog(this);
    private final ValueSource valueSource;
	private final JsonPathProcessor jsonPathProcessor;

    public JsonParameter(Object id, ParameterPrototype prototype, String context, ValueSource valueSource, JsonPathProcessor jsonPathProcessor, ValueProcessor processor) {
        super(id, prototype, context, processor);
        this.jsonPathProcessor = jsonPathProcessor;
		this.valueSource = valueSource;
	}

    @Override
    public Object extractValue(Message message) throws RegurgitatorException {
		Object value = jsonPathProcessor.process(valueSource.getValue(message, log), message);
		log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '" + getId() + '\'');
		return value;
    }
}
