package me.mehedee.whatmask.storage.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Mask.class, UsageHistory.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class DB extends RoomDatabase {
    public abstract MaskDao dao();

    public static DB INSTANCE;

    public static MaskDao getDao(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DB.class, "maskdb").build();
        }

        return INSTANCE.dao();
    }

//    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("UPDATE Mask SET purchased_at = " + new Date(2020, 1, 1).getTime() + ", is_safe_after_purchase = 1");
//        }
//    };

}
