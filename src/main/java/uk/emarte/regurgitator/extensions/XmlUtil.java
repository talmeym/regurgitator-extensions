/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.Caching;
import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.RegurgitatorException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import static uk.emarte.regurgitator.core.CacheProvider.Cache;
import static uk.emarte.regurgitator.core.Log.getLog;

class XmlUtil {
    private static final Log log = getLog(XmlUtil.class);

    static Document getDocument(String documentText) throws RegurgitatorException {
        Cache<Document> cache = Caching.getCache(Document.class);

        if (cache.contains(documentText)) {
            log.debug("Found existing xml document");
            return cache.get(documentText);
        }

        log.debug("Parsing xml document");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new ByteArrayInputStream(documentText.getBytes()));
            cache.set(documentText, document);
            return document;
        } catch (Exception e) {
            throw new RegurgitatorException("Error parsing xml document: ", e);
        }
    }
}
