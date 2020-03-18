package com.example.android.architectureexample.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.architectureexample.entity.NoteWithCategory;
import com.example.android.architectureexample.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT n.*, c.id AS c_id, c.name AS c_name FROM note_table AS n LEFT JOIN category_table AS c ON n.categoryId = c.id")
    List<NoteWithCategory> getAllNotesWithCategory();
}
