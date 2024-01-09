/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.RegurgitatorException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;

class VelocityUtil {
    private static final Log log = Log.getLog(VelocityUtil.class);
    private static Exception initError;

    static {
        try {
            Properties properties = new Properties();

            try {
                log.debug("Loading velocity properties");
                properties.load(getInputStreamForFile("classpath:/velocity.properties"));
                log.debug("Loaded {} properties", properties.size());
            } catch(IOException ioe) {
                log.debug("Could not find velocity.properties '{}', continuing without properties", ioe.getMessage());
            }

            Velocity.init(properties);
        } catch (Exception e) {
            log.error("Error initialising velocity", e);
            initError = e;
        }
    }

    static String buildFrom(Map<String, Object> valueMap, String templateText) throws RegurgitatorException {
        if(initError != null) {
            throw new RegurgitatorException("Error initialising velocity", initError);
        }

        try {
            VelocityContext context = new VelocityContext(valueMap);
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, VelocityUtil.class.getName(), templateText);
            writer.close();
            return writer.toString();
        } catch (Exception e) {
            throw new RegurgitatorException("Error building velocity message", e);
        }
    }
}
