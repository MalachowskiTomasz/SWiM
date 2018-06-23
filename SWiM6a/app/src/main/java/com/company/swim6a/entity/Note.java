package com.company.swim6a.entity;

import java.io.File;
import java.util.Date;

public class Note {
    private String title;
    private String author;
    private Date date;
    private String description;
    private File file;

    public Note(String title, String author, Date date, String description, File file) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
