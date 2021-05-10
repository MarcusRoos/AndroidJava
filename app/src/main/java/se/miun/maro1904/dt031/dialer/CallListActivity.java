package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CallListActivity extends AppCompatActivity {

    private TextView emptyHistory;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        init();
    }

    private void init() {
        setTitle("Call history");
        emptyHistory = findViewById(R.id.emptyCallHistory);
        recyclerView = findViewById(R.id.historyList);
        new DatabaseCaller(this::printHistory).execute();
    }

    private void printHistory(List<CallHistory> historyList) {

        if (historyList.isEmpty()) {
            emptyHistory.setVisibility(View.VISIBLE);
        }

        else {
            historyList.sort((date1, date2) -> {
                if (date1.getDate() == null || date2.getDate() == null)
                    return 0;
                return date1.compDate().compareTo(date2.compDate());
            });
            CalledAdapter adapter1 = new CalledAdapter(historyList);
            recyclerView.setAdapter(adapter1);
            emptyHistory.setVisibility(View.INVISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
