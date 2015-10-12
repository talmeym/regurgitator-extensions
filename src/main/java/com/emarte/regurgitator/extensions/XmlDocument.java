package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.StringReader;

import static com.emarte.regurgitator.core.Caching.Cache;

class XmlDocument {
	private static Log log = Log.getLog(XmlDocument.class);

    static Document getDocument(String documentText) throws RegurgitatorException {
		Cache cache = Caching.getCache(XmlDocument.class);

		if (cache.hasValue(documentText)) {
			log.debug("Found existing xml document");
			return (Document) cache.getValue(documentText);
		}

		log.debug("Parsing xml document");
		Document document = parseXmlDocument(documentText);
		cache.setValue(documentText, document);
		return document;
    }

    private static Document parseXmlDocument(String documentText) throws RegurgitatorException {
        try {
            return new SAXReader().read(new StringReader(documentText));
        } catch (DocumentException e) {
            throw new RegurgitatorException("Error parsing xml document: ", e);
        }
    }
}
