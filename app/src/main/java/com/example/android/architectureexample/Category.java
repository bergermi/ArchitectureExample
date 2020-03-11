package com.example.android.architectureexample;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "category_table")
@Getter
public class Category {

    @PrimaryKey(autoGenerate = true)
    @Setter
    private int id;

    private String name;

    public Category(String name) {
        this.name = name;
    }

}
