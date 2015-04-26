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

    //Database infromation.
    private static final String DB_NAME = "StudyManager"; // Database name
    private static final int VERSION = 0; // Used to rebuild database if we make changes to structure.

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

    private Context m_context; //Context used to create this database.

    private static ArrayList<Subject> subjects;

    /**
     * Singleton method.
     * @param context - Context to create this object with (if needed)
     * @return - instance of this class.
     */
    public static DatabaseHelper getInstance(Context context) {
        if (m_instance == null)
            m_instance = new DatabaseHelper(context);

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
    }

    /**
     * Called when database is first created. Since the only table we know 100% should be around
     * this is where we should create our subjects table.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creation statement. Table with id and name.
        String CreateSubjectTable = "CREATE TABLE " + TABLE_SUBJECTS + " ("
                + TABLE_SUBJECTS_KEY_NAME + " TEXT"
                + ")";

        String CreateSectionsTable = "CREATE TABLE " + TABLE_SECTIONS + " ("
                + TABLE_SECTION_KEY_SECTION + " TEXT, "
                + TABLE_SECTIONS_KEY_SUBJECT + " TEXT"
                + ")";

        String CreateNotesTable = "CREATE TABLE " + TABLE_NOTES + " ("
                + TABLE_NOTES_KEY_FRONT + " TEXT, "
                + TABLE_NOTES_KEY_BACK + " TEXT, "
                + TABLE_NOTES_KEY_SECTION + " TEXT"
                + ")";


        db.execSQL(CreateSubjectTable);
        db.execSQL(CreateSectionsTable);
        db.execSQL(CreateNotesTable);

        Log.i("Database", "Created all tables!");
    }

    /**
     * Checks if there is an upgrade needed if so it deletes all old stuff and recreates new.
     * Currently I don't know how I want to do this so lets leave it blank.
     * @param db - Database instance
     * @param oldVersion - Version this device is on.
     * @param newVersion - Version our code is on
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement schema changes and data massage here when upgrading

        // While we are creating our app we can use the drop if exists method.

        // Delete old tables if they exist

        //Now recreate
        //onCreate(db);
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
    public void saveDatabase() {
        SQLiteDatabase db = this.getWritableDatabase(); //Database

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
                }

            }

        }
    }

    public ArrayList<Subject> getSubjects() {
        if (!subjects.isEmpty()) {
            //If we have already loaded from the database then just return whats in cache.
            return subjects;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        HashMap<String, Subject> subjectMap = new HashMap();

        Cursor cursor = db.query(TABLE_SUBJECTS, null, null, null, null, null, null);

        //Nothing in here.
        if (cursor == null) {
            return null;
        }


        for (cursor.moveToFirst(); cursor.isAfterLast(); cursor.moveToNext()) {

        }



        return subjects;
    }


}