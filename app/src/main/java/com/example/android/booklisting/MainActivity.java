package com.example.android.booklisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById(R.id.lv_main_bookslist);

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book("title 1", "author 1", R.drawable.book_cover, "title 1"));
        bookList.add(new Book("title 2", "author 2", R.drawable.book_cover, "title 2"));
        bookList.add(new Book("title 3", "author 3", R.drawable.book_cover, "title 3"));
        bookList.add(new Book("title 4", "author 4", R.drawable.book_cover, "title 4"));
        bookList.add(new Book("title 4", "author 4", R.drawable.book_cover, "title 4"));
        bookList.add(new Book("title 4", "author 4", R.drawable.book_cover, "title 4"));
        bookList.add(new Book("title 1", "author 1", R.drawable.book_cover, "title 1"));
        bookList.add(new Book("title 2", "author 2", R.drawable.book_cover, "title 2"));
        bookList.add(new Book("title 3", "author 3", R.drawable.book_cover, "title 3"));
        bookList.add(new Book("title 4", "author 4", R.drawable.book_cover, "title 4"));
        bookList.add(new Book("title 4", "author 4", R.drawable.book_cover, "title 4"));
        bookList.add(new Book("title 4", "author 4", R.drawable.book_cover, "title 4"));

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookAdapter(this, bookList);

        bookListView.setAdapter(mAdapter);
    }
}
