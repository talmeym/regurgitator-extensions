package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import java.util.*;

import static com.emarte.regurgitator.core.StringType.stringify;

public abstract class AbstractValueBuilder implements ValueBuilder {
    Map<String, Object> getValueMap(Message message, boolean allContexts) {
		return allContexts ? getAllContextValues(message) : getContextValues(message.getParameters());
	}

	private Map<String, Object> getAllContextValues(Message message) {
		Map<String, Object> values = new HashMap<String, Object>();

		for(Parameters context: message.contexts()) {
			if(context != message.getParameters()) {
				values.put(makeTemplateSafe(stringify(context.getId())), getContextValues(context));
			} else {
				for (Object id: context.ids()) {
					values.put(makeTemplateSafe(stringify(id)), context.getValue(id));
				}
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