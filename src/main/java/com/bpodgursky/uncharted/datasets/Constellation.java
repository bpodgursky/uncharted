package com.bpodgursky.uncharted.datasets;

public class Constellation {
  private final String constellation;
  private final String genitive;

  public Constellation(String constellation, String genitive) {
    this.constellation = constellation;
    this.genitive = genitive;
  }

  public String getConstellation() {
    return constellation;
  }

  public String getGenitive() {
    return genitive;
  }

  @Override
  public String toString() {
    return "Constellation{" +
        "constellation='" + constellation + '\'' +
        ", genitive='" + genitive + '\'' +
        '}';
  }
}
