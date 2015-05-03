package edu.auburn.eng.csse.comp3710.team6;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tyler Hoover on 4/25/15.
 */
public class Note implements Parcelable {

    private String front; //What is on the front of the study card.
    private String back; //What is on the back of the study card.

    /**
     * Nothing to really do here.
     */
    public Note() {

    }

    public Note(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Sets the front text of this card.
     *
     * @param text - Text for front of card.
     */
    public void setFront(String text) {
        front = text;
    }

    /**
     * Sets the back text of this card.
     *
     * @param text - Text for back of card.
     */
    public void setBack(String text) {
        back = text;
    }

    /**
     * Gets text for front of card.
     *
     * @return - front text.
     */
    public String getFront() {
        if (front == null) {
            return "";
        }
        return front;
    }

    /**
     * Gets text for back of card.
     *
     * @return - back text.
     */
    public String getBack() {
        if (back == null) {
            return "";
        }
        return back;
    }


    ////////////////////////////////////////////////////////////////
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(front);
        dest.writeString(back);
    }

    private Note(Parcel in) {
        front = in.readString();
        back = in.readString();
    }


}
