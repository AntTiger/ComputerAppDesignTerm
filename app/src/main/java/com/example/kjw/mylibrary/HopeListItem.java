package com.example.kjw.mylibrary;

/**
 * Created by User on 2017-06-25.
 */

public class HopeListItem {
    private static int num  = 1;
    private String date;
    private String name;
    private String status;

    public HopeListItem( String date, String name, String status) {
        this.date = date;
        this.name = name;
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void increaseNum(){
        num++;
    }
}
