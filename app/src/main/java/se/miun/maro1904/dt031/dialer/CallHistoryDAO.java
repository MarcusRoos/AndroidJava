package se.miun.maro1904.dt031.dialer;

import androidx.room.*;

import java.util.List;

@Dao
public interface CallHistoryDAO {

    @Query("SELECT * FROM CallHistory")
    List<CallHistory> getAll();

    @Insert
    void insert(CallHistory entity);

    @Query("DELETE FROM CallHistory")
    public void deleteAll();

}
