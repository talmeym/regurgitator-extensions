/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.junit.Test;
import uk.emarte.regurgitator.core.RegurgitatorException;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class FreemarkerUtilTest {
    @Test
    public void testUtil() throws RegurgitatorException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Martyn");
        assertEquals("Hello Martyn", FreemarkerUtil.buildFrom(map, "Hello ${name}"));
    }
}