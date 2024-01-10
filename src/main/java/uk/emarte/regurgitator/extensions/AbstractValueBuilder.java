/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.ContextLocation;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.Parameters;
import uk.emarte.regurgitator.core.ValueBuilder;

import java.util.HashMap;
import java.util.Map;

import static uk.emarte.regurgitator.core.StringType.stringify;

abstract class AbstractValueBuilder implements ValueBuilder {
    Map<String, Object> getValueMap(Message message, boolean allContexts) {
        return allContexts ? getAllContextValues(message) : getContextValues(message.getParameters());
    }

    private Map<String, Object> getAllContextValues(Message message) {
        Map<String, Object> values = new HashMap<String, Object>();

        Object sessionId = message.hasSession() ? message.getSession().getId() : null;

        for(Parameters context: message.contexts()) {
            if(ContextLocation.PARAMETER_CONTEXT.equals(context.getId())) {
                for (Object id: context.ids()) {
                    values.put(makeTemplateSafe(stringify(id)), context.getValue(id));
                }
            } else {
                Object id = context.getId().equals(sessionId) ? ContextLocation.SESSION_CONTEXT : context.getId();
                values.put(makeTemplateSafe(stringify(id)), getContextValues(context));
            }
        }

        return values;
    }

    private Map<String, Object> getContextValues(Parameters context) {
        Map<String, Object> values = new HashMap<String, Object>();

        for (Object id: context.ids()) {
            values.put(makeTemplateSafe(stringify(id)), context.getValue(id));
        }

        return values;
    }

    private String makeTemplateSafe(String string) {
        return string.replaceAll("-", "_");
    }
}