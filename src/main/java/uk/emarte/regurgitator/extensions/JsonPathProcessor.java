/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;
import com.jayway.jsonpath.JsonPath;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.JsonPathUtil.strip;
import static com.jayway.jsonpath.JsonPath.compile;

public class JsonPathProcessor implements ValueProcessor {
    private static final Log log = getLog(JsonPathProcessor.class);
    private final JsonPath path;

    public JsonPathProcessor(String path) {
        this.path = compile(path);
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        try {
            log.debug("Applying jsonpath '{}'", path.getPath());
            return strip(path.read(stringify(value)));
        } catch(Exception e) {
            throw new RegurgitatorException("Error extracting json parameter using path: '" + path.getPath() + "'", e);
        }
    }
}
