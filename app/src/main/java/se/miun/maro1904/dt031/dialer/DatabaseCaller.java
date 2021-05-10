package se.miun.maro1904.dt031.dialer;

import android.os.AsyncTask;
import java.util.List;

public class DatabaseCaller extends AsyncTask<Void, Void, List<CallHistory>> {
    private final OnFetchFinishedListener listener;

    @Override
    protected List<CallHistory> doInBackground(Void... voids) {
        return MainActivity.DATABASE.historyDao().getAll();
    }

    @Override
    protected void onPostExecute(List<CallHistory> result) {
        listener.onFetchFinished(result);
    }

    public DatabaseCaller(OnFetchFinishedListener listener) {
        this.listener = listener;
    }

    public interface OnFetchFinishedListener {
        void onFetchFinished(List<CallHistory> result);
    }

}