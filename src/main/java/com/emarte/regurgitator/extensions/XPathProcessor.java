package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.dom4j.*;

import java.util.Map;

import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.XPathUtil.strip;
import static com.emarte.regurgitator.extensions.XmlDocument.getDocument;
import static org.dom4j.DocumentHelper.createXPath;

public class XPathProcessor implements ValueProcessor {
	private static final Log log = Log.getLog(XPathProcessor.class);

	private final Map<String, String> namespaces;
	private String xpath;

	public XPathProcessor(String xpath, Map<String, String> namespaces) {
		this.xpath = xpath;
		this.namespaces = namespaces;
	}

	@Override
	public Object process(Object value, Message message) throws RegurgitatorException {
		return process(getDocument(stringify(value)));
	}

	public Object process(Document document) throws RegurgitatorException {
		log.debug("Applying xpath '" + xpath + "'");
		XPath xpathSelector = createXPath(xpath);
		xpathSelector.setNamespaceURIs(namespaces);
		return strip(xpathSelector.evaluate(document));
	}
}
