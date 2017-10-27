/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.XmlSchemaUtil.getSchema;

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
        try {
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new ByteArrayInputStream(stringify(value).getBytes()));
            validator.validate(source);
            log.debug("Value successfully validated against schema '{}'", schemaPath);
            return value;
        } catch (Exception e) {
            log.warn("Value did not validate against schema '{}'", schemaPath);
            throw new RegurgitatorException("Value did not validate against schema + '" + schemaPath + "'", e);
        }
    }
}
