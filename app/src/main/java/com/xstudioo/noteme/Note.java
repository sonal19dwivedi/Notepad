package com.xstudioo.noteme;

public class Note {
    private long id;
    private String title;
    private String content;
    private String date;
    private String time;
    private String color;

    Note(){

    }

    Note(String title,String content,String date, String time, String color){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    Note(long id,String title,String content,String date, String time, String color){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.color = color;
    }
    Note(long l, String string, String cursorString, String time, String color){
       // empty constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
