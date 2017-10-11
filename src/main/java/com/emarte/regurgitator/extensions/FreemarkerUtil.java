/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.RegurgitatorException;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

class FreemarkerUtil {
    private static final Configuration CONFIGURATION = new Configuration();

    static String buildFrom(Map<String, Object> valueMap, String templateText) throws RegurgitatorException {
        try {
            StringWriter writer = new StringWriter();
            Template template = new Template("template", templateText, CONFIGURATION);
            template.process(valueMap, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RegurgitatorException("Error building freemarker message", e);
        }
    }
}
