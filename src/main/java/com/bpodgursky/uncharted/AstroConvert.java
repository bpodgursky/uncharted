package com.bpodgursky.uncharted;

import com.bpodgursky.uncharted.datasets.Angle;
import com.bpodgursky.uncharted.datasets.Coordinate;

public class AstroConvert {

  public static Coordinate equatorialToCartesian(Angle rightAscension, Angle declination, double distance){

    double x = distance * Math.cos(rightAscension.getRadians()) * Math.cos(declination.getRadians());
    double y = distance * Math.sin(rightAscension.getRadians()) * Math.cos(declination.getRadians());
    double z = distance * Math.sin(declination.getRadians());


    Coordinate coordinate = new Coordinate(x, y, z);

    System.out.println(coordinate);

    return coordinate;
  }

}
