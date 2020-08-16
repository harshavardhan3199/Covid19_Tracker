package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CovidAdapter extends ArrayAdapter<Covid>
{
    public CovidAdapter(Context context, List<Covid> covids)
    {
        super(context,0,covids);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.covid_list,parent,false);
        }
        Covid currentCovid=getItem(position);
        long totalCases=currentCovid.getTotal();
        TextView totalCasesText=(TextView)listItemView.findViewById(R.id.total_cases_text);
        totalCasesText.setText(String.valueOf(totalCases));
        long recoveredCases=currentCovid.getRecovered();
        TextView recoveredCasesText=(TextView)listItemView.findViewById(R.id.recovered_text);
        recoveredCasesText.setText(String.valueOf(recoveredCases));
        long activeCases=currentCovid.getActive();
        TextView activeCasesText=(TextView)listItemView.findViewById(R.id.active_text);
        activeCasesText.setText(String.valueOf(activeCases));
        long totalDeaths=currentCovid.getDeaths();
        TextView totalDeathText=(TextView)listItemView.findViewById(R.id.death_text);
        totalDeathText.setText(String.valueOf(totalDeaths));
        long todayCases=currentCovid.getTodayCases();
        TextView todayCasesText=(TextView)listItemView.findViewById(R.id.today_cases_text);
        todayCasesText.setText(String.valueOf(todayCases));
        long todayDeaths=currentCovid.getTodayDeaths();
        TextView todayDeathText=(TextView)listItemView.findViewById(R.id.today_death_text);
        todayDeathText.setText(String.valueOf(todayDeaths));
        long todayRecovered=currentCovid.getTodayRecovered();
        TextView todayRecoveredText=(TextView)listItemView.findViewById(R.id.today_recovered_text);
        todayRecoveredText.setText(String.valueOf(todayRecovered));
        long affectedCountries=currentCovid.getAffectedCountries();
        TextView affectedCountriesText=(TextView)listItemView.findViewById(R.id.affected_countries_text);
        affectedCountriesText.setText(String.valueOf(affectedCountries));
        Date dateObject=new Date(currentCovid.getLastUpdated());
        TextView lastUpdatedDateText=(TextView)listItemView.findViewById(R.id.last_updated_date_text);
        String formattedDate=formatDate(dateObject);
        lastUpdatedDateText.setText(formattedDate);
        TextView lastUpdatedTimeText=(TextView)listItemView.findViewById(R.id.last_updated_time_text);
        String formattedTime=formatTime(dateObject);
        lastUpdatedTimeText.setText(formattedTime);
        return listItemView;
    }
    public String formatDate(Date dateObject)
    {
        SimpleDateFormat dateFormatter=new SimpleDateFormat("LLL dd,yyyy");
        return dateFormatter.format(dateObject);
    }

    public String formatTime(Date dateObject)
    {
        SimpleDateFormat timeFormatter=new SimpleDateFormat("h:mm a");
        return timeFormatter.format(dateObject);
    }
}
