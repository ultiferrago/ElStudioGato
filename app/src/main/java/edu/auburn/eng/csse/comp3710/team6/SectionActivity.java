package edu.auburn.eng.csse.comp3710.team6;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by kennystreit on 4/23/15.
 */
public class SectionActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section_layout);

        Toast.makeText(getApplicationContext(), "You have entered Section List", Toast.LENGTH_SHORT).show();
    }
}
