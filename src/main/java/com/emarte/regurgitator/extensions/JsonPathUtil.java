/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import net.minidev.json.JSONObject;

import java.util.*;

import static net.minidev.json.JSONObject.toJSONString;

class JsonPathUtil {
    @SuppressWarnings("unchecked")
    static Object strip(Object extract) {
        if (extract instanceof Collection) {
            if (((Collection) extract).size() > 0) {
                List<Object> objs = new ArrayList<Object>();

                for (Object obj : (Collection) extract) {
                    if(obj instanceof JSONObject) {
                        objs.add(((JSONObject) obj).toJSONString());
                    } else {
                        objs.add(obj);
                    }
                }

                return objs;
            }

            return null;
        }

        if(extract instanceof JSONObject) {
            return ((JSONObject)extract).toJSONString();
        }

        if(extract instanceof LinkedHashMap) {
            return toJSONString((LinkedHashMap) extract);
        }

        return extract;
    }
}
