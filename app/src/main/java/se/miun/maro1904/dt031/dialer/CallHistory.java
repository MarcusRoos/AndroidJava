package se.miun.maro1904.dt031.dialer;

import androidx.room.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class CallHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "lng")
    private String lng;
    @ColumnInfo(name = "lat")
    private String lat;


    public int getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public String getDate() {
        return date;
    }
    public String getLng() {
        return lng;
    }
    public String getLat() {
        return lat;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public Date compDate() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);
        try {
            d = dateFormat.parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
