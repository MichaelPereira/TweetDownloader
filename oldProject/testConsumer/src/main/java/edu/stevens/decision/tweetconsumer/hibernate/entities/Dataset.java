package edu.stevens.decision.tweetconsumer.hibernate.entities;
// Generated Apr 7, 2011 2:05:00 PM by Hibernate Tools 3.2.1.GA



/**
 * Dataset generated by hbm2java
 */
public class Dataset  implements java.io.Serializable {


     private Integer datasetId;
     private String datasetName;

    public Dataset() {
    }

    public Dataset(String datasetName) {
       this.datasetName = datasetName;
    }
   
    public Integer getDatasetId() {
        return this.datasetId;
    }
    
    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }
    public String getDatasetName() {
        return this.datasetName;
    }
    
    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }




}

