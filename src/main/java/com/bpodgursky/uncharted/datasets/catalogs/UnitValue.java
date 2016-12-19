package com.bpodgursky.uncharted.datasets.catalogs;

import com.bpodgursky.uncharted.util.Converter;

public class UnitValue {

  private final Unit unit;
  private final Double quantity;

  public UnitValue(Double value, Unit unit) {
    this.unit = unit;
    this.quantity = value;
  }

  public Unit getUnit() {
    return unit;
  }

  public Double getQuantity() {
    return quantity;
  }

  public static UnitValue of(Double value, Unit unit){
    return new UnitValue(value, unit);
  }

  public UnitValue in(Unit unit){
    return Converter.convert(this, unit);
  }

}
