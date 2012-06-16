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

package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.TableContainer;
import de.brands4friends.daleq.internal.types.TableType;
import de.brands4friends.daleq.internal.types.TableTypeFactory;

public class TableBuilder implements Table {

    private final TableType tableType;
    private final List<Row> rows;

    public TableBuilder(final TableType tableType) {
        this.tableType = tableType;
        this.rows = Lists.newArrayList();
    }

    @Override
    public Table with(final Row... rows) {
        this.rows.addAll(Arrays.asList(rows));
        return this;
    }

    @Override
    public Table withSomeRows(final Iterable<Long> ids) {
        for (long id : ids) {
            this.rows.add(Daleq.aRow(id));
        }
        return this;
    }

    @Override
    public Table withSomeRows(final long... ids) {
        return withSomeRows(Longs.asList(ids));
    }

    @Override
    public Table withRowsUntil(final long maxId) {
        for (long i = 0; i < maxId; i++) {
            this.rows.add(Daleq.aRow(i));
        }
        return this;
    }

    @Override
    public Table allHaving(final FieldDef fieldDef, @Nullable final Object value) {
        for (Row row : rows) {
            row.f(fieldDef, value);
        }
        return this;
    }

    @Override
    public TableContainer build(final Context context) {
        final List<RowContainer> rowContainers = Lists.transform(rows, new Function<Row, RowContainer>() {
            @Override
            public RowContainer apply(final Row row) {
                return row.build(context, tableType);
            }
        });
        return new TableContainerImpl(tableType.getName(), rowContainers);
    }

    public static <T> TableBuilder aTable(final Class<T> fromClass) {
        final TableType tableType = new TableTypeFactory().create(fromClass);
        return new TableBuilder(tableType);
    }
}
