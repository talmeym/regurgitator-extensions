package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;

public class VelocityBuilder extends AbstractValueBuilder {
	private static final Log log = getLog(VelocityBuilder.class);
	private static Exception initError;

    static {
        try {
            Velocity.init();
        } catch (Exception e) {
            initError = e;
        }
    }

	private ValueSource valueSource;
	private boolean allContexts;

	public VelocityBuilder(ValueSource valueSource, boolean allContexts) throws RegurgitatorException {
		this.allContexts = allContexts;
		if(initError != null) {
			throw new RegurgitatorException("Error initialising Velocity", initError);
		}

		this.valueSource = valueSource;
	}

	@Override
	public String build(Message message) throws RegurgitatorException {
		Map<String, Object> valueMap = getValueMap(message, allContexts);
		log.debug("Building value from value map: " + valueMap);

		Object value = valueSource.getValue(message, log);

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
