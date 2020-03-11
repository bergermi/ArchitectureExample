package com.example.android.architectureexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class, Category.class}, version = 2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public abstract CategoryDao getCategoryDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
            categoryDao = db.getCategoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert((new Note("Title 1", "Description 1", 1)));
            noteDao.insert((new Note("Title 2", "Description 2", 2)));
            noteDao.insert((new Note("Title 3", "Description 3", 3)));

            categoryDao.insert(new Category("Category A"));
            categoryDao.insert(new Category("Category B"));
            categoryDao.insert(new Category("Category C"));
            categoryDao.insert(new Category("Category D"));

            return null;
        }
    }
}
