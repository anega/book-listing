package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    // Adapter for the list of books
    private BookAdapter mAdapter;
    // Set the class log tag
    private static final String LOG_TAG = MainActivity.class.getName();
    // URL for book data from google
    private static String BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=bonobo";
    // Constant value for the book loader ID
    private static final int BOOK_LOADER_ID = 0;
    // TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;
    // Loading data ProgressBar
    private ProgressBar mIndeterminateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById(R.id.lv_main_bookslist);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        // Find a reference to the empty earthquakes view in the layout
        mEmptyStateTextView = findViewById(R.id.tv_empty_books);
        mIndeterminateBar = findViewById(R.id.indeterminateBar);
        final EditText queryWord = findViewById(R.id.et_main_queryword);

        // Set the adapter on the {@link} ListView, so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book currentBook = mAdapter.getItem(i);
                Uri bookUri = Uri.parse(currentBook.getBookUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise display error
            // First hide loading indicator so error message will be wisible
            mIndeterminateBar.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_intenet);
        }
        // Set empty view for no books response
        bookListView.setEmptyView(mEmptyStateTextView);

        queryWord.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null || i == EditorInfo.IME_ACTION_SEARCH) {
                    BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=" + queryWord.getText().toString();
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }

                return true;
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, BOOK_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide progress bar when loading is finished
        mIndeterminateBar.setVisibility(View.GONE);
        // Set empty state text to display "No data available."
        mEmptyStateTextView.setText(R.string.no_data_available);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
