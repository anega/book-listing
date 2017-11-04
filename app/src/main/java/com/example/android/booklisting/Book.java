package com.example.android.booklisting;

/**
 * Book class
 */
public class Book {
    private String mTitle;
    private String mAuthor;
    private int mImgUrl;
    private String mBookUrl;

    public Book(String title, String author, int imgUrl, String bookUrl) {
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

    public int getCoverImg() {
        return mImgUrl;
    }

    public String getBookUrl() {
        return mBookUrl;
    }
}
