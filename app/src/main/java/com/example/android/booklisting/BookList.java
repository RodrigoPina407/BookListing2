package com.example.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.LoaderManager;


import java.util.ArrayList;
import java.util.List;

public class BookList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String GENERIC_BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookListAdapter mAdapter;
    private TextView mEmptyTextView;
    private String mSearchTerms;
    private String mQuery;
    private final static int BOOK_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Intent intent = getIntent();

        mSearchTerms = intent.getStringExtra("search");

        StringBuilder query = new StringBuilder();

        mSearchTerms = mSearchTerms.replaceAll(" ","+");

        query.append(GENERIC_BOOK_REQUEST_URL).append(mSearchTerms).append("&maxResults=40");

        mQuery = query.toString();

        ListView bookList = (ListView) findViewById(R.id.book_list);
        mEmptyTextView = (TextView)findViewById(R.id.empty_view);

        bookList.setEmptyView(mEmptyTextView);

        mAdapter = new BookListAdapter(this, new ArrayList<Book>());

        bookList.setAdapter(mAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        } else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    @Override
    public android.content.Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, mQuery);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyTextView.setText(R.string.no_books_found);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }


    }


}
