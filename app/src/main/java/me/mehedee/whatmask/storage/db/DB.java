package me.mehedee.whatmask.storage.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Mask.class, UsageHistory.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class DB extends RoomDatabase {
    public abstract MaskDao dao();

    public static DB INSTANCE;

    public static MaskDao getDao(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DB.class, "maskdb")
                    .addMigrations(MIGRATION_3_4)
                    .addMigrations(MIGRATION_4_5)
                    .addMigrations(MIGRATION_5_6)
                    .build();
        }

        return INSTANCE.dao();
    }

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTEr table Mask add column is_active int");
            database.execSQL("update Mask set is_active = 1");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DELETE from UsageHistory WHERE mask_Id not IN (select uid from Mask)");
        }
    };

    private static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER table Mask add column style int");
            database.execSQL("update Mask set style = 0");
        }
    };

}
