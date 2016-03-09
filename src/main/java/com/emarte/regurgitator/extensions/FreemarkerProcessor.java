package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import freemarker.template.*;

import java.io.StringWriter;
import java.util.*;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;

public class FreemarkerProcessor implements ValueProcessor {
    private static final Log log = getLog(FreemarkerProcessor.class);

	private String templateValue;

	public FreemarkerProcessor(String templateValue) {
		this.templateValue = templateValue;
	}

	@Override
	public Object process(Object value, Message message) throws RegurgitatorException {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("value", value);
		log.debug("Building value from template value '" + templateValue + "' and value map: " + valueMap);

		try {
			StringWriter writer = new StringWriter();
			Template template = new Template("template", stringify(templateValue), new Configuration());
			template.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			template.process(valueMap, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RegurgitatorException("Error building freemarker message", e);
		}
	}
}
