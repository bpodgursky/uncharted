package com.bpodgursky.uncharted.datasets;

public class StellarClassification {

  private SpectralClass mainClass;
  private Double range;
  private LuminosityClass luminosityClass;
  private Double whiteDwarfTemp;

  public StellarClassification() {  }

  public StellarClassification setMainClass(SpectralClass mainClass) {
    this.mainClass = mainClass;
    return this;
  }

  public StellarClassification setRange(Double range) {
    this.range = range;
    return this;
  }

  public StellarClassification setLuminosityClass(LuminosityClass luminosityClass) {
    this.luminosityClass = luminosityClass;
    return this;
  }

  public SpectralClass getMainClass() {
    return mainClass;
  }

  public Double getRange() {
    return range;
  }

  public LuminosityClass getLuminosityClass() {
    return luminosityClass;
  }

  public Double getWhiteDwarfTemp() {
    return whiteDwarfTemp;
  }

  public void setWhiteDwarfTemp(Double whiteDwarfTemp) {
    this.whiteDwarfTemp = whiteDwarfTemp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof StellarClassification)) return false;

    StellarClassification that = (StellarClassification) o;

    if (luminosityClass != that.luminosityClass) return false;
    if (mainClass != that.mainClass) return false;
    if (range != null ? !range.equals(that.range) : that.range != null) return false;
    if (whiteDwarfTemp != null ? !whiteDwarfTemp.equals(that.whiteDwarfTemp) : that.whiteDwarfTemp != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = mainClass != null ? mainClass.hashCode() : 0;
    result = 31 * result + (range != null ? range.hashCode() : 0);
    result = 31 * result + (luminosityClass != null ? luminosityClass.hashCode() : 0);
    result = 31 * result + (whiteDwarfTemp != null ? whiteDwarfTemp.hashCode() : 0);
    return result;
  }
}
