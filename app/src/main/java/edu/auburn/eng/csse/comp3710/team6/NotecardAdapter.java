package edu.auburn.eng.csse.comp3710.team6;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardAdapter extends RecyclerView.Adapter<NotecardAdapter.ViewHolder> {
    private ArrayList<Note> notecardList;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mQuestion;
        public ImageView correct;
        public ImageView incorrect;

        public ViewHolder(View v) {
            super(v);
            mQuestion = (TextView) v.findViewById(R.id.question);
            correct = (ImageView) v.findViewById(R.id.correct);
            incorrect = (ImageView) v.findViewById(R.id.incorrect);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotecardAdapter(ArrayList<Note> notecardList, Activity activity) {
        this.notecardList = notecardList;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotecardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notecard_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //***** OnClickListener for the notecard itself to reveal the answer *****//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),
                        "Question:: " + notecardList.get(position).getFront(),
                               Toast.LENGTH_SHORT).show();
                holder.mQuestion.setText(notecardList.get(position).getBack());
                activity.findViewById(R.id.correct).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.incorrect).setVisibility(View.VISIBLE);
            }
        });


        //***** OnClickListener for the 'correct' button *****//
        holder.correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),
                    "Aww... Sucks to suck",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //***** OnClickListener for the 'incorrect' button *****//
        holder.incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),
                    "You've been awarded a cookie!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.mQuestion.setText(notecardList.get(position).getFront());
        Log.d("KENNY", "Question: " + notecardList.get(position).getFront());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notecardList.size();
    }

    public ArrayList<Note> getNotecardList() {
        return notecardList;
    }
}
