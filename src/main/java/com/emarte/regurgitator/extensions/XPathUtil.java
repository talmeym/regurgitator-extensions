package com.emarte.regurgitator.extensions;

import org.dom4j.Node;

import java.util.*;

@SuppressWarnings({"unchecked"})
public class XPathUtil {
	public static Object strip(Object extract) {
		if (extract instanceof Collection) {
			if (((Collection) extract).size() > 0) {
				List<Object> strings = new ArrayList<Object>();

				for (Node node : (Collection<Node>) extract) {
					if (node.getText() != null && node.getText().length() > 0) {
						strings.add(node.getText());
					}
				}

				return strings;
			}

			return null;
		}

		if (extract instanceof Node) {
			if (((Node) extract).getText().length() > 0) {
				return ((Node)extract).getText();
			}

			return null;
		}

		return extract;
	}
}
