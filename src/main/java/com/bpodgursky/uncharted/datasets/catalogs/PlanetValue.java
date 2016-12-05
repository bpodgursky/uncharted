package com.bpodgursky.uncharted.datasets.catalogs;

public class PlanetValue {
  private final double value;
  private final ValueSource source;

  PlanetValue(double property, ValueSource source) {
    this.value = property;
    this.source = source;
  }

  public static PlanetValue of(double property, ValueSource source){
    return new PlanetValue(property, source);
  }
}
