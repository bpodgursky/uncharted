package com.bpodgursky.uncharted.datasets;

public class ParsedClassification {

  private SpectralClass mainClass;
  private Double whiteDwarfTempIndex;

  public ParsedClassification() {  }

  public ParsedClassification setMainClass(SpectralClass mainClass) {
    this.mainClass = mainClass;
    return this;
  }

  public SpectralClass getMainClass() {
    return mainClass;
  }

  public ParsedClassification setWhiteDwarfTempIndex(Double whiteDwarfTempIndex) {
    this.whiteDwarfTempIndex = whiteDwarfTempIndex;
    return this;
  }

  public Double getWhiteDwarfTempIndex() {
    return whiteDwarfTempIndex;
  }

  @Override
  public String toString() {
    return "ParsedClassification{" +
        "mainClass=" + mainClass +
        ", whiteDwarfTempIndex=" + whiteDwarfTempIndex +
        '}';
  }
}
