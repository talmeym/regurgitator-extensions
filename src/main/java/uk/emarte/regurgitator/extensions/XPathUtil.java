/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
class XPathUtil {
    static Object strip(Object extract) {
        // convert empty string into null
        if(extract != null && extract.equals("")) {
            extract = null;
        }

        // convert text node list into string list
        if (extract instanceof Collection) {
            Collection<Node> nodeCollection = (Collection<Node>) extract;

            if (nodeCollection.size() > 0) {
                List<Object> objs = new ArrayList<>();

                for (Node node : nodeCollection) {
                    if (node.getTextContent() != null && node.getTextContent().length() > 0) {
                        objs.add(node.getTextContent());
                    }
                }

                return objs;
            }

            // convert empty collection into null
            return null;
        }

        // convert NodeList into string list
        if(extract instanceof NodeList) {
            NodeList nodeList = (NodeList) extract;
            List<Object> list = new ArrayList<>(nodeList.getLength());

            for(int i = 0; i < nodeList.getLength(); i++) {
                list.add(nodeList.item(i).getTextContent());
            }

            return list;
        }

        // convert Node into string
        if (extract instanceof Node) {
            if (((Node) extract).getTextContent().length() > 0) {
                return ((Node)extract).getTextContent();
            }

            return null;
        }

        return extract;
    }
}
