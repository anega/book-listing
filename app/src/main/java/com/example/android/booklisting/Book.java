package com.example.android.booklisting;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Book class
 */
public class Book {
    private String mTitle;
    private String mAuthor;
    private Bitmap mImgUrl;
    private String mBookUrl;

    public Book(String title, String author, Bitmap imgUrl, String bookUrl) {
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

    public Bitmap getCoverImg() {
        return mImgUrl;
    }

    public String getBookUrl() {
        return mBookUrl;
    }
}
