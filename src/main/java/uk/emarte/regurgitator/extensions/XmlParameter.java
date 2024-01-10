/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.*;

import java.util.List;

import static uk.emarte.regurgitator.core.Log.getLog;

public class XmlParameter extends ParameterExtractor {
    private final Log log = getLog(this);
    private final ValueSource valueSource;
    private final XpathProcessor xpathProcessor;

    public XmlParameter(Object id, ParameterPrototype prototype, String context, ValueSource valueSource, XpathProcessor xpathProcessor, List<ValueProcessor> processors) {
        super(id, prototype, context, processors);
        this.valueSource = valueSource;
        this.xpathProcessor = xpathProcessor;
    }

    @Override
    public Object extractValue(Message message) throws RegurgitatorException {
        Object value = xpathProcessor.process(valueSource.getValue(message, log), message);
        log.debug("Extracted " + (value != null ? "value '" + value + "'" : "no value") + " for parameter '{}'", getId());
        return value;
    }
}
