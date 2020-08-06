package me.mehedee.whatmask.storage.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@Entity
public class UsageHistory implements Parcelable {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.US);

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "start_time")
    public Date startTime;

    @ColumnInfo(name = "end_time")
    public Date endTime;

    @ColumnInfo(name = "mask_id")
    public int maskId;

    public UsageHistory() {
    }

    protected UsageHistory(Parcel in) {
        uid = in.readInt();
        try {
            startTime = sdf.parse(in.readString());
            endTime = sdf.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        maskId = in.readInt();
    }

    public static final Creator<UsageHistory> CREATOR = new Creator<UsageHistory>() {
        @Override
        public UsageHistory createFromParcel(Parcel in) {
            return new UsageHistory(in);
        }

        @Override
        public UsageHistory[] newArray(int size) {
            return new UsageHistory[size];
        }
    };

    @Override
    public String toString() {
        return "UsageHistory{" +
                "uid=" + uid +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", mask_id=" + maskId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(uid);
        dest.writeString(sdf.format(startTime));
        dest.writeString(sdf.format(endTime));
        dest.writeInt(maskId);
    }


}
