/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Map;

import static javax.xml.xpath.XPathConstants.NODESET;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.XPathUtil.strip;
import static uk.emarte.regurgitator.extensions.XmlUtil.getDocument;

public class XpathProcessor implements ValueProcessor {
    private static final Log log = Log.getLog(XpathProcessor.class);
    private final String xpath;
    private final XPathExpression xPathExpression;

    public XpathProcessor(String xpath, Map<String, String> namespaceUris) throws RegurgitatorException {
        this.xpath = xpath;

        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xPath = xPathfactory.newXPath();

            if (namespaceUris != null) {
                xPath.setNamespaceContext(new SimpleNamespaceContext(namespaceUris));
            }

            xPathExpression = xPath.compile(xpath);
        } catch(XPathExpressionException xee) {
            throw new RegurgitatorException("Error compiling xpath '" + xpath + "'", xee);
        }
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        if(value != null) {
            return processXPath(value);
        }

        log.warn("No value to process");
        return null;
    }

    Object processXPath(Object value) throws RegurgitatorException {
        try {
            log.debug("Applying xpath '{}'", xpath);
            return strip(xPathExpression.evaluate(getDocument(stringify(value)), NODESET));
        } catch (XPathExpressionException xee) {
            throw new RegurgitatorException("Error evaluating xpath", xee);
        }
    }
}
