package com.example.android.architectureexample.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.architectureexample.dao.CategoryDao;
import com.example.android.architectureexample.dao.NoteDao;
import com.example.android.architectureexample.entity.Category;
import com.example.android.architectureexample.entity.Note;

@Database(entities = {Note.class, Category.class}, version = 3, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao getNoteDao();

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
            noteDao = db.getNoteDao();
            categoryDao = db.getCategoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.insert(new Category("Category A"));
            categoryDao.insert(new Category("Category B"));
            categoryDao.insert(new Category("Category C"));
            categoryDao.insert(new Category("Category D"));

            noteDao.insert((new Note("Title 1", "Description 1", 1, 1)));
            noteDao.insert((new Note("Title 2", "Description 2", 2, 2)));
            noteDao.insert((new Note("Title 3", "Description 3", 3, 3)));

            Log.e("JOIN", noteDao.getAllNotesWithCategory().toString());

            return null;
        }
    }
}
