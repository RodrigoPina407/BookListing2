package com.example.android.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rodrigo on 12/06/2017.
 */

public class BookListAdapter extends ArrayAdapter<Book> {


    public BookListAdapter(Context context, List<Book> book){
        super(context, 0, book);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView ratingView = (TextView) listItemView.findViewById(R.id.rating);
        TextView titleView = (TextView) listItemView.findViewById(R.id.book_title);
        TextView authorView = (TextView) listItemView.findViewById(R.id.book_author);

        ratingView.setText(currentBook.getRating());
        titleView.setText(currentBook.getTitle());
        authorView.setText(currentBook.getAuthor());


        return listItemView;
    }
}
