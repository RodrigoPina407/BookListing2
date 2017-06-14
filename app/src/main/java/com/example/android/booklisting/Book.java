package com.example.android.booklisting;

import android.graphics.drawable.Drawable;

/**
 * Created by Rodrigo on 12/06/2017.
 */

public class Book {

    private String mBookTitle;
    private String mBookDescription;
    private String mBookAuthor;
    private String mRating;
    private String mBuyLink;

    public Book(String title, String author, String description, String rating, String buyLink) {

        mBookTitle = title;
        mBookAuthor = author;
        mBookDescription = description;
        mRating = rating;
        mBuyLink = buyLink;

    }


    public String getTitle() {
        return mBookTitle;
    }

    public String getBookDescription() {
        return mBookDescription;
    }

    public String getAuthor() {
        return mBookAuthor;
    }

    public String getRating() {
        return mRating;
    }

    public String getBuyLink(){return mBuyLink;}



}
