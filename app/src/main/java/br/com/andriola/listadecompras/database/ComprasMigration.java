package br.com.andriola.listadecompras.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class ComprasMigration {

    @SuppressWarnings("WeakerAccess")
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }

    };
    static final Migration[] TODAS_MIGRATION= {MIGRATION_1_2};
}
