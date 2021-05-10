package se.miun.maro1904.dt031.dialer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalledAdapter extends RecyclerView.Adapter<CalledAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView dateView;
        public TextView coordView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.historyNumber);
            dateView = itemView.findViewById(R.id.historyDate);
            coordView = itemView.findViewById(R.id.historyCoords);
        }
    }

    private final List<CallHistory> mContacts;

    public CalledAdapter(List<CallHistory> contacts) {
        mContacts = contacts;
    }

    @NonNull
    @Override
    public CalledAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.callhistory, parent, false);
        return new ViewHolder(contactView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CalledAdapter.ViewHolder holder, int position) {
        CallHistory contact = mContacts.get(position);

        TextView listNumber = holder.nameView;
        TextView listDate = holder.dateView;
        TextView listCoordinates = holder.coordView;

        listNumber.setText(contact.getNumber());
        listDate.setText(contact.getDate());
        listCoordinates.setText(contact.getLat() + ", " + contact.getLng());


    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

}
