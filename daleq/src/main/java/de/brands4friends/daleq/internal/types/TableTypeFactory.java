/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.internal.types;

import java.util.List;

import de.brands4friends.daleq.TableDef;

public class TableTypeFactory {

    private final FieldScanner fieldScanner = new FieldScanner();

    public <T> TableType create(final Class<T> fromClass) {

        final TableDef tableDef = fromClass.getAnnotation(TableDef.class);
        if (tableDef == null) {
            throw new IllegalArgumentException("Expected @TableDef on class '" + fromClass.getCanonicalName() + "'");
        }

        final List<FieldType> fields = fieldScanner.scan(fromClass);
        return new TableType(tableDef.value(), fields);
    }
}