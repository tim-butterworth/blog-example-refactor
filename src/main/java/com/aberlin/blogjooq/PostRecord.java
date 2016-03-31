package com.aberlin.blogjooq;


import org.jooq.Table;
import org.jooq.impl.UpdatableRecordImpl;

class PostRecord extends UpdatableRecordImpl<PostRecord> {
    public PostRecord(Table<PostRecord> table) {
        super(table);
    }
}
