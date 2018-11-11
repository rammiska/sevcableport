package ru.sevcableport.android.model;


public class CardItem {

    private int mTextResource;
    private int mTitleResource;
    private int mPhotoResource;

    public CardItem(int title, int text, int photo) {
        mTitleResource = title;
        mTextResource = text;
        mPhotoResource = photo;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getPhoto() {
        return mPhotoResource;
    }
}
