package com.bpodgursky.uncharted.datasets.catalogs;

import java.util.Map;

import com.bpodgursky.uncharted.datasets.AstroConvert;
import com.bpodgursky.uncharted.datasets.PlanetProperties;
import com.bpodgursky.uncharted.util.MapBuilder;

//  TODO everything in here is very unscientific
//  try to calculate from each other?  guess better?
public class PlanetDefaults {

  public static Map<PlanetProperties, PlanetValue> getPlanetProperties(String semiMajorAxisRaw,
                                                                       String eccentricity,
                                                                       String orbitalPeriodDays,
                                                                       String inclination,
                                                                       String massJup,
                                                                       String radiusJup,
                                                                       String density){
    return new MapBuilder<PlanetProperties, PlanetValue>()
        .put(PlanetProperties.SEMI_MAJOR_AXIS_LYS,  getOrDefault(semiMajorAxisRaw, 0.387098, AstroConvert.LIGHTYEARS_PER_AU))  //  Mercury (idk)
        .put(PlanetProperties.ECCENTRICTY, getOrDefault(eccentricity, 0.0))  //  circle
        .put(PlanetProperties.ORBITAL_PERIOD_DAYS, getOrDefault(orbitalPeriodDays, 88))
        .put(PlanetProperties.INCLINATION, getOrDefault(inclination, 0.0))
        .put(PlanetProperties.MASS_KG, getOrDefault(massJup, 1.0, AstroConvert.JUP_MASS_IN_KG))
        .put(PlanetProperties.RADIUS_LYS, getOrDefault(radiusJup, 1.0, AstroConvert.JUP_RADIUS_IN_LYS))
        .put(PlanetProperties.DENSITY_GCC, getOrDefault(density, 1.326)) //  jupiter
        .get();

  }

  private static PlanetValue getOrDefault(String valueRaw, double defaultValue){
    return getOrDefault(valueRaw, defaultValue, 1.0);
  }

  private static PlanetValue getOrDefault(String valueRaw, double defaultValue, double conversionFactor){
    if(!valueRaw.equals("")){
      return PlanetValue.of(Double.parseDouble(valueRaw)*conversionFactor, ValueSource.SUPPLIED);
    }
    return PlanetValue.of(defaultValue*conversionFactor, ValueSource.DEFAULT);
  }


}
