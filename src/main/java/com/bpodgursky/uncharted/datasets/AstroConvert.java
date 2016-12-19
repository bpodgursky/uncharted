package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.datasets.catalogs.Unit;
import com.bpodgursky.uncharted.datasets.catalogs.UnitValue;

public class AstroConvert {

  public static final double RADIANS_PER_HOUR = Math.PI / 12;
  public static final double RADIANS_PER_ARCMINUTE = Math.PI / 720;
  public static final double RADIANS_PER_ARCSECOND = Math.PI / 43200;

  public static final double DEGREE_IN_RADIANS = 0.0174532925;
  public static final double LIGHTYEARS_PER_PARSEC = 3.26163344;
  public static final double LIGHTYEARS_PER_AU = 1.58125e-5;

  public static final double JUP_RADIUS_IN_LYS = 7.3896527e-9;
  public static final Double JUP_MASS_IN_KG = 1.898e27;

  //  from https://en.wikipedia.org/wiki/Color_index
  public static Double bvToTemperature(Double bvIndex){

    double p1 = 1.0 / (.92 * bvIndex + 1.7);
    double p2 = 1.0 / (.92 * bvIndex + .62);

    return 4600 * (p1 + p2);

  }

  //  http://skyserver.sdss.org/dr1/en/proj/advanced/hr/radius1.asp
  public static Double getRadiusLys(Double solLuminosityMultiples, UnitValue temperature){
    Double luminosityWatts = solLuminosityMultiples*3.846*Math.pow(10, 26);
    Double radiusMeters = Math.sqrt(luminosityWatts / (4*Math.PI*5.67*Math.pow(10, -8) * Math.pow(temperature.in(Unit.K).getQuantity(), 4)));
    return radiusMeters / (9.461*Math.pow(10, 15));

  }

  public static Coordinate equatorialToCartesian(Double rightAscensionRadians, Double declinationRadians, double distance){

    double x = distance * Math.cos(rightAscensionRadians) * Math.cos(declinationRadians);
    double y = distance * Math.sin(rightAscensionRadians) * Math.cos(declinationRadians);
    double z = distance * Math.sin(declinationRadians);

    return new Coordinate(x, y, z);
  }

  public static Double parallaxToLightyears(double parallax){
    return (1000 / parallax) * LIGHTYEARS_PER_PARSEC;
  }

  public static Double parsecsToLightyears(double parsecs){
    return LIGHTYEARS_PER_PARSEC * parsecs;
  }

  public static Double auToLightyears(double au){
    return LIGHTYEARS_PER_AU*au;
  }

  public static Double hoursToRadians(double hours){
    return RADIANS_PER_HOUR * hours;
  }

  public static Double degreesToRadians(double degrees){
    return DEGREE_IN_RADIANS * degrees;
  }

  public static Double parseDegreeMinSec(String str){
    String[] split = str.split(" ");

    int degrees = Integer.parseInt(split[0]);
    int minute = Integer.parseInt(split[1]);
    double second = Double.parseDouble(split[2]);

    return degrees * RADIANS_PER_HOUR +
        minute * RADIANS_PER_ARCMINUTE +
        second * RADIANS_PER_ARCSECOND;
  }

  public static double jupRadiiToLys(double jupRadii){
    return jupRadii * JUP_RADIUS_IN_LYS;
  }

  public static double jupMassInKg(double jupMasses){
    return jupMasses * JUP_MASS_IN_KG;
  }

}
