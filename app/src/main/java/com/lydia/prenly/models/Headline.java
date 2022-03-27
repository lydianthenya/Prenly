package com.lydia.prenly.models;

public class Headline {
    private String name;
    private String dateOfPosting;
    private String title;
    private String thumbnail;
    private String url;
    private String content;
    private String author;

    public Headline(String name, String dateOfPosting, String title, String thumbnail, String url, String content, String author) {
        this.name = name;
        this.dateOfPosting = dateOfPosting;
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
        this.content = content;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(String dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}