package com.bpodgursky.uncharted.datasets.catalogs;

public class ObjectValue {

  private final UnitValue value;

  private final ValueSource source;

  public ObjectValue(double property, ValueSource source, Unit unit) {
    this(new UnitValue(property, unit), source);
  }

  public ObjectValue(UnitValue value, ValueSource source) {
    this.value = value;
    this.source = source;
  }

  public UnitValue getValue() {
    return value;
  }

  public ValueSource getSource() {
    return source;
  }

  public static ObjectValue value(String raw, Unit rawUnit, Unit targetUnit, UnitValue defaultValue) {
    if(!raw.equals("")){
      return new ObjectValue(UnitValue.of(Double.parseDouble(raw), rawUnit).in(targetUnit), ValueSource.SUPPLIED);
    }else{
      return new ObjectValue(defaultValue.in(targetUnit), ValueSource.DEFAULT);
    }
  }
}
