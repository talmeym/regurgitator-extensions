/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.w3c.dom.ls.LSInput;
import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.RegurgitatorException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;

class XmlSchemaUtil {
    private static final Log log = Log.getLog(XmlSchemaUtil.class);
    private static final SchemaFactory FACTORY = newInstance(W3C_XML_SCHEMA_NS_URI);

    static {
        FACTORY.setResourceResolver((type, namespaceURI, publicId, systemId, baseURI) -> {
            ByteStreamLSInput input = new ByteStreamLSInput();

            try {
                String resolvePath = "classpath:/" + systemId.substring(systemId.lastIndexOf("/") + 1);
                input.setByteStream(getInputStreamForFile(resolvePath));
            } catch (IOException e) {
                log.error("Error resolving resource for schema validation", e);
                throw new IllegalStateException("Error resolving resource for schema validation", e);
            }

            return input;
        });
    }

    static Schema getSchema(String schemaPath) throws RegurgitatorException {
        try {
            return FACTORY.newSchema(new StreamSource(getInputStreamForFile(schemaPath)));
        } catch (Exception e) {
            throw new RegurgitatorException("Error loading schema '" + schemaPath + "'", e);
        }
    }

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
