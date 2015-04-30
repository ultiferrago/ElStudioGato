package edu.auburn.eng.csse.comp3710.team6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ferrago on 4/29/15.
 */
public class SubjectDeleteAdapter extends BaseAdapter {

    private ArrayList<Subject> subjects;
    private Context ctx;
    private static LayoutInflater layoutInflater;
    private int subjectPosition;

    private final OrganizationDeleteFragment mODF;

    public SubjectDeleteAdapter(Context ctx, OrganizationDeleteFragment fragRequester) {
        this.ctx = ctx;
        this.subjects = MainActivity.subjects;
        mODF = fragRequester;
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
        View rowView = layoutInflater.inflate(R.layout.delete_list_item_layout, null);

        final TextView tv = (TextView) rowView.findViewById(R.id.item_delete_name);
        final CheckBox rb = (CheckBox)rowView.findViewById(R.id.item_delete_box);
        tv.setText(subjects.get(position).getSubjectName());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb.toggle();
                mODF.toggleDeleteItem(position);
            }
        });
        return rowView;

    }
}
