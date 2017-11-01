package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving data from API.
 */
public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {}

    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Book> bookList = extractFeatureFromJson(jsonResponse);

        return bookList;
    }

    /**
     * Returns new URL object from the given string URL.
     *
     * @param stringUrl String url where the request will be send
     * @return URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with url creation", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the url is null, then return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with retrieving data.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Book> bookList = new ArrayList<>();

        try {
            // Extract the JSONArray associated with the key called "items",
            // which represents a list of items (or books).
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray bookItemsList = jsonObject.getJSONArray("items");
            for (int i = 0; i < bookItemsList.length(); i++) {
                // Get book item from array
                JSONObject bookItem = bookItemsList.getJSONObject(i);
                JSONObject volInfo = bookItem.getJSONObject("volumeInfo");
                String bookTitle = volInfo.getString("title");
                JSONArray bookAuthors = volInfo.getJSONArray("authors");
                // Get book authors
                StringBuilder authorStringBuilder = new StringBuilder();
                for (int j = 0; j < bookAuthors.length(); j++) {
                    JSONObject row = bookAuthors.getJSONObject(j);
                    authorStringBuilder.append(row).append(", ");
                }
                String bookAuthorsString = authorStringBuilder.toString();
                // Get book cover image url
                JSONObject bookCoverUrlList = bookItem.getJSONObject("imageLinks");
                String bookCoverUrlThumb = bookCoverUrlList.getString("thumbnail");
                // Get url to a book page
                String bookPageUrl = bookItem.getString("selfLink");

                // Create a new {@link Book} object
                bookList.add(new Book(bookTitle, bookAuthorsString, bookCoverUrlThumb, bookPageUrl));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results.", e);
        }

        return bookList;
    }
}
