package edu.auburn.eng.csse.comp3710.team6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kennystreit on 4/26/15.
 */
public class NotecardEditFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Note> notecardList = new ArrayList<>();

    public NotecardEditFragment(ArrayList<Note> notes) {
        this.notecardList = notes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.notecard_fragment, container, false);

        /*JsonStorage jsonStorage = new JsonStorage(getActivity());
        try {
            String notecardJSON = jsonStorage.readNotecardsAsset();
            Log.d("KENNY", "Drink JSON: " + notecardJSON);
            notecardList = jsonStorage.createNotecardItem(notecardJSON);
            Log.d("KENNY", "Success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException ej) {
            Log.d("KENNY", "JSON Failed: " + ej);
            ej.printStackTrace();
        }*/

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NotecardEditAdapter(notecardList);
        mRecyclerView.setAdapter(mAdapter);

        Log.d("KENNY", "Fragment created...");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.notecard_menu, menu);
        Log.d("KENNY", "Options Menu Changed...");
    }
}
