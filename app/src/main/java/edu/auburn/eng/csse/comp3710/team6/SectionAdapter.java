package edu.auburn.eng.csse.comp3710.team6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kennystreit on 4/23/15.
 */
public class SectionAdapter extends ArrayAdapter<String> {
    private final Context context;
    ArrayList<HashMap<String, String>> sectionList;

    /* Constructor */
    public SectionAdapter(Context context, ArrayList<HashMap<String, String>> sectionList) {
        super(context, R.layout.section_listitem, new String[sectionList.size()]);
        this.context = context;
        this.sectionList = sectionList;
    }

    /******************************************************************************************
     * Used to set the ListView in the Section Activity
     ******************************************************************************************
     * @param position Iterates through ListView and populates each one
     * @param convertView
     * @param parent
     * @return
     ******************************************************************************************/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.section_listitem, parent, false);
        TextView subject = (TextView) rowView.findViewById(R.id.subject);
        TextView section = (TextView) rowView.findViewById(R.id.section);
        subject.setText("Test Section 1");
        section.setText("Test Subject 1");

        return rowView;
    }
}
