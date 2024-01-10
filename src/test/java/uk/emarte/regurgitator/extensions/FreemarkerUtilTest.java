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
    public void testThis() throws RegurgitatorException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Miles");
        assertEquals("Hello Miles", FreemarkerUtil.buildFrom(map, "Hello ${name}"));
    }
}