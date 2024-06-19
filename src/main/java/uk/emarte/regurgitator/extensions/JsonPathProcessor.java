/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.PathNotFoundException;
import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.JsonPathUtil.strip;

public class JsonPathProcessor implements ValueProcessor {
    private static final Log log = getLog(JsonPathProcessor.class);
    private final JsonPath jsonPath;

    public JsonPathProcessor(String jsonpath) throws RegurgitatorException {
        try {
            jsonPath = JsonPath.compile(jsonpath);
        } catch(JsonPathException jpe) {
            log.debug("Json Path Exception");
            throw new RegurgitatorException("Error compiling json path '" + jsonpath + "'", jpe);
        }
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        if (value != null) {
            return processJsonPath(value);
        }

        log.warn("No value to process");
        return null;
    }

    Object processJsonPath(Object value) throws RegurgitatorException {
        try {
            log.debug("Applying jsonpath '{}'", jsonPath.getPath());
            return strip(jsonPath.read(stringify(value)));
        } catch (PathNotFoundException pnfe) {
            log.debug("Path Not Found Exception");
            return null; // TODO really ??
        } catch (JsonPathException jpe) {
            throw new RegurgitatorException("Error evaluating jsonpath", jpe);
        }
    }
}
