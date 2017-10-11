/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.io.*;

import static com.emarte.regurgitator.core.FileUtil.checkResource;
import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;

public class XmlSchemaValidator implements ValueProcessor {
    private static final Log log = getLog(XmlSchemaValidator.class);
    private static final SchemaFactory factory = newInstance(W3C_XML_SCHEMA_NS_URI);

    static {
        factory.setResourceResolver(new LSResourceResolver() {
            public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
                ByteStreamLSInput input = new ByteStreamLSInput();

                try {
                    input.setByteStream(getInputStreamForFile(systemId));
                } catch (IOException e) {
                    // TODO do something
                }

                return input;
            }
        });
    }

    private final String schemaName;
    private final Schema schema;

    public XmlSchemaValidator(String schemaName) throws RegurgitatorException {
        this.schemaName = schemaName;
        checkResource(schemaName);

        try {
            schema = factory.newSchema(new StreamSource(getInputStreamForFile(schemaName)));
        } catch (Exception e) {
            throw new RegurgitatorException("Error loading schema '" + schemaName + "'", e);
        }
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {

        try {
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new ByteArrayInputStream(stringify(value).getBytes()));
            validator.validate(source);
            log.debug("Value successfully validated against schema '{}'", schemaName);
            return value;
        } catch (Exception e) {
            log.warn("Value did not validate against schema '{}'", schemaName);
            throw new RegurgitatorException("Value did not validate against schema + '" + schemaName + "'", e);
        }
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    private static class ByteStreamLSInput implements LSInput {
        private final UnsupportedOperationException uoe = new UnsupportedOperationException("Can only use byte stream");
        private InputStream byteStream;
        public String getBaseURI() { return null; }
        public InputStream getByteStream() { return byteStream; }
        public boolean getCertifiedText() { return false; }
        public Reader getCharacterStream() { return null; }
        public String getEncoding() { return null; }
        public String getPublicId() { return null; }
        public String getStringData() { return null; }
        public String getSystemId() { return null; }
        public void setBaseURI(String baseURI) { throw uoe; }
        public void setByteStream(InputStream byteStream) { this.byteStream = byteStream; }
        public void setCertifiedText(boolean certifiedText) { throw uoe; }
        public void setCharacterStream(Reader characterStream) { throw uoe; }
        public void setEncoding(String encoding) { throw uoe; }
        public void setPublicId(String publicId) { throw uoe; }
        public void setStringData(String stringData) { throw uoe; }
        public void setSystemId(String systemId) { throw uoe; }
    }
}
