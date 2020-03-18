package com.example.android.architectureexample.entity;

import androidx.room.Embedded;

public class NoteWithCategory {

    @Embedded
    public Note note;

    @Embedded(prefix = "c_")
    public Category category;

}
