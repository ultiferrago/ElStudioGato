package edu.auburn.eng.csse.comp3710.team6;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardAdapter extends RecyclerView.Adapter<NotecardAdapter.ViewHolder> {
    private ArrayList<Note> notecardList;
    private static HashMap<Integer, String> selectedMap = new HashMap();

    private TextView percent;

    private static String RIGHT = "right";
    private String WRONG = "wrong";
    private String JUST_SELECTED = "";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mQuestion;
        public ImageView correct;
        public ImageView incorrect;
        public LinearLayout background;
        public CardView cview;

        public ViewHolder(View v) {
            super(v);
            mQuestion = (TextView) v.findViewById(R.id.question);
            correct = (ImageView) v.findViewById(R.id.correct);
            incorrect = (ImageView) v.findViewById(R.id.incorrect);
            background = (LinearLayout) v.findViewById(R.id.cardLayout);
            cview = (CardView) v.findViewById(R.id.card_view);
        }
    }

    public void setTextView(TextView tv) {
        percent = tv;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotecardAdapter(TextView percent, ArrayList<Note> notecardList) {
        this.notecardList = notecardList;
        this.percent = percent;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotecardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notecard_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (selectedMap.containsKey((Integer)position)) {
            holder.mQuestion.setText(notecardList.get(position).getBack());
            holder.correct.setVisibility(View.VISIBLE);
            holder.incorrect.setVisibility(View.VISIBLE);

            if (selectedMap.get((Integer)position) == RIGHT) {
                holder.background.setBackgroundResource(android.R.color.holo_green_light);
            } else if (selectedMap.get((Integer)position) == WRONG) {
                holder.background.setBackgroundResource(android.R.color.holo_red_light);
            }
        } else {
            holder.mQuestion.setText(notecardList.get(position).getFront());
        }
        //***** OnClickListener for the notecard itself to reveal the answer *****//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mQuestion.setText(notecardList.get(position).getBack());
                holder.correct.setVisibility(View.VISIBLE);
                holder.incorrect.setVisibility(View.VISIBLE);
                selectedMap.put((Integer)position, JUST_SELECTED);
            }
        });


        //***** OnClickListener for the 'correct' button *****//
        holder.correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.background.setBackgroundResource(android.R.color.holo_green_light);
                selectedMap.put((Integer) position, RIGHT);

                percent.setText(rightPercent() + "%");
                percent.invalidate();
               //holder.cview.setBackground(v.getResources().getDrawable(R.drawable.right_background));
            }
        });

        //***** OnClickListener for the 'incorrect' button *****//
        holder.incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.background.setBackgroundResource(android.R.color.holo_red_light);
                selectedMap.put((Integer) position, WRONG);
                percent.setText(rightPercent() + "%");
                percent.invalidate();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notecardList.size();
    }

    public ArrayList<Note> getNotecardList() {
        return notecardList;
    }

    public HashMap<Integer, String> getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(HashMap<Integer, String> selected) {
        selectedMap = (HashMap<Integer, String>)selected.clone();
    }



    public void updateNotes(ArrayList<Note> notes) {
        this.notecardList = notes;
    }

    public static int rightPercent() {
        double total = selectedMap.size();
        if (total <=0) {
            total = 1;
        }
        double right = 0;
        for (String temp : selectedMap.values()) {
            if (temp == RIGHT) {
                right++;
            }
        }

        return (int)((right / total) * 100);
    }
}
