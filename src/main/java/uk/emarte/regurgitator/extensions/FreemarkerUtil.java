/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.RegurgitatorException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;

class FreemarkerUtil {
    private static final Log log = Log.getLog(FreemarkerUtil.class);
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_23);
    private static Exception initError;

    static {
        try {
            Properties properties = new Properties();

            try {
                log.debug("Loading freemarker properties");
                properties.load(getInputStreamForFile("classpath:/freemarker.properties"));
                log.debug("Loaded {} properties", properties.size());
            } catch(IOException ioe) {
                log.debug("Could not find freemarker.properties '{}', continuing without properties", ioe.getMessage());
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
        } catch(TemplateException te) {
            throw new RegurgitatorException("Template error building freemarker message", te);
        } catch (IOException ioe) {
            throw new RegurgitatorException("IO error building freemarker message", ioe);
        } catch(Exception e) {
            throw new RegurgitatorException("Other error building freemarker message", e);
        }
    }
}
