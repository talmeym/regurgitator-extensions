/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;

import javax.xml.xpath.*;
import java.util.Map;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;
import static com.emarte.regurgitator.extensions.XmlUtil.getDocument;
import static com.emarte.regurgitator.extensions.XPathUtil.strip;

public class ContainsXpath implements ConditionBehaviour {
    private static final Log log = getLog(ContainsXpath.class);
    private final Map<String, String> namespaceUris;

    public ContainsXpath(Map<String, String> namespaceUris) {
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
                log.debug("Parameter " + (value != null ? "satisfies" : "does not satisfy") + " xpath '" + conditionValue + "'");
                contains = value != null;
            } catch (XPathExpressionException e) {
                throw new RegurgitatorException("Error evaluating xpath", e);
            }
        }

        return contains == expectation;
    }
}
