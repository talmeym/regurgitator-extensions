package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.RegurgitatorException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;

class VelocityUtil {
    private static Exception initError;

    static {
        try {
            Velocity.init();
        } catch (Exception e) {
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
