/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.RegurgitatorException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XmlSchemaUtilTest {
    @Test
    public void testThis() throws RegurgitatorException, IOException, SAXException {
        Schema schema = XmlSchemaUtil.getSchema("classpath:/xml-schema-import-test.xsd");

        Validator validator = schema.newValidator();
        String xml = "<me xmlns=\"http://core.regurgitator.emarte.com\">Miles</me>";
        validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes())));
    }

    @Test(expected = RegurgitatorException.class)
    public void testFailedImport() throws RegurgitatorException {
        XmlSchemaUtil.getSchema("classpath:/xml-schema-failed-import-test.xsd");
    }
}