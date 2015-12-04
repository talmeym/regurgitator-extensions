package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;

import static com.emarte.regurgitator.core.StringType.stringify;

public class VelocityBuilder extends AbstractValueBuilder {
	private static final Log log = Log.getLog(VelocityBuilder.class);
	private static Exception initError;

    static {
        try {
            Velocity.init();
        } catch (Exception e) {
            initError = e;
        }
    }

	private ContextLocation source;
	private String staticValue;

	public VelocityBuilder(ContextLocation source, String staticValue) throws RegurgitatorException {
		if(initError != null) {
			throw new RegurgitatorException("Error initialising Velocity", initError);
		}

		this.source = source;
		this.staticValue = staticValue;
	}

	@Override
	public String build(Message message) throws RegurgitatorException {
		Map<String, Object> valueMap = getValueMap(message);
		log.debug("Building value from value map: " + valueMap);
		Object value;

		if(source != null) {
			Parameter parameter = message.getContextValue(source);

			if(parameter == null) {
				throw new RegurgitatorException("No value found at context location '" + source + "'");
			}

			log.debug("Using template from context location '" + source + "'");
			value = parameter.getValue();
		} else {
			log.debug("Using static value template");
			value = staticValue;
		}

		try {
			VelocityContext context = new VelocityContext(valueMap);
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, getClass().toString(), stringify(value));
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			throw new RegurgitatorException("Error building velocity message", e);
		}
	}
}
