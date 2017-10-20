package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.RegurgitatorException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import java.io.*;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;

class XmlSchemaUtil {
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

    static Schema getSchema(String schemaPath) throws RegurgitatorException {
        try {
            return factory.newSchema(new StreamSource(getInputStreamForFile(schemaPath)));
        } catch (Exception e) {
            throw new RegurgitatorException("Error loading schema '" + schemaPath + "'", e);
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
