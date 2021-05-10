package se.miun.maro1904.dt031.dialer;

import androidx.room.*;

@Database(entities = {CallHistory.class}, version = 1, exportSchema = false)
public abstract class CallHistoryDB extends RoomDatabase {
    public abstract CallHistoryDAO historyDao();
}