package com.example.kjw.mylibrary;

/**
 * Created by User on 2017-06-25.
 */

public class HopeListSearchItem {
    String name;
    String author;
    String imgURL;
    String publisher;
    String publishdate;
    String isbn;
    String price;

    public HopeListSearchItem(String name, String author, String imgURL, String publisher, String publishdate, String isbn, String price) {
        this.name = name;
        this.author = author;
        this.imgURL = imgURL;
        this.publisher = publisher;
        this.publishdate = publishdate;
        this.isbn = isbn;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPrice() {
        return price;
    }
}
