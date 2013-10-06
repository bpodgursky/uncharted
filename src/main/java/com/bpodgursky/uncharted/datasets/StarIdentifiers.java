package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.datasets.identifiers.BayerFlamsteed;

public class StarIdentifiers {

  //  anything unique
  private final String primaryId;

  private String hipparcosId;
  private String henryDraperId;
  private String harvardRevisedId;
  private String glieseId;
  private BayerFlamsteed bayerFlamsteed;
  private String properName;


  public StarIdentifiers(String primaryId) {
    this.primaryId = primaryId;
  }

  public void setHipparcosId(String hipparcosId) {
    this.hipparcosId = hipparcosId;
  }

  public void setHenryDraperId(String henryDraperId) {
    this.henryDraperId = henryDraperId;
  }

  public void setHarvardRevisedId(String harvardRevisedId) {
    this.harvardRevisedId = harvardRevisedId;
  }

  public void setGlieseId(String glieseId) {
    this.glieseId = glieseId;
  }

  public void setBayerFlamsteed(BayerFlamsteed bayerFlamsteed) {
    this.bayerFlamsteed = bayerFlamsteed;
  }

  public void setProperName(String properName) {
    this.properName = properName;
  }

  public String getPrimaryId() {
    return primaryId;
  }

  public String getHipparcosId() {
    return hipparcosId;
  }

  public String getHenryDraperId() {
    return henryDraperId;
  }

  public String getHarvardRevisedId() {
    return harvardRevisedId;
  }

  public String getGlieseId() {
    return glieseId;
  }

  public BayerFlamsteed getBayerFlamsteed() {
    return bayerFlamsteed;
  }

  public String getProperName() {
    return properName;
  }

  @Override
  public String toString() {
    return "StarIdentifiers{" +
        "primaryId='" + primaryId + '\'' +
        ", hipparcosId='" + hipparcosId + '\'' +
        ", henryDraperId='" + henryDraperId + '\'' +
        ", harvardRevisedId='" + harvardRevisedId + '\'' +
        ", glieseId='" + glieseId + '\'' +
        ", bayerFlamsteed='" + bayerFlamsteed + '\'' +
        ", properName='" + properName + '\'' +
        '}';
  }
}
