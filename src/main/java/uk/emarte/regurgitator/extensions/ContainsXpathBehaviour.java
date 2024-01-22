/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Map;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.XPathUtil.strip;
import static uk.emarte.regurgitator.extensions.XmlUtil.getDocument;

public class ContainsXpathBehaviour implements ConditionBehaviour {
    private static final Log log = getLog(ContainsXpathBehaviour.class);
    private final Map<String, String> namespaceUris;

    public ContainsXpathBehaviour(Map<String, String> namespaceUris) {
        this.namespaceUris = namespaceUris;
    }

    @Override
    public boolean evaluate(Parameter parameter, Message message, String conditionValue, boolean expectation) throws RegurgitatorException {
        boolean contains = false;

        if (parameter != null) {
            try {
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();

                if (namespaceUris != null) {
                    xpath.setNamespaceContext(new SimpleNamespaceContext(namespaceUris));
                }

                XPathExpression expr = xpath.compile(conditionValue);
                Object value = strip(expr.evaluate(getDocument(stringify(parameter))));
                log.debug("Parameter " + (value != null ? "satisfies" : "does not satisfy") + " xpath '{}'", conditionValue);
                contains = value != null;
            } catch (XPathExpressionException e) {
                throw new RegurgitatorException("Error evaluating xpath", e);
            }
        }

        return contains == expectation;
    }
}
