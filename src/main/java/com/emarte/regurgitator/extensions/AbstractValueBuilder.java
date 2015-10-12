package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import java.util.*;

public abstract class AbstractValueBuilder implements ValueBuilder {
    Map<String, Object> getValueMap(Message message) {
        Map<String, Object> values = new HashMap<String, Object>();
		Parameters parameters = message.getParameters();

        for (Object id: parameters.ids()) {
            values.put(String.valueOf(id), parameters.getValue(id));
        }

        return values;
    }
}
