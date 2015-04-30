package edu.auburn.eng.csse.comp3710.team6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tyler Hoover on 4/27/15.
 */
public class SubjectAdapter extends BaseAdapter {

    private ArrayList<Subject> subjects;
    private Context ctx;
    private static LayoutInflater layoutInflater;

    public SubjectAdapter(Context ctx, ArrayList<Subject> subjects) {
        this.subjects = subjects;
        this.ctx = ctx;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.adapter_layout, null);

        TextView tv = (TextView) rowView.findViewById(R.id.SubjectText);
        tv.setText(subjects.get(position).getSubjectName());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //A button was clicked launch the section fragment.
                Log.i("SubjectClick", subjects.get(position).getSubjectName());
                MainActivity.toSectionFragment(subjects.get(position));
            }
        });
        return rowView;

    }
}
