package com.bpodgursky.uncharted.datasets;

import java.util.concurrent.atomic.AtomicInteger;

abstract class ObjectRecord {

  private static final AtomicInteger COUNT = new AtomicInteger();

  private final String properName;
  private final String type;
  private final int primaryId;

  private ExternalLinks links;
  private Double radiusInLys;

  public ObjectRecord(String properName,
                      String type){
    this.properName = properName;
    this.type = type;
    this.primaryId = COUNT.incrementAndGet();
  }

  protected void setLinks(ExternalLinks links) {
    this.links = links;
  }

  protected void setRadiusInLys(Double radiusInLys) {
    this.radiusInLys = radiusInLys;
  }

  public int getPrimaryId() {
    return primaryId;
  }
}
