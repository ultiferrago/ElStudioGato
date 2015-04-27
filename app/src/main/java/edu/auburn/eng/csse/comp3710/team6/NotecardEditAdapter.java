package edu.auburn.eng.csse.comp3710.team6;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardEditAdapter extends RecyclerView.Adapter<NotecardEditAdapter.ViewHolder> {
    private ArrayList<NotecardItem> notecardList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mQuestion;
        public TextView mAnswer;
        public ViewHolder(View v) {
            super(v);
            mQuestion = (EditText) v.findViewById(R.id.question);
            mAnswer = (EditText) v.findViewById(R.id.answer);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotecardEditAdapter(ArrayList<NotecardItem> notecardList) {
        this.notecardList = notecardList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotecardEditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notecard_preview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.mQuestion.setText(notecardList.get(position).getState());
        holder.mAnswer.setText(notecardList.get(position).getCapital());
        Log.d("KENNY", "Question: " + notecardList.get(position).getState());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notecardList.size();
    }
}
