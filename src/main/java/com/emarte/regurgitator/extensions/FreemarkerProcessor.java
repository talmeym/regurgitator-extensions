/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.Log;
import com.emarte.regurgitator.core.Message;
import com.emarte.regurgitator.core.RegurgitatorException;
import com.emarte.regurgitator.core.ValueProcessor;

import java.util.Map;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.extensions.FreemarkerUtil.buildFrom;
import static java.util.Collections.singletonMap;

public class FreemarkerProcessor implements ValueProcessor {
    private static final Log log = getLog(FreemarkerProcessor.class);
    private final String templateValue;

    public FreemarkerProcessor(String templateValue) {
        this.templateValue = templateValue;
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        Map<String, Object> valueMap = singletonMap("value", value);
        log.debug("Building value from template value '{}' and value map: {}", templateValue, valueMap);
        return buildFrom(valueMap, templateValue);
    }
}
