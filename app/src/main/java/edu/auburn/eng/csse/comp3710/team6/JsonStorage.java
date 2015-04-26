package edu.auburn.eng.csse.comp3710.team6;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by kennystreit on 1/27/15.
 */
public class JsonStorage {

    private String mJson;
    private Context mContext;

    public JsonStorage(Context context) {
        mJson = "";
        this.mContext = context;
    }

    public JsonStorage(String json, Context context) {
        this.mJson = json;
        this.mContext = context;
    }

    /******************************************************************************************************************************************
     * Loads the json array into string form
     ******************************************************************************************************************************************
     * @return The full string of the json array
     * @throws java.io.IOException
     * @throws org.json.JSONException
     ******************************************************************************************************************************************/
    public String readNotecardsAsset() throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(mContext.getAssets().open(
                    "notecards.txt")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public ArrayList<NotecardItem> createNotecardItem(String json) throws JSONException {
        JSONObject wholeJSON = new JSONObject(json);
        JSONArray notecards = wholeJSON.getJSONArray("notecards");

        ArrayList<NotecardItem> totalDrinkList = new ArrayList<>();
        for (int i = 0; i < notecards.length(); i++) {
            NotecardItem item = new NotecardItem();
            JSONObject object = notecards.getJSONObject(i);
            item.setState(object.getString("state"));
            item.setCapital(object.getString("capital"));
            totalDrinkList.add(item);
        }
        return totalDrinkList;
    }

    /*********************************************************************************************************************************
     * This will write the actual object to the text file.
     *********************************************************************************************************************************
     * @param outer It's the outermost json object that encompasses all tabs
     *********************************************************************************************************************************/
    public void writeJSON(JSONObject outer) {

        FileOutputStream fos;
        try {
            fos = mContext.openFileOutput("notecards.txt", Context.MODE_PRIVATE);

            fos.write(outer.toString().getBytes());

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
