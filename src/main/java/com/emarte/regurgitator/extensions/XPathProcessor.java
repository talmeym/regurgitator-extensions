package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import javax.xml.xpath.*;
import java.util.Map;

import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.XmlDocument.getDocument;
import static com.emarte.regurgitator.extensions.XpathUtil.strip;

public class XpathProcessor implements ValueProcessor {
	private static final Log log = Log.getLog(XpathProcessor.class);

	private final Map<String, String> namespaceUris;
	private String xpath;

	public XpathProcessor(String xpath, Map<String, String> namespaceUris) {
		this.xpath = xpath;
		this.namespaceUris = namespaceUris;
	}

	@Override
	public Object process(Object value, Message message) throws RegurgitatorException {
		log.debug("Applying xpath '" + xpath + "'");

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();

		if (namespaceUris != null) {
			xpath.setNamespaceContext(new SimpleNamespaceContext(namespaceUris));
		}

		try {
			XPathExpression expression = xpath.compile(this.xpath);
			return strip(expression.evaluate(getDocument(stringify(value)), XPathConstants.NODESET));
		} catch (XPathExpressionException e) {
			throw new RegurgitatorException("Error evaluating xpath", e);
		}
	}
}
