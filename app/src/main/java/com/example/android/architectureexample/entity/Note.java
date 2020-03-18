package com.example.android.architectureexample.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName = "note_table")
@Getter
public class Note {

    @PrimaryKey(autoGenerate = true)
    @Setter
    private int id;

    private String title;

    private String description;

    private int priority;

    @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = SET_NULL)
    @ColumnInfo(name = "categoryId")
    private final Integer categoryId;

    public Note(String title, String description, int priority, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.categoryId = categoryId;
    }

}
