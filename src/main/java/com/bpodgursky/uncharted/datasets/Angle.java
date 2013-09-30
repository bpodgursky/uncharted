package com.bpodgursky.uncharted.datasets;

public class Angle {

  private final int hour;
  private final int arcMinutes;
  private final double arcSeconds;

  private final double radians;

  public static final double RADIANS_PER_HOUR = Math.PI / 12;
  public static final double RADIANS_PER_ARCMINUTE = Math.PI / 720;
  public static final double RADIANS_PER_ARCSECOND = Math.PI / 43200;

  public Angle(int hour, int minute, double arcSeconds) {
    this.hour = hour;
    this.arcMinutes = minute;
    this.arcSeconds = arcSeconds;

    this.radians = hour * RADIANS_PER_HOUR +
        arcMinutes * RADIANS_PER_ARCMINUTE +
        arcSeconds * RADIANS_PER_ARCSECOND;
  }

  public static Angle parse(String str){
    String[] split = str.split(" ");

    int degrees = Integer.parseInt(split[0]);
    int minute = Integer.parseInt(split[1]);
    double second = Double.parseDouble(split[2]);

    return new Angle(degrees, minute, second);
  }

  public int getDegrees() {
    return hour;
  }

  public int getArcMinutes() {
    return arcMinutes;
  }

  public double getArcSeconds() {
    return arcSeconds;
  }

  public double getRadians(){
    return radians;
  }


  @Override
  public String toString() {
    return "Angle{" +
        "hour=" + hour +
        ", arcMinutes=" + arcMinutes +
        ", arcSeconds=" + arcSeconds +
        '}';
  }
}
