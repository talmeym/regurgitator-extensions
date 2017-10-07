/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.*;

import static com.emarte.regurgitator.core.Log.getLog;
import static java.util.Collections.singletonMap;

public class VelocityProcessor implements ValueProcessor {
    private static final Log log = getLog(VelocityProcessor.class);
    private static Exception initError;

    static {
        try {
            Velocity.init();
        } catch (Exception e) {
            initError = e;
        }
    }

    private String templateValue;

    public VelocityProcessor(String templateValue) throws RegurgitatorException {
        if(initError != null) {
            throw new RegurgitatorException("Error initialising velocity", initError);
        }

        this.templateValue = templateValue;
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        Map<String, Object> valueMap = singletonMap("value", value);
        log.debug("Building value from template value '{}' and value map: {}", templateValue, valueMap);

        try {
            VelocityContext context = new VelocityContext(valueMap);
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, getClass().toString(), templateValue);
            writer.close();
            return writer.toString();
        } catch (Exception e) {
            throw new RegurgitatorException("Error building velocity message", e);
        }
    }
}
