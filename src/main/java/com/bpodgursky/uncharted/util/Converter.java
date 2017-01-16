package com.bpodgursky.uncharted.util;

import com.bpodgursky.uncharted.datasets.catalogs.Unit;
import com.bpodgursky.uncharted.datasets.catalogs.UnitValue;

import com.liveramp.commons.collections.nested_map.TwoNestedMap;


public class Converter {

  private static final TwoNestedMap<Unit, Unit, Double> CONVERSIONS = new TwoNestedMap<>();

  static void addConversion(Unit from, Unit to, Double factor) {
    CONVERSIONS.put(from, to, factor);
    CONVERSIONS.put(to, from, 1.0/factor);
  }

  static {

    for (Unit unit : Unit.values()) {
      addConversion(unit, unit, 1.0);
    }

    addConversion(Unit.AU, Unit.LY, 1.58125e-5);
    addConversion(Unit.MASS_JUP, Unit.KG, 1.898e27);
    addConversion(Unit.RADIUS_JUP, Unit.KM, 69911.0);
    addConversion(Unit.RADIUS_JUP, Unit.LY, 7.3896e-9);
    addConversion(Unit.RADIUS_EARTH, Unit.RADIUS_JUP, 0.146822127);
    addConversion(Unit.RADIUS_EARTH, Unit.LY, 6.734e-10);

  }


  public static UnitValue convert(UnitValue value, Unit to) {
    return convert(value.getQuantity(), value.getUnit(), to);
  }

  public static UnitValue convert(double value, Unit from, Unit to){
    Double factor = CONVERSIONS.get(from, to);

    if(factor == null){
      throw new RuntimeException("Cannot convert from "+from+" to "+to);
    }

    return new UnitValue(value * factor, to);
  }

}
