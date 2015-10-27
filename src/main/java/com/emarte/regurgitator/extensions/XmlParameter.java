package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import static com.emarte.regurgitator.core.CoreTypes.STRING;
import static com.emarte.regurgitator.core.StringType.stringify;

public class XmlParameter extends ParameterExtractor {
	private final Log log = Log.getLog(this);
	private final ContextLocation source;
	private final XPathProcessor xPathProcessor;

	public XmlParameter(Object id, ParameterPrototype prototype, String context, ContextLocation source, XPathProcessor xPathProcessor, ValueProcessor processor) {
		super(id, prototype, context, processor);
		this.source = source;
		this.xPathProcessor = xPathProcessor;
	}

	@Override
	public Object extractValue(Message message) throws RegurgitatorException {
		Parameter parameter = message.getContextValue(source);

		if (parameter == null || parameter.getType() != STRING) {
			throw new RegurgitatorException(parameter == null ? "Xml source document not found" : "Xml source document not a string");
		}

		Object value = xPathProcessor.process(stringify(parameter), message);
		log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '" + getId() + '\'');
		return value;
	}
}
