/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import uk.emarte.regurgitator.core.RegurgitatorException;

import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;

public class JsonSchemaUtil {
    static JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

    static JsonSchema getSchema(String schemaPath) throws RegurgitatorException {
        try {
            return factory.getSchema(getInputStreamForFile(schemaPath));
        } catch (Exception e) {
            throw new RegurgitatorException("Error loading schema '" + schemaPath + "'", e);
        }
    }
}
