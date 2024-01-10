/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;

import javax.xml.xpath.*;
import java.util.Map;

import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.XPathUtil.strip;
import static uk.emarte.regurgitator.extensions.XmlUtil.getDocument;

public class XpathProcessor implements ValueProcessor {
    private static final Log log = Log.getLog(XpathProcessor.class);
    private final Map<String, String> namespaceUris;
    private final String xpath;

    public XpathProcessor(String xpath, Map<String, String> namespaceUris) {
        this.xpath = xpath;
        this.namespaceUris = namespaceUris;
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        log.debug("Applying xpath '{}'", xpath);

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
