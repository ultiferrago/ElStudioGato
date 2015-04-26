package edu.auburn.eng.csse.comp3710.team6;

import android.content.Context;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardItem {

    private String state;
    private String capital;

    Context context;

    public NotecardItem() {}

    public NotecardItem(Context context) {
        this.context = context;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String captial) {
        this.capital = captial;
    }
}
