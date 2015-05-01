package edu.auburn.eng.csse.comp3710.team6;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Tyler Hoover on 4/25/15.
 */
public class Subject implements Parcelable {

    private final String name; //Subject name ie Comp 3710
    private final ArrayList<Section> sections; //Stores all sections

    /**
     * Creates a new subject object. Typically used for new subjects
     * @param name - Name of this subject.
     */
    public Subject(String name) {
        this.name = name;
        sections = new ArrayList();
    }

    /**
     * Creates a subject object. Typically used for loading subjects.
     * @param name - Name of subject.
     * @param sections - Sections stored under this subject.
     */
    public Subject(String name, ArrayList<Section> sections) {
        this.name = name;
        this.sections = sections;
    }

    /**
     * Adds a new section to this object. Used when section has already been created.
     * @param section - Section to add to this subject.
     */
    public void addSection(Section section) {
        sections.add(section);
    }

    /**
     * Adds a new section to this object. Used for creating a brand new section.
     * @param sectionName - Name of section to create and add.
     */
    public void addSection(String sectionName) {

        sections.add(new Section(sectionName));
    }

    /**
     * Returns name of this subject.
     * @return - Subject name.
     */
    public String getSubjectName() {
        return name;
    }

    /**
     * Returns list of sections for this subject.
     * @return - List of sections.
     */
    public ArrayList<Section> getSections() {
        return sections;
    }

    public Section getSection(String name) {
       Section sec = null;
       for (Section temp : sections) {
           if (temp.getName().equals(name)) {
               sec = temp;
           }
       }
       if (sec == null) {
        sec = new Section(name);
        sections.add(sec);
       }
        return sec;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private Subject(Parcel in) {
        this.name = in.readString();
        sections = in.readArrayList(Section.class.getClassLoader());
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(sections);
    }
}
