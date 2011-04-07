package edu.stevens.decision.tweetconsumer.hibernate.entities;
// Generated Apr 7, 2011 2:05:00 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Tweet generated by hbm2java
 */
public class Tweet  implements java.io.Serializable {


     private long tweetId;
     private Long tweetUser;
     private String tweetUserLogin;
     private Date tweetDate;
     private String tweetText;
     private Long tweetRetweet;
     private Float tweetLatitude;
     private Float tweetLongitude;
     private String tweetPlace;
     private String tweetLanguage;

    public Tweet() {
    }

	
    public Tweet(long tweetId) {
        this.tweetId = tweetId;
    }
    public Tweet(long tweetId, Long tweetUser, String tweetUserLogin, Date tweetDate, String tweetText, Long tweetRetweet, Float tweetLatitude, Float tweetLongitude, String tweetPlace, String tweetLanguage) {
       this.tweetId = tweetId;
       this.tweetUser = tweetUser;
       this.tweetUserLogin = tweetUserLogin;
       this.tweetDate = tweetDate;
       this.tweetText = tweetText;
       this.tweetRetweet = tweetRetweet;
       this.tweetLatitude = tweetLatitude;
       this.tweetLongitude = tweetLongitude;
       this.tweetPlace = tweetPlace;
       this.tweetLanguage = tweetLanguage;
    }
   
    public long getTweetId() {
        return this.tweetId;
    }
    
    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
    public Long getTweetUser() {
        return this.tweetUser;
    }
    
    public void setTweetUser(Long tweetUser) {
        this.tweetUser = tweetUser;
    }
    public String getTweetUserLogin() {
        return this.tweetUserLogin;
    }
    
    public void setTweetUserLogin(String tweetUserLogin) {
        this.tweetUserLogin = tweetUserLogin;
    }
    public Date getTweetDate() {
        return this.tweetDate;
    }
    
    public void setTweetDate(Date tweetDate) {
        this.tweetDate = tweetDate;
    }
    public String getTweetText() {
        return this.tweetText;
    }
    
    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
    public Long getTweetRetweet() {
        return this.tweetRetweet;
    }
    
    public void setTweetRetweet(Long tweetRetweet) {
        this.tweetRetweet = tweetRetweet;
    }
    public Float getTweetLatitude() {
        return this.tweetLatitude;
    }
    
    public void setTweetLatitude(Float tweetLatitude) {
        this.tweetLatitude = tweetLatitude;
    }
    public Float getTweetLongitude() {
        return this.tweetLongitude;
    }
    
    public void setTweetLongitude(Float tweetLongitude) {
        this.tweetLongitude = tweetLongitude;
    }
    public String getTweetPlace() {
        return this.tweetPlace;
    }
    
    public void setTweetPlace(String tweetPlace) {
        this.tweetPlace = tweetPlace;
    }
    public String getTweetLanguage() {
        return this.tweetLanguage;
    }
    
    public void setTweetLanguage(String tweetLanguage) {
        this.tweetLanguage = tweetLanguage;
    }




}


