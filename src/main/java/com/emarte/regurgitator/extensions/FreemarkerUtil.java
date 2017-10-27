/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.Log;
import com.emarte.regurgitator.core.RegurgitatorException;
import freemarker.template.*;

import java.io.*;
import java.util.Map;
import java.util.Properties;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;

class FreemarkerUtil {
    private static final Log log = Log.getLog(FreemarkerUtil.class);
    private static final Configuration CONFIGURATION = new Configuration();
    private static Exception initError;

    static {
        try {
            Properties properties = new Properties();

            try {
                properties.load(getInputStreamForFile("classpath:/freemarker.properties"));
            } catch(IOException ioe) {
                log.debug("Error finding freemarker.properties '{}', continuing without properties", ioe.getMessage());
            }

            CONFIGURATION.setSettings(properties);
        } catch (Exception e) {
            log.error("Error initialising freemarker", e);
            initError = e;
        }
    }

    static String buildFrom(Map<String, Object> valueMap, String templateText) throws RegurgitatorException {
        if(initError != null) {
            throw new RegurgitatorException("Error initialising freemarker", initError);
        }

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
