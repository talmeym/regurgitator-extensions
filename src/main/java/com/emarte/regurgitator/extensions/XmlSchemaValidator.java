package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.w3c.dom.ls.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.io.*;

import static com.emarte.regurgitator.core.FileUtil.*;
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
					// do something
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
		String xml = stringify(value);

		try {
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
            validator.validate(source);
            log.debug("Value successfully validated against schema '" + schemaName + "'");
			return value;
        } catch (Exception e) {
			log.warn("Value did not validate against schema '" + schemaName + "': " + e);
			throw new RegurgitatorException("Value did not validate against schema + '" + schemaName + "'", e);
        }
    }

	private static class ByteStreamLSInput implements LSInput {
		private UnsupportedOperationException uoe = new UnsupportedOperationException("Can only use byte stream");
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
