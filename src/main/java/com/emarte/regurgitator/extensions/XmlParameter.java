package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

public class XmlParameter extends ParameterExtractor {
	private final Log log = Log.getLog(this);
	private final ValueSource valueSource;
	private final XpathProcessor xpathProcessor;

	public XmlParameter(Object id, ParameterPrototype prototype, String context, ValueSource valueSource, XpathProcessor xpathProcessor, ValueProcessor processor) {
		super(id, prototype, context, processor);
		this.valueSource = valueSource;
		this.xpathProcessor = xpathProcessor;
	}

	@Override
	public Object extractValue(Message message) throws RegurgitatorException {
		Object value = xpathProcessor.process(valueSource.getValue(message, log), message);
		log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '" + getId() + '\'');
		return value;
	}
}
