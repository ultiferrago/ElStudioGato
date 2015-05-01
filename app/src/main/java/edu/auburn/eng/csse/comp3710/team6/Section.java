package edu.auburn.eng.csse.comp3710.team6;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tyler Hoover on 4/25/15.
 */
public class Section implements Parcelable {

    private final String sectionName; //Name of this section
    private final ArrayList<Note> notes; //List of the notes attached to this section.

    /**
     * Creates a new section object with the given name.
     * @param sectionName - Name of this section.
     */
    public Section(String sectionName) {
        this.sectionName = sectionName;
        notes = new ArrayList();
    }

    /**
     * Creates a new Section with a given name and list of note cards.
     * @param sectionName - Name of section.
     * @param notes - Notes attached to this section.
     */
    public Section(String sectionName, ArrayList<Note> notes) {
        this.sectionName = sectionName;
        this.notes = notes;
    }

    /**
     * Adds multiple note cards to this section.
     * @param notes - List of note cards to add.
     */
    public void addNotes(ArrayList<Note> notes) {
        for (Note note : notes) {
            this.notes.add(note);
        }
    }

    /**
     * Adds multiple note cards to this section.
     * @param notes - List of note cards to add.
     */
    public void addNotes(Note... notes) {
        for (Note note : notes) {
            this.notes.add(note);
        }
    }

    /**
     * Adds a single note card to this section.
     * @param note - Note to add.
     */
    public void addNote(Note note) {
        notes.add(note);
    }

    /**
     * Gets the name of this section.
     * @return - name of section.
     */
    public String getName() {
        return sectionName;
    }

    /**
     * Get the list of note cards attached to this section.
     * @return - list of note cards.
     */
    public ArrayList<Note> getNoteCards() {
        return (ArrayList<Note>)notes.clone();
    }

    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sectionName);
        dest.writeList(notes);
    }

    private Section(Parcel in) {
        sectionName = in.readString();
        notes = in.readArrayList(Note.class.getClassLoader());
    }
}
