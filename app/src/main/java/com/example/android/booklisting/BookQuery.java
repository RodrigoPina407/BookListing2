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


public final class BookQuery {

    private static final String LOG_TAG = BookQuery.class.getName();
    private static List<Book> mBooks;

    private BookQuery() {}

    public static List<Book> getBookData(String requestUrl) {

        URL url = getUrl(requestUrl);

        String jsonResponse = null;

        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e(LOG_TAG, "HTTP request failed!", e);

        }

        List<Book> books = extractFeatureFromJson(jsonResponse);

        mBooks = books;

        return books;

    }


    private static URL getUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // Check if url is null
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* milliseconds */);
            urlConnection.setConnectTimeout(150000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Test if the response code from the request is 200

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem retrieving the Book JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

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


    private static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }


        List<Book> books = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentBook = itemsArray.getJSONObject(i);

                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                JSONArray authorArray;

                String authors;

                if (volumeInfo.has("authors")) {

                    authorArray = volumeInfo.getJSONArray("authors");

                    StringBuilder authorsStringBuild = new StringBuilder();

                    for (int j = 0; j < authorArray.length(); j++) {
                        authorsStringBuild.append(authorArray.getString(j));
                        authorsStringBuild.append(" ");
                    }

                    authors = authorsStringBuild.toString();

                } else
                    authors = "No authors available";


                String title = volumeInfo.getString("title");

                String description;

                if (volumeInfo.has("description"))
                    description = volumeInfo.getString("description");

                else
                    description = "No description available";

                String rating;

                if (volumeInfo.has("averageRating"))
                    rating = volumeInfo.getString("averageRating");

                else
                    rating = "n/a";

                JSONObject saleInfo = currentBook.getJSONObject("saleInfo");

                String buyLink;

                if(saleInfo.has("buyLink"))
                    buyLink = saleInfo.getString("buyLink");
                else
                    buyLink = "";




                Book book = new Book(title, authors, description, rating, buyLink);

                books.add(book);
            }


        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }

        return books;
    }


    public static List<Book> getBooks(){
        return mBooks;
    }

}
