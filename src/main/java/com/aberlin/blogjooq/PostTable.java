package com.aberlin.blogjooq;

import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

class PostTable extends TableImpl<PostRecord> {
    public final TableField<PostRecord, Integer> id = createField("id", SQLDataType.INTEGER, this);
    public final TableField<PostRecord, String> title = createField("title", SQLDataType.VARCHAR, this);
    public final TableField<PostRecord, String> contents = createField("contents", SQLDataType.VARCHAR, this);
    public final TableField<PostRecord, String> author = createField("author", SQLDataType.VARCHAR, this);

    public PostTable(String name) {
        super(name);
    }
}