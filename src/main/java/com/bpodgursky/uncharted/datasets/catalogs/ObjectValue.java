package com.bpodgursky.uncharted.datasets.catalogs;

public class ObjectValue {

  private final UnitValue value;
  private final ValueSource source;
  private final String measurementQualifier;

  public ObjectValue(double property, ValueSource source, Unit unit) {
    this(new UnitValue(property, unit), source, null);
  }

  public ObjectValue(UnitValue value, ValueSource source, String measurementQualifier) {
    this.value = value;
    this.source = source;
    this.measurementQualifier = measurementQualifier;
  }

  public UnitValue getValue() {
    return value;
  }

  public ValueSource getSource() {
    return source;
  }

  public String getMeasurementQualifier() {
    return measurementQualifier;
  }

  public static ObjectValue value(String raw, Unit rawUnit, Unit targetUnit, UnitValue defaultValue) {
    return value(raw, rawUnit, targetUnit, defaultValue, null);
  }

  public static ObjectValue value(String raw, Unit rawUnit, Unit targetUnit, UnitValue defaultValue, String measurementQualifier) {
    if(!raw.equals("")){
      return new ObjectValue(UnitValue.of(Double.parseDouble(raw), rawUnit).in(targetUnit), ValueSource.SUPPLIED, measurementQualifier);
    }else{
      return new ObjectValue(defaultValue.in(targetUnit), ValueSource.DEFAULT, measurementQualifier);
    }
  }
}
