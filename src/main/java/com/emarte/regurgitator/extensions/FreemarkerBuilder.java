package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import freemarker.template.*;

import java.io.StringWriter;
import java.util.Map;

import static com.emarte.regurgitator.core.StringType.stringify;

public class FreemarkerBuilder extends AbstractValueBuilder {
    private static final Log log = Log.getLog(FreemarkerBuilder.class);

	private final ValueSource valueSource;

	public FreemarkerBuilder(ValueSource valueSource) {
		this.valueSource = valueSource;
	}

    @Override
    public String build(Message message) throws RegurgitatorException {
		Map<String, Object> valueMap = getValueMap(message);
		log.debug("Building value from value map: " + valueMap);

		Object value = valueSource.getValue(message, log);

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
