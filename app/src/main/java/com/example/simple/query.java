package com.example.simple;

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

public class query {

    private static final String TAG="rohith";
    private query() {
    }

    // json parsing and url connection
    public static List<cricMatch> fetchEarthquakeData(String requestUrl)
    {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<cricMatch> cricMatches= extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return cricMatches;
    }
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //make HTTP request to the given url and get reponse
    public static String makeHttpRequest(URL url) throws  IOException
    {
        String jsonResponse=" ";

        if(url==null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        }
        catch(IOException e)
        {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        finally {
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
    public static  String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return  output.toString();
    }
    public static  List<cricMatch> extractFeatureFromJson(String cricMatchJson)
    {
        if(TextUtils.isEmpty(cricMatchJson))
        {
            return null;
        }
        List<cricMatch> cricMatches=new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {

            JSONObject baseJsonResponse=new JSONObject(cricMatchJson);
            JSONArray cricMatchArray=baseJsonResponse.getJSONArray("matches");
            for(int i=0;i<cricMatchArray.length();i++)
            {
                JSONObject currentMatch=cricMatchArray.getJSONObject(i);
                String team1=currentMatch.getString("team-1");
                String team2=currentMatch.getString("team-2");
                String type=currentMatch.getString("type");
                String date=currentMatch.getString("date");

                cricMatch cricmatch=new cricMatch(team1,team2,date,type);
                cricMatches.add(cricmatch);

            }

        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return cricMatches;

    }


}
