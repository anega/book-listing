package com.example.android.booklisting;

import android.graphics.Bitmap;

/**
 * Book item class
 */
public class Book {
    private final String mTitle;
    private final String mAuthor;
    private final Bitmap mCoverImg;
    private final String mBookUrl;

    /**
     * Book item constructor
     *
     * @param title    String book item title
     * @param author   String book item author
     * @param coverImg Bitmap book item cover image
     * @param bookUrl  String book item details url
     */
    public Book(String title, String author, Bitmap coverImg, String bookUrl) {
        mTitle = title;
        mAuthor = author;
        mCoverImg = coverImg;
        mBookUrl = bookUrl;
    }

    /**
     * Book title getter
     *
     * @return String title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Book author getter
     *
     * @return String author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Book image getter
     *
     * @return Bitmap image
     */
    public Bitmap getCoverImg() {
        return mCoverImg;
    }

    /**
     * Book details url getter
     *
     * @return String url
     */
    public String getBookUrl() {
        return mBookUrl;
    }
}
