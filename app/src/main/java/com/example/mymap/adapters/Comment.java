package com.example.mymap.adapters;

import java.io.Serializable;

public class Comment implements Serializable {
    private String userName;
    private String commentText;

    public Comment(String userName, String commentText) {
        this.userName = userName;
        this.commentText = commentText;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}