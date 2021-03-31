package com.example.android.miwok;

public class Word {
    private String mMiwokTranslation;
    private String mDefaultTranslation;

    private int nImageResourseId= NO_IMAGE_RESOURCE;
    private static final int NO_IMAGE_RESOURCE=-1;

    @Override
    public String toString() {
        return "Word{" +
                "mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", nImageResourseId=" + nImageResourseId +
                ", mAudioResourseId=" + mAudioResourseId +
                '}';
    }

    private  int mAudioResourseId;

    public Word(String DefaultTranslation, String MiwokTranslation ,int audioResourseId){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation= MiwokTranslation;
        mAudioResourseId = audioResourseId;
    }
    public Word(String DefaultTranslation, String MiwokTranslation ,int imageResourseId,int audioResourseId){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation= MiwokTranslation;
        nImageResourseId= imageResourseId;
        mAudioResourseId = audioResourseId;
    }

    public String getmMiwokTranslation(){
        return mMiwokTranslation;
    }
    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }
    public int getnImageResourseId(){
        return nImageResourseId;
    }
    public int getmAudioResourseId(){return mAudioResourseId;}
    public boolean hasImage(){
        if(nImageResourseId==-1) return false;
        else return true;
    }
}
