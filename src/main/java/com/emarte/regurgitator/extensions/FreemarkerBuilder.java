package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import freemarker.template.*;

import java.io.StringWriter;
import java.util.Map;

import static com.emarte.regurgitator.core.StringType.stringify;

public class FreemarkerBuilder extends AbstractValueBuilder {
    private final Log log = Log.getLog(this);
	private final ContextLocation source;
	private final String staticValue;

	public FreemarkerBuilder(ContextLocation source, String staticValue) {
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
            StringWriter writer = new StringWriter();
            Template template = new Template("template", stringify(value), new Configuration());
            template.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			template.process(valueMap, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RegurgitatorException("Error building freemarker message", e);
        }
    }
}
