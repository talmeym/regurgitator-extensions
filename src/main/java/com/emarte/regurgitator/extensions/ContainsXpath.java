package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.dom4j.XPath;

import java.util.Map;

import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.XPathUtil.strip;
import static com.emarte.regurgitator.extensions.XmlDocument.getDocument;
import static org.dom4j.DocumentHelper.createXPath;

public class ContainsXpath implements ConditionBehaviour {
	private static final Log log = Log.getLog(ContainsXpath.class);

	private final Map<String, String> namespaceUris;

	public ContainsXpath(Map<String, String> namespaceUris) {
		this.namespaceUris = namespaceUris;
	}

	@Override
	public boolean evaluate(Parameter parameter, Message message, String conditionValue, boolean expectation) throws RegurgitatorException {
		boolean contains = false;

		if (parameter != null) {
			XPath xpathSelector = createXPath(conditionValue);

			if (namespaceUris != null) {
				xpathSelector.setNamespaceURIs(namespaceUris);
			}

			Object value = strip(xpathSelector.evaluate(getDocument(stringify(parameter))));
			log.debug("Parameter " + (value != null ? "satifies" : "does not satisfy") + " xpath '" + conditionValue + "'");
			contains = value != null;
		}

		return contains == expectation;
	}
}
