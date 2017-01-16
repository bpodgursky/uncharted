package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.datasets.identifiers.BayerFlamsteed;

public class StarIdentifiers {

  private String hipparcosId;
  private String henryDraperId;
  private String harvardRevisedId;
  private String glieseId;
  private BayerFlamsteed bayerFlamsteed;
  private String properName;

  public StarIdentifiers() {}

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

  public String getPrimaryName() {

    if (properName != null) {
      return properName;
    }

    if (bayerFlamsteed != null) {
      return bayerFlamsteed.getPrettyName();
    }

    if (glieseId != null) {
      return glieseId;
    }

    if (harvardRevisedId != null) {
      return "HR "+harvardRevisedId;
    }

    if (henryDraperId != null) {
      return "HD "+henryDraperId;
    }

    return "HIP "+hipparcosId;
  }

}
