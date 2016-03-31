package com.aberlin.blogjooq;

import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

class CommentTable extends TableImpl<CommentRecord> {
    public final TableField<CommentRecord, Long> id = createField("id", SQLDataType.BIGINT, this);
    public final TableField<CommentRecord, String> message = createField("message", SQLDataType.VARCHAR, this);
    public final TableField<CommentRecord, Integer> postId = createField("post_id", SQLDataType.INTEGER, this);

    public CommentTable(String name) {
        super(name);
    }
}