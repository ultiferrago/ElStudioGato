package edu.auburn.eng.csse.comp3710.team6.database;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.Note;
import edu.auburn.eng.csse.comp3710.team6.Section;
import edu.auburn.eng.csse.comp3710.team6.Subject;

/**
 * Created by Ferrago on 4/28/15.
 */
public class DummyDatabase {

    public static ArrayList<Subject> getDummySubjects() {
        ArrayList<Subject> dummySubjects = new ArrayList();

        Subject sub1 = new Subject("Subject 1");
        Subject sub2 = new Subject("Subject 2");
        Subject sub3 = new Subject("Subject 3");

        Section chap1 = new Section("Chapter 1");
        chap1.addNote(new Note("This is the front", "This is the back"));
        chap1.addNote(new Note("Chapter 1", "Is horrible"));
        Section chap2 = new Section("Chapter 2");
        chap2.addNote(new Note("This is the front", "This is the back"));
        chap2.addNote(new Note("This is the subject 1", "chapter 2 card"));

        sub1.addSection(chap1);
        sub1.addSection(chap2);
        dummySubjects.add(sub1);

        Section sec1 = new Section("Section 1");
        sec1.addNote(new Note("This is the front", "This is the back"));
        sec1.addNote(new Note("Apple", "Pie"));
        Section sec2 = new Section("Section 2");
        sec2.addNote(new Note("This is the front", "This is the back"));
        sec2.addNote(new Note("Grilled", "Cheese"));

        sub2.addSection(sec1);
        sub2.addSection(sec2);
        dummySubjects.add(sub2);

        Section lec1 = new Section("Lecture 1");
        Note note9 = new Note("This is the front", "This is the back");
        Note note10 = new Note("Potato", "Soup");
        Section lec2 = new Section("Lecture 2");
        Note note11 = new Note("This is the front", "This is the back");
        Note note12 = new Note("Blah", "Blah");



        return dummySubjects;
    }
}
