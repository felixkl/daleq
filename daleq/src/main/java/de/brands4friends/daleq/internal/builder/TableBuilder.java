package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TableStructureFactory;

public class TableBuilder implements Table {

    private final TableStructure tableStructure;
    private final List<Row> rows;

    public TableBuilder(final TableStructure tableStructure) {
        this.tableStructure = tableStructure;
        this.rows = Lists.newArrayList();
    }

    @Override
    public Table with(Row... rows) {
        this.rows.addAll(Arrays.asList(rows));
        return this;
    }

    @Override
    public Table withSomeRows(Iterable<Object> substitutes) {
        // TODO
        return this;
    }

    public static <T> TableBuilder aTable(Class<T> fromClass) {
        final TableStructure tableStructure = new TableStructureFactory().create(fromClass);
        return new TableBuilder(tableStructure);
    }
}