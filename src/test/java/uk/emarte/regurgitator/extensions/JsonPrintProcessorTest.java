/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonPrintProcessorTest {
    @Test
    public void testListOfSimpleTypes() throws RegurgitatorException {
        JsonPrintProcessor processor = new JsonPrintProcessor();
        assertEquals("[\"Dave\",\"Wendy\",\"Harold\"]", processor.process(asList("Dave", "Wendy", "Harold"), null));
        assertEquals("[21,18,55]", processor.process(asList(21, 18, 55), null));
    }

    @Test
    public void testListOfObjectsWithMixedType() throws RegurgitatorException {
        JsonPrintProcessor processor = new JsonPrintProcessor();
        List<Person> list = asList(person("Dave", 21), person("Wendy", 18), person("Harold", 55));
        String json = (String) processor.process(list, null);
        assertEquals("[{\"name\":\"Dave\",\"age\":21},{\"name\":\"Wendy\",\"age\":18},{\"name\":\"Harold\",\"age\":55}]", json);
    }

    @Test
    public void testObjectWithListOfObjectsWithMixedTypes() throws RegurgitatorException {
        JsonPrintProcessor processor = new JsonPrintProcessor();
        Map<String, List<Person>> map = singletonMap("people", asList(person("Dave", 21), person("Wendy", 18), person("Harold", 55)));
        String json = (String) processor.process(map, null);
        assertEquals("{\"people\":[{\"name\":\"Dave\",\"age\":21},{\"name\":\"Wendy\",\"age\":18},{\"name\":\"Harold\",\"age\":55}]}", json);
    }

    @Test
    public void testPassThrough() throws RegurgitatorException {
        JsonPrintProcessor processor = new JsonPrintProcessor();
        assertNull(processor.process(null, new Message(null)));
    }

    private Person person(String name, int age) {
        return new Person(name, age);
    }

    private static class Person {
        public final String name;
        public final int age;

        private Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}