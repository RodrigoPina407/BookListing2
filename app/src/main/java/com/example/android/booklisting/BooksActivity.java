package com.example.android.booklisting;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.android.booklisting.R.id.fab;

public class BooksActivity extends AppCompatActivity {

    private List<Book> mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        mBooks = BookQuery.getBooks();

        Intent intent = getIntent();

        int position = intent.getIntExtra("position", 0);

        final Book currentBook = mBooks.get(position);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri bookUri = Uri.parse(currentBook.getBuyLink());

                if (currentBook.getBuyLink().equals(""))
                    Toast.makeText(getApplicationContext(), R.string.not_available, Toast.LENGTH_LONG).show();

                else{
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }


            }
        });


        TextView title = (TextView) findViewById(R.id.book_title);
        TextView author = (TextView) findViewById(R.id.book_author);
        TextView rating = (TextView) findViewById(R.id.book_rating);
        TextView description = (TextView) findViewById(R.id.description);

        title.setText(currentBook.getTitle());
        author.setText(currentBook.getAuthor());
        rating.setText(currentBook.getRating());
        description.setText(currentBook.getBookDescription());


    }

}
