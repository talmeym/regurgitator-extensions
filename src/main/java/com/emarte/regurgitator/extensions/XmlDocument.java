package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.StringReader;

import static com.emarte.regurgitator.core.CacheProvider.Cache;

class XmlDocument {
	private static final Log log = Log.getLog(XmlDocument.class);

    static Document getDocument(String documentText) throws RegurgitatorException {
		Cache<Document> cache = Caching.getCache(Document.class);

		if (cache.contains(documentText)) {
			log.debug("Found existing xml document");
			return cache.get(documentText);
		}

		log.debug("Parsing xml document");

		try {
			Document document = new SAXReader().read(new StringReader(documentText));;
			cache.set(documentText, document);
			return document;
		} catch (DocumentException e) {
			throw new RegurgitatorException("Error parsing xml document: ", e);
		}
    }
}
