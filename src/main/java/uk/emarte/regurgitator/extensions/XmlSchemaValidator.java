/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.xml.sax.SAXException;
import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.XmlSchemaUtil.getSchema;

public class XmlSchemaValidator implements ValueProcessor {
    private static final Log log = getLog(XmlSchemaValidator.class);

    private final String schemaPath;
    private final Schema schema;

    public XmlSchemaValidator(String schemaPath) throws RegurgitatorException {
        this.schemaPath = schemaPath;
        schema = getSchema(schemaPath);
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        if(value != null) {
            return validateAgainstSchema(value);
        }

        log.warn("No value to process");
        return null;
    }

    Object validateAgainstSchema(Object value) throws RegurgitatorException {
        try {
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new ByteArrayInputStream(stringify(value).getBytes()));
            validator.validate(source);
            log.debug("Value successfully validated against xml schema '{}'", schemaPath);
            return value;
        } catch (SAXException | IOException e) {
            throw new RegurgitatorException("Value did not validate against xml schema + '" + schemaPath + "'", e);
        }
    }
}
