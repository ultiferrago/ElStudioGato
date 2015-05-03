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
        chap1.addNote(new Note("Chapter 1 card 1 front", "chapter 1 card 1 back"));
        chap1.addNote(new Note("Chapter 1 card 2 front", "chapter 1 card 2 back"));
        Section chap2 = new Section("Chapter 2");
        chap2.addNote(new Note("Chapter 2 card 1 front", "chapter 2 card 1 back"));
        chap2.addNote(new Note("Chapter 2 card 2 front", "chapter 2 card 2 back"));

        sub1.addSection(chap1);
        sub1.addSection(chap2);
        dummySubjects.add(sub1);

        Section sec1 = new Section("Section 1");
        sec1.addNote(new Note("Section 1 card 1 front", "Section 1 card 1 back"));
        sec1.addNote(new Note("Section 1 card 2 front", "Section 1 card 2 back"));
        Section sec2 = new Section("Section 2");
        sec2.addNote(new Note("Section 2 card 1 front", "Section 2 card 1 back"));
        sec2.addNote(new Note("Section 2 card 2 front", "Section 2 card 2 back"));

        sub2.addSection(sec1);
        sub2.addSection(sec2);
        dummySubjects.add(sub2);

        Section lec1 = new Section("Lecture 1");
        lec1.addNote(new Note("Lecture 1 card 1 front", "Lecture 1 card 1 back"));
        lec1.addNote(new Note("Lecture 1 card 2 front", "Lecture 1 card 2 back"));
        Section lec2 = new Section("Lecture 2");
        lec2.addNote(new Note("Lecture 2 card 1 front", "Lecture 2 card 1 back"));
        lec2.addNote(new Note("Lecture 2 card 2 front", "Lecture 2 card 2 back"));

        sub3.addSection(lec1);
        sub3.addSection(lec2);

        dummySubjects.add(sub3);

        return dummySubjects;
    }
}
