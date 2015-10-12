package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.*;

public class VelocityProcessor implements ValueProcessor {
	protected Log log = Log.getLog(AbstractValueBuilder.class);
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
			throw new RegurgitatorException("Error initalising velocity: " + initError);
		}

		this.templateValue = templateValue;
	}

	@Override
	public Object process(Object value) throws RegurgitatorException {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("value", value);
		log.debug("Building value from template value '" + templateValue + "' and value map: " + valueMap);

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
