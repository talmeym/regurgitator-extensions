package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import com.jayway.jsonpath.JsonPath;

import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.JsonPathUtil.strip;

public class JsonPathProcessor implements ValueProcessor {
	private static final Log log = Log.getLog(JsonPathProcessor.class);

	private final JsonPath path;

	public JsonPathProcessor(String path) {
		this.path = JsonPath.compile(path);
	}

	@Override
	public Object process(Object value) throws RegurgitatorException {
		try {
			log.debug("Applying jsonpath '" + path.getPath() + "'");
			return strip(path.read(stringify(value)));
		} catch(Exception e) {
			throw new RegurgitatorException("Error extracting json parameter using path: '" + path.getPath() + "'", e);
		}
	}
}
