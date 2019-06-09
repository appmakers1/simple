package com.example.simple;

public class cricMatch {
    private String mteamA;
    private String mteamB;
    private String mdate;
    private String mtype;

    public cricMatch(String teamA,String teamB,String date,String type)
    {
        mteamA=teamA;
        mteamB=teamB;
        mdate=date;
        mtype=type;
    }

    public String getMteamA() {
        return mteamA;
    }

    public String getMteamB() {
        return mteamB;
    }

    public String getMdate() {
        return mdate;
    }

    public String getMtype() {
        return mtype;
    }
}
