package com.bpodgursky.uncharted.datasets.catalogs;

//  TODO everything in here is very unscientific
//  try to calculate from each other?  guess better?
public class PlanetDefaults {

//  public static Map<PlanetProperties, ObjectValue> getPlanetProperties(String semiMajorAxisRaw,
//                                                                       String eccentricity,
//                                                                       String orbitalPeriodDays,
//                                                                       String inclination,
//                                                                       String massJup,
//                                                                       String radiusJup,
//                                                                       String density) {
//    return new MapBuilder<PlanetProperties, ObjectValue>()
//        .put(PlanetProperties.SEMI_MAJOR_AXIS_LYS, getOrDefault(semiMajorAxisRaw, , AstroConvert.LIGHTYEARS_PER_AU))  //  Mercury (idk)
//        .put(PlanetProperties.ECCENTRICTY, getOrDefault(eccentricity, 0.0))  //  circle
//        .put(PlanetProperties.ORBITAL_PERIOD_DAYS, getOrDefault(orbitalPeriodDays, 88))
//        .put(PlanetProperties.INCLINATION, getOrDefault(inclination, 0.0))
//        .put(PlanetProperties.MASS_KG, getOrDefault(massJup, 1.0, AstroConvert.JUP_MASS_IN_KG))
//        .put(PlanetProperties.RADIUS_LYS, getOrDefault(radiusJup, 1.0, AstroConvert.JUP_RADIUS_IN_LYS))
//        .put(PlanetProperties.DENSITY_GCC, getOrDefault(density, 1.326)) //  jupiter
//        .get();
//
//  }

  public static final UnitValue DEFAULT_SEMI_MAJOR_AXIS = new UnitValue(0.387098, Unit.AU); //  mercury AUs
  public static final UnitValue DEFAULT_ECCENTRICITY = new UnitValue(0.0, Unit.NONE);
  public static final UnitValue DEFAULT_ORBITAL_PERIOD = new UnitValue(88.0, Unit.DAY); //  mercury
  public static final UnitValue DEFAULT_INCLINATION = new UnitValue(0.0, Unit.NONE);
  public static final UnitValue DEFAULT_MASS = new UnitValue(1.0, Unit.MASS_JUP);
  public static final UnitValue DEFAULT_RADIUS = new UnitValue(1.0, Unit.RADIUS_JUP);
  public static final UnitValue DENSITY = new UnitValue(1.326, Unit.G_PER_CC); // jupiter

//  private static ObjectValue getOrDefault(String valueRaw, double defaultValue) {
//    return getOrDefault(valueRaw, defaultValue, 1.0);
//  }

  //
  //  private static ObjectValue getOrDefault(String valueRaw, double defaultValue, double conversionFactor){
  //    if(!valueRaw.equals("")){
  //      return ObjectValue.of(Double.parseDouble(valueRaw)*conversionFactor, ValueSource.SUPPLIED);
  //    }
  //    return ObjectValue.of(defaultValue*conversionFactor, ValueSource.DEFAULT);
  //  }


}
