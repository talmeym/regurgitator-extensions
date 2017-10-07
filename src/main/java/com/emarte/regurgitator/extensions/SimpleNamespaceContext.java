/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.*;

import static java.util.Collections.EMPTY_SET;

class SimpleNamespaceContext implements NamespaceContext {
    private final Map<String, String> urisByPrefix = new HashMap<String, String>();
    private final Map<String, Set<String>> prefixesByURI = new HashMap<String, Set<String>>();

    SimpleNamespaceContext(Map<String, String> namespaces) {
        addNamespace(XMLConstants.XML_NS_PREFIX, XMLConstants.XML_NS_URI);
        addNamespace(XMLConstants.XMLNS_ATTRIBUTE, XMLConstants.XMLNS_ATTRIBUTE_NS_URI);

        for(String prefix: namespaces.keySet()) {
            addNamespace(prefix, namespaces.get(prefix));
        }
    }

    private synchronized void addNamespace(String prefix, String namespaceURI) {
        urisByPrefix.put(prefix, namespaceURI);
        if (prefixesByURI.containsKey(namespaceURI)) {
            (prefixesByURI.get(namespaceURI)).add(prefix);
        } else {
            Set<String> set = new HashSet<String>();
            set.add(prefix);
            prefixesByURI.put(namespaceURI, set);
        }
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("prefix cannot be null");
        if (urisByPrefix.containsKey(prefix))
            return urisByPrefix.get(prefix);
        else
            return XMLConstants.NULL_NS_URI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return (String) getPrefixes(namespaceURI).next();
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        if (namespaceURI == null)
            throw new IllegalArgumentException("namespaceURI cannot be null");
        if (prefixesByURI.containsKey(namespaceURI)) {
            return prefixesByURI.get(namespaceURI).iterator();
        } else {
            return EMPTY_SET.iterator();
        }
    }

}