package com.aberlin.blogjooq;

import org.jooq.Table;
import org.jooq.impl.UpdatableRecordImpl;

class CommentRecord extends UpdatableRecordImpl<CommentRecord> {
    public CommentRecord(Table<CommentRecord> table) {
        super(table);
    }
}