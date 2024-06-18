/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.junit.Test;
import uk.emarte.regurgitator.core.RegurgitatorException;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

public class FreemarkerUtilTest {
    @Test
    public void testUtil() throws RegurgitatorException {
        assertEquals("Hello Martyn", FreemarkerUtil.buildFrom(singletonMap("name", "Martyn"), "Hello ${name}"));
    }

    @Test(expected = RegurgitatorException.class)
    public void testUtil_ioException() throws RegurgitatorException {
        FreemarkerUtil.buildFrom(singletonMap("name", "Martyn"), "Hello ${name");
    }

    @Test(expected = RegurgitatorException.class)
    public void testUtil_templateException() throws RegurgitatorException {
        FreemarkerUtil.buildFrom(singletonMap("name", "Martyn"), "Hello ${age}");
    }
}