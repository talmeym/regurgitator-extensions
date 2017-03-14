package com.emarte.regurgitator.extensions;

import org.w3c.dom.*;

import java.util.*;

@SuppressWarnings({"unchecked"})
public class XPathUtil {
	public static Object strip(Object extract) {
		if(extract != null && extract.equals("")) {
			extract = null;
		}

		if (extract instanceof Collection) {
			if (((Collection) extract).size() > 0) {
				List<Object> objs = new ArrayList<Object>();

				for (Node node : (Collection<Node>) extract) {
					if (node.getTextContent() != null && node.getTextContent().length() > 0) {
						objs.add(node.getTextContent());
					}
				}

				return objs;
			}

			return null;
		}

		if(extract instanceof NodeList) {
			NodeList nodeList = (NodeList) extract;
			List<Object> list = new ArrayList<Object>(nodeList.getLength());

			for(int i = 0; i < nodeList.getLength(); i++) {
				list.add(nodeList.item(i).getTextContent());
			}

			return list.size() == 1 ? list.get(0) : list;
		}

		if (extract instanceof Node) {
			if (((Node) extract).getTextContent().length() > 0) {
				return ((Node)extract).getTextContent();
			}

			return null;
		}

		return extract;
	}
}
