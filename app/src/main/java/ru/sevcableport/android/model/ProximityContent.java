package ru.sevcableport.android.model;

public class ProximityContent {

    public ProximityContent(String title, String subtitle, String text, int photoResourceId, int notificationSmallIconResourceId) {
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.photoResourceId = photoResourceId;
        this.notificationSmallIconResourceId = notificationSmallIconResourceId;
    }

    private String title;

    private String subtitle;

    private String text;

    private int photoResourceId;

    private int notificationSmallIconResourceId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPhotoResourceId() {
        return photoResourceId;
    }

    public void setPhotoResourceId(int photoResourceId) {
        this.photoResourceId = photoResourceId;
    }

    public int getNotificationSmallIconResourceId() {
        return notificationSmallIconResourceId;
    }

    public void setNotificationSmallIconResourceId(int notificationSmallIconResourceId) {
        this.notificationSmallIconResourceId = notificationSmallIconResourceId;
    }
}
