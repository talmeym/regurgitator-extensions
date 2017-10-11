/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import java.util.Map;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.extensions.VelocityUtil.buildFrom;
import static java.util.Collections.singletonMap;

public class VelocityProcessor implements ValueProcessor {
    private static final Log log = getLog(VelocityProcessor.class);
    private final String templateValue;

    public VelocityProcessor(String templateValue) {
        this.templateValue = templateValue;
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        Map<String, Object> valueMap = singletonMap("value", value);
        log.debug("Building value from template value '{}' and value map: {}", templateValue, valueMap);
        return buildFrom(valueMap, templateValue);
    }
}
