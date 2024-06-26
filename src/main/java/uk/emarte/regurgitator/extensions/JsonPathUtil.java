/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

class JsonPathUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static Object strip(Object extract) {
        // convert list entries that are LinkedHashMaps into json strings
        if (extract instanceof Collection) {
            List<Object> objs = new ArrayList<>();

            for (Object obj : (Collection<?>) extract) {
                if(obj instanceof LinkedHashMap) {
                    objs.add(toJsonString(obj));
                } else {
                    objs.add(obj);
                }
            }

            return objs;
        }

        // convert LinkedHashMap into json string
        if(extract instanceof LinkedHashMap) {
            return toJsonString(extract);
        }

        return extract;
    }

    private static Object toJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj;
        }
    }
}
