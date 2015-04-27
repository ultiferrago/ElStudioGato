package edu.auburn.eng.csse.comp3710.team6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Tyler Hoover on 4/25/15.
 */
public class Subject {

    private final String name; //Subject name ie Comp 3710
    private final HashMap<String, Section> sections; //Stores all sections

    /**
     * Creates a new subject object. Typically used for new subjects
     * @param name - Name of this subject.
     */
    public Subject(String name) {
        this.name = name;
        sections = new HashMap();
    }

    /**
     * Creates a subject object. Typically used for loading subjects.
     * @param name - Name of subject.
     * @param sections - Sections stored under this subject.
     */
    public Subject(String name, ArrayList<Section> sections) {
        this.name = name;
        this.sections = new HashMap();
        for (Section sec : sections) {
            this.sections.put(sec.getName(), sec);
        }
    }

    /**
     * Adds a new section to this object. Used when section has already been created.
     * @param section - Section to add to this subject.
     */
    public void addSection(Section section) {
        sections.put(section.getName(), section);
    }

    /**
     * Adds a new section to this object. Used for creating a brand new section.
     * @param sectionName - Name of section to create and add.
     */
    public void addSection(String sectionName) {

        sections.put(sectionName, new Section(sectionName));
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
    public Collection<Section> getSections() {
        return sections.values();
    }

    public Section getSection(String name) {
       Section sec = sections.get(name);
        if (sec == null) {
            sec = new Section(name);
        }
        return sec;
    }
}
