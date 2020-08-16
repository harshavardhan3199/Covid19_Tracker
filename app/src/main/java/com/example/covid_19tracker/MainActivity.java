package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView emptyText;
    private static final String COVID_URL="https://corona.lmao.ninja/v2/all";
    private static final String LOG_TAG = MainActivity.class.getName();
    private CovidAdapter covidAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=(ListView)findViewById(R.id.list_view);
        emptyText=(TextView)findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyText);
        covidAdapter=new CovidAdapter(this,new ArrayList<Covid>());
        listView.setAdapter(covidAdapter);
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected())
        {
            CovidAsync covidAsync = new CovidAsync();
            covidAsync.execute(COVID_URL);
        }
        else
        {
            View loadingIndicator=findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);
            emptyText.setText(R.string.data_unavailable);
        }

    }
    private class CovidAsync extends AsyncTask<String,Void, List<Covid>>
    {

        @Override
        protected List<Covid> doInBackground(String... urls)
        {
            if(urls.length<1||urls[0]==null)
            {
                return null;
            }
            List<Covid> result=QueryUtils.fetchCovidData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Covid> covids)
        {
            View loadingIndicator=findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);
            emptyText.setText(R.string.covid_data_unavailable);
            covidAdapter.clear();
            if(covids!=null&&!covids.isEmpty())
            {
                covidAdapter.addAll(covids);
            }
        }
    }


}