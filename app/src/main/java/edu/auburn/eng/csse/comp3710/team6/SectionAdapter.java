package edu.auburn.eng.csse.comp3710.team6;

import android.content.Context;
import android.content.Intent;
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
public class SectionAdapter extends BaseAdapter {

    private ArrayList<Section> sections;
    private Context ctx;
    private static LayoutInflater layoutInflater;
    private int subjectPosition;

    public SectionAdapter(Context ctx, ArrayList<Section> sections) {
        this.sections = sections;
        this.ctx = ctx;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public Object getItem(int position) {
        return sections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.adapter_layout, null);

        TextView tv = (TextView) rowView.findViewById(R.id.SubjectText);
        tv.setText(sections.get(position).getName());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //A button was clicked launch the section fragment.
                Log.i("SectionClick", sections.get(position).getName());
                Intent intent = new Intent(ctx, NotecardActivity.class);
                intent.putExtra("SubjectPos", subjectPosition);
                intent.putExtra("SectionPos", position);
                intent.putExtra("sectionName", sections.get(position).getName());

                ctx.startActivity(intent);
            }
        });
        return rowView;

    }
}
