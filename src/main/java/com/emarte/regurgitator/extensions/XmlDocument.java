package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.w3c.dom.Document;

import javax.xml.parsers.*;
import java.io.ByteArrayInputStream;

import static com.emarte.regurgitator.core.CacheProvider.Cache;
import static com.emarte.regurgitator.core.Log.getLog;

class XmlDocument {
	private static final Log log = getLog(XmlDocument.class);

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
