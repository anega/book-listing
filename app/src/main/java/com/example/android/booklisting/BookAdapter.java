package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book book = getItem(position);

        ImageView bookCover = listItemView.findViewById(R.id.iv_listitem_bookcover);
        bookCover.setImageBitmap((book.getCoverImg()));

        TextView bookTitle = listItemView.findViewById(R.id.tv_listitem_booktitle);
        bookTitle.setText(book.getTitle());

        TextView bookAuthor = listItemView.findViewById(R.id.tv_listitem_bookauthor);
        bookAuthor.setText(book.getAuthor());

        return listItemView;
    }
}
