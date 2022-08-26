package com.example.loginactivity;

public class Testing_HelperClass {
    String music;
    String like;
    String url;

    public Testing_HelperClass() {
    }

    public Testing_HelperClass(String music, String like, String url) {
        this.music = music;
        this.like = like;
        this.url = url;

    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return this.music + ", " + like + ", " + url;
    }
}
