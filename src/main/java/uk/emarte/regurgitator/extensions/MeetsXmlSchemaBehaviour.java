/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.xml.sax.SAXException;
import uk.emarte.regurgitator.core.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;

public class MeetsXmlSchemaBehaviour implements ConditionBehaviour {
    private static final Log log = getLog(MeetsXmlSchemaBehaviour.class);

    @Override
    public boolean evaluate(Parameter parameter, Message message, String conditionValue, boolean expectation) throws RegurgitatorException {
        boolean validates = false;

        if(parameter != null) {
            try {
                Schema schema = XmlSchemaUtil.getSchema(conditionValue);
                Validator validator = schema.newValidator();
                Source source = new StreamSource(new ByteArrayInputStream(stringify(parameter.getValue()).getBytes()));
                validator.validate(source);
                validates = true;
            } catch (SAXException se) {
                // validates = false;
            } catch (IOException e) {
                throw new RegurgitatorException("Exception validating against xml schema: " + conditionValue, e);
            }
        }

        log.debug("Parameter " + (validates ?  "meets"  : "does not meets") + " xml schema '{}'", conditionValue);
        return validates == expectation;
    }
}
