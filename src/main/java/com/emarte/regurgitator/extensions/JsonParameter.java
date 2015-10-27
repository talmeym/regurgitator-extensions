package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import static com.emarte.regurgitator.core.CoreTypes.STRING;

public class JsonParameter extends ParameterExtractor {
	private final Log log = Log.getLog(this);
    private final ContextLocation source;
	private final JsonPathProcessor jsonPathProcessor;

    public JsonParameter(Object id, ParameterPrototype prototype, String context, ContextLocation source, JsonPathProcessor jsonPathProcessor, ValueProcessor processor) {
        super(id, prototype, context, processor);
        this.jsonPathProcessor = jsonPathProcessor;
		this.source = source;
	}

    @Override
    public Object extractValue(Message message) throws RegurgitatorException {
		Parameter parameter = message.getContextValue(source);

		if (parameter == null || parameter.getType() != STRING) {
			throw new RegurgitatorException(parameter == null ? "Json source document not found" : "Json source document not a string");
		}

		Object value = jsonPathProcessor.process(parameter.getValue(), message);
		log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '" + getId() + '\'');
		return value;
    }
}
