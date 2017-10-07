/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import java.util.Map;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.FreemarkerUtil.buildFrom;

public class FreemarkerBuilder extends AbstractValueBuilder {
    private static final Log log = getLog(FreemarkerBuilder.class);
    private final ValueSource valueSource;
    private final boolean allContexts;

    public FreemarkerBuilder(ValueSource valueSource, boolean allContexts) {
        this.valueSource = valueSource;
        this.allContexts = allContexts;
    }

    @Override
    public String build(Message message) throws RegurgitatorException {
        Map<String, Object> valueMap = getValueMap(message, allContexts);
        log.debug("Building value from value map: " + valueMap);
        return buildFrom(valueMap, stringify(valueSource.getValue(message, log)));
    }
}
