package com.example.covid_19tracker;

public class Covid
{
    private long mTotal,mRecovered,mActive,mDeaths,mTodayCases,mTodayDeaths,mTodayRecovered,mAffectedCountries,mLastUpdated;
    public Covid(long total,long recovered,long active,long deaths,long todayCases,long todayDeaths,long todayRecovered,long affectedCountries,long lastUpdated )
    {
        mTotal=total;
        mRecovered=recovered;
        mActive=active;
        mDeaths=deaths;
        mTodayCases=todayCases;
        mTodayDeaths=todayDeaths;
        mTodayRecovered=todayRecovered;
        mAffectedCountries=affectedCountries;
        mLastUpdated=lastUpdated;
    }
    public long getTotal()
    {
        return mTotal;
    }
    public long getRecovered()
    {
        return mRecovered;
    }
    public long getActive()
    {
        return mActive;
    }
    public long getDeaths()
    {
        return mDeaths;
    }
    public long getTodayCases()
    {
        return mTodayCases;
    }
    public long getTodayDeaths()
    {
        return mTodayDeaths;
    }
    public long getTodayRecovered()
    {
        return mTodayRecovered;
    }
    public long getAffectedCountries()
    {
        return mAffectedCountries;
    }
    public long getLastUpdated()
    {
        return mLastUpdated;
    }


}
