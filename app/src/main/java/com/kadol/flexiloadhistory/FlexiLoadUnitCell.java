package com.kadol.flexiloadhistory;

/**
 * Created by Nafi on 4/9/2017.
 */

public class FlexiLoadUnitCell {

    private String mDate;
    private String mTime;
    private String mTaka;

    public FlexiLoadUnitCell(String mDate, String mTime, String mTaka){

        this.mDate=mDate;
        this.mTime=mTime;
        this.mTaka=mTaka;
    }

    public String getmDate(){
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmTaka() {
        return mTaka;
    }
}
