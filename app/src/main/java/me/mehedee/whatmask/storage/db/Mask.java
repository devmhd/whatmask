package me.mehedee.whatmask.storage.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Mask implements Parcelable {
    private static final DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "model")
    public String model;

    @ColumnInfo(name = "purchased_at")
    public LocalDateTime purchasedAt;

    @ColumnInfo(name = "is_safe_after_purchase")
    public Boolean isSafeRightAfterPurchase;

    @ColumnInfo(name = "is_active")
    public Boolean isActive;

    public Mask() {
    }

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (ldt, typeOfT, context) ->
                new JsonPrimitive(ldt.format(localTimeFormatter))
        );

        Gson gson = gsonBuilder.create();

        return gson.toJson(this);
    }

    public static Mask fromJson(String json) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json1, typeOfT, context) ->
                LocalDateTime.parse(json1.getAsString(), localTimeFormatter)
        );

        Gson gson = gsonBuilder.create();

        return gson.fromJson(json, Mask.class);
    }

    @Override
    public String toString() {
        return "Mask{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", purchasedAt=" + purchasedAt +
                ", isSafeRightAfterPurchase=" + isSafeRightAfterPurchase +
                ", isActive=" + isActive +
                '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(uid);
        out.writeString(name);
        out.writeString(model);
        out.writeString(purchasedAt.format(localTimeFormatter));
        out.writeInt(isSafeRightAfterPurchase ? 1 : 0);
        out.writeInt(isActive ? 1 : 0);
    }

    public static final Parcelable.Creator<Mask> CREATOR
            = new Parcelable.Creator<Mask>() {
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    private Mask(Parcel in) {
        uid = in.readInt();
        name = in.readString();
        model = in.readString();
        purchasedAt = LocalDateTime.parse(in.readString(), localTimeFormatter);
        isSafeRightAfterPurchase = in.readInt() > 0;
        isActive = in.readInt() > 0;
    }
}
