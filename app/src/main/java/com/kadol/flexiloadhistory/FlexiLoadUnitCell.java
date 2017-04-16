package com.kadol.flexiloadhistory;

/**
 * Created by Nafi on 4/9/2017.
 */

public class FlexiLoadUnitCell {

    private String mDate;
    private String mTime;
    private int mTaka;
    private int mOperator;

    public FlexiLoadUnitCell(String mDate, String mTime, int mTaka, int mOperator){

        this.mDate=mDate;
        this.mTime=mTime;
        this.mTaka=mTaka;
        this.mOperator = mOperator;
    }

    public String getmDate(){
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public int getmTaka() {
        return mTaka;
    }

    public int getmOperator() {
        return mOperator;
    }
}
