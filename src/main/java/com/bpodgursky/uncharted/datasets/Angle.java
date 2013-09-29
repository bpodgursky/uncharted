package com.bpodgursky.uncharted.datasets;

public class Angle {
  private final double hour;
  private final double minute;
  private final double second;

  public Angle(double hour, double minute, double second) {
    this.hour = hour;
    this.minute = minute;
    this.second = second;
  }

  public static Angle parse(String str){
    String[] split = str.split(" ");

    double hour = Double.parseDouble(split[0]);
    double minute = Double.parseDouble(split[1]);
    double second = Double.parseDouble(split[2]);

    return new Angle(hour, minute, second);
  }

  public double getHour() {
    return hour;
  }

  public double getMinute() {
    return minute;
  }

  public double getSecond() {
    return second;
  }

  @Override
  public String toString() {
    return "Angle{" +
        "hour=" + hour +
        ", minute=" + minute +
        ", second=" + second +
        '}';
  }
}
