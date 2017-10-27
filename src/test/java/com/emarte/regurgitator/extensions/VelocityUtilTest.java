/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.RegurgitatorException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class VelocityUtilTest {
    @Test
    public void testThis() throws RegurgitatorException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Miles");
        assertEquals("Hello Miles", VelocityUtil.buildFrom(map, "Hello ${name}"));
    }
}