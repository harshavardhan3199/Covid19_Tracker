package com.example.covid_19tracker;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

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

public class QueryUtils
{
    private static String LOG_TAG=QueryUtils.class.getSimpleName();
    private QueryUtils(){}
    public static List<Covid> fetchCovidData(String requestURL)
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        URL url=createUrl(requestURL);
        String jsonResponce=null;
        try
        {
            jsonResponce=makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Problem making Http Request "+e);
        }
        List<Covid> covids=extractFeatureFromJson(jsonResponce);
        return covids;
    }
    private static URL createUrl(String requestURL)
    {
        URL url=null;
        try
        {
            if(requestURL!=null)
            {
                url=new URL(requestURL);
            }
        }
        catch(MalformedURLException e)
        {
            Log.e(LOG_TAG,"Problem making URL "+e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url)throws IOException
    {
        String jsonResponce="";
        if(url==null)
        {
            return jsonResponce;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try
        {
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponce=readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG,"Error responce code "+urlConnection.getResponseCode());
            }
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"Problem retrieving covid data "+e);
        }
        finally
        {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponce;
    }
    private static String readFromStream(InputStream inputStream)throws IOException
    {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null)
            {
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }
    public static List<Covid> extractFeatureFromJson(String covidJson)
    {
        if(TextUtils.isEmpty(covidJson))
        {
            return null;
        }
        List<Covid> covidData=new ArrayList<>();
        try
        {
            JSONObject baseJsonResponce=new JSONObject(covidJson);
            long total=baseJsonResponce.getLong("cases");
            long recovered=baseJsonResponce.getLong("recovered");
            long activeCases=baseJsonResponce.getLong("active");
            long totalDeaths=baseJsonResponce.getLong("deaths");
            long todayCases=baseJsonResponce.getLong("todayCases");
            long todayDeaths=baseJsonResponce.getLong("todayDeaths");
            long todayRecovered=baseJsonResponce.getLong("todayRecovered");
            long affectedCountries=baseJsonResponce.getLong("affectedCountries");
            long lastUpdated=baseJsonResponce.getLong("updated");
            Covid covid=new Covid(total,recovered,activeCases,totalDeaths,todayCases,todayDeaths,todayRecovered,affectedCountries,lastUpdated);
            covidData.add(covid);
        }
        catch (JSONException e)
        {
           Log.e(LOG_TAG,"Problem parsing query data "+e);
        }
        return covidData;
    }

}
