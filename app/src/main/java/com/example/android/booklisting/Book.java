package com.example.android.booklisting;

/**
 * Book class
 */
public class Book {
    private String mTitle;
    private String mAuthor;
    private String mImgUrl;
    private String mBookUrl;

    public Book(String title, String author, String imgUrl, String bookUrl) {
        mTitle = title;
        mAuthor = author;
        mImgUrl = imgUrl;
        mBookUrl = bookUrl;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public String getBookUrl() {
        return mBookUrl;
    }
}
