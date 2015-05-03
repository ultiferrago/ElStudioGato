package edu.auburn.eng.csse.comp3710.team6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import edu.auburn.eng.csse.comp3710.team6.Note;
import edu.auburn.eng.csse.comp3710.team6.Section;
import edu.auburn.eng.csse.comp3710.team6.Subject;


public class DatabaseHelper extends SQLiteOpenHelper {

    //Singleton Instance
    private static DatabaseHelper m_instance;

    private long lastUpdateTime = 0;

    //Database infromation.
    private static final String DB_NAME = "StudyManager"; // Database name
    private static final int VERSION = 1; // Used to rebuild database if we make changes to structure.

    //Subject Table information.
    public static final String TABLE_SUBJECTS = "Subjects"; //This table should always be created.
    public static final String TABLE_SUBJECTS_KEY_NAME = "Name";

    //Section Table Information
    public static final String TABLE_SECTIONS = "Sections";
    public static final String TABLE_SECTION_KEY_SECTION = "SectionName";
    public static final String TABLE_SECTIONS_KEY_SUBJECT = "Subject";

    //Note Table Information
    public static final String TABLE_NOTES = "Notes";
    public static final String TABLE_NOTES_KEY_FRONT = "Front";
    public static final String TABLE_NOTES_KEY_BACK = "Back";
    public static final String TABLE_NOTES_KEY_SECTION = "Section";
    public static final String TABLE_NOTES_KEY_SUBJECT = "Subject";

    private Context m_context; //Context used to create this database.

    private static ArrayList<Subject> subjects;

    /**
     * Singleton method.
     * @param context - Context to create this object with (if needed)
     * @return - instance of this class.
     */
    public static DatabaseHelper getInstance(Context context) {
        if (m_instance == null) {
            m_instance = new DatabaseHelper(context);
        }
        return m_instance;
    }


    /**
     * Creates a DatabaseHelper object
     * @param context
     */
    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        m_context = context;
        subjects = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
    }

    /**
     * Called when database is first created. Since the only table we know 100% should be around
     * this is where we should create our subjects table.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creation statement. Table with id and name.
        String CreateSubjectTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SUBJECTS + " ("
                + TABLE_SUBJECTS_KEY_NAME + " TEXT"
                + ")";

        String CreateSectionsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SECTIONS + " ("
                + TABLE_SECTION_KEY_SECTION + " TEXT, "
                + TABLE_SECTIONS_KEY_SUBJECT + " TEXT"
                + ")";

        String CreateNotesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + " ("
                + TABLE_NOTES_KEY_FRONT + " TEXT, "
                + TABLE_NOTES_KEY_BACK + " TEXT, "
                + TABLE_NOTES_KEY_SECTION + " TEXT, "
                + TABLE_NOTES_KEY_SUBJECT + " TEXT"
                + ")";


        db.execSQL(CreateSubjectTable);
        db.execSQL(CreateSectionsTable);
        db.execSQL(CreateNotesTable);

        Log.i("Database", "Created all tables!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * Counts the number of subjects already in the table
     * @return - Number of subjects  already created.
     */
    public int getTableCount(String tableName) {
        String countQuery = "SELECT * FROM " + tableName;  //Should return everything in table
        if (m_instance != null) {
            SQLiteDatabase db = m_instance.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            // cursor.close();
            return cursor.getCount(); //Since the query said show me everything, the count should be the "how many are in here"
        }
        //we should throw an exception...
        return -1;
    }


    /**
     * Writes the current cached information into the database.
     */
    public void saveDatabase(ArrayList<Subject> subjects) {
        if (System.currentTimeMillis() - lastUpdateTime < 15000) {
            //If the last save time was less than 60 seconds ago.
            return;
        }
        lastUpdateTime = System.currentTimeMillis();
        SQLiteDatabase db = this.getWritableDatabase(); //Database

        //We want to make sure the database matches perfectly so lets just delete everything in the table and rewrite it.
        db.execSQL("delete from "+ TABLE_SUBJECTS);
        db.execSQL("delete from "+ TABLE_SECTIONS);
        db.execSQL("delete from "+ TABLE_NOTES);
        //Go through every subject
        for (Subject sub : subjects) {

            ContentValues subjectValues = new ContentValues();
            subjectValues.put(TABLE_SUBJECTS_KEY_NAME, sub.getSubjectName());

            db.insert(TABLE_SUBJECTS, null, subjectValues);

            for (Section section : sub.getSections()) {
                ContentValues sectionValues = new ContentValues();
                sectionValues.put(TABLE_SECTION_KEY_SECTION, section.getName());
                sectionValues.put(TABLE_SECTIONS_KEY_SUBJECT, sub.getSubjectName());

                db.insert(TABLE_SECTIONS, null, sectionValues);

                for (Note note : section.getNoteCards()) {
                    ContentValues noteValues = new ContentValues();
                    noteValues.put(TABLE_NOTES_KEY_FRONT, note.getFront());
                    noteValues.put(TABLE_NOTES_KEY_BACK, note.getBack());
                    noteValues.put(TABLE_NOTES_KEY_SECTION, section.getName());
                    noteValues.put(TABLE_NOTES_KEY_SUBJECT, sub.getSubjectName());
                    db.insert(TABLE_NOTES, null, noteValues);
                }

            }

        }
        //Log.i("Database", "Database saved!");
    }

    public ArrayList<Subject> getSubjects() {
        if (!subjects.isEmpty()) {
            //If we have already loaded from the database then just return whats in cache.
            return subjects;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUBJECTS, null, null, null, null, null, null);

        //Nothing in here.
        if (cursor == null) {
            Log.i("Database", "Cursor was null");
            //return null;
        }

        cursor.moveToFirst();
        HashMap<String, Subject> tempMap = new HashMap();


        //Go through and create a map of all the subjects.

        while (!cursor.isAfterLast()) {
            String subName = cursor.getString(0);
            tempMap.put(subName, new Subject(subName));
            cursor.moveToNext();
        }

        //Set cursor to new table.
        cursor = db.query(TABLE_SECTIONS, null, null, null, null, null, null);

        if (cursor == null) {
            Log.i("Database", "Cursor for sections was null!");
            //return null;
        }

        cursor.moveToFirst();

        //Go through and add sections to correct subject.
        while (!cursor.isAfterLast()) {
            String sectionName = cursor.getString(0);
            String subjectName = cursor.getString(1);
            Section sec = new Section(sectionName);
            Subject sub = tempMap.get(subjectName);
            if (sub != null) {
                sub.addSection(sec);
            }
            cursor.moveToNext();
        }

        //Set curosr to new table
        cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);

        if (cursor == null) {
            Log.i("Database", "Cursor for notes was null!");
            return null;
        }

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String sectionName = cursor.getString(2);
            String subjectName = cursor.getString(3);

            String front = cursor.getString(0);
            String back = cursor.getString(1);

            Note note = new Note();
            note.setFront(front);
            note.setBack(back);

            Subject sub = tempMap.get(subjectName);
            if (sub != null) {
                sub.getSection(sectionName).addNote(note);
            }

            cursor.moveToNext();
        }

        cursor.close();

        for (Subject sub : tempMap.values()) {
            subjects.add(sub);
        }

        return subjects;
    }

    public ContentValues saveNotecardValues(String question, String answer, String section, String subject) {
        ContentValues values = new ContentValues();
        values.put(TABLE_NOTES_KEY_FRONT, question);
        values.put(TABLE_NOTES_KEY_BACK, answer);
        values.put(TABLE_NOTES_KEY_SECTION, section);
        values.put(TABLE_NOTES_KEY_SUBJECT, subject);

        return values;
    }

    public ContentValues saveSubjectValues(String subjectName) {
        ContentValues values = new ContentValues();
        values.put(TABLE_SUBJECTS_KEY_NAME, subjectName);

        return values;
    }

    public ContentValues saveSectionValues(String sectionName, String sectionSubject) {
        ContentValues values = new ContentValues();
        values.put(TABLE_SECTION_KEY_SECTION, sectionName);
        values.put(TABLE_SECTIONS_KEY_SUBJECT, sectionSubject);

        return values;
    }



}