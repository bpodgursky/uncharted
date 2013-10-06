package com.bpodgursky.uncharted.datasets;

import java.util.Map;

public class StarRecord {

  private final Map<DatasetName, String> allIdentifiers;

  private final String primaryId;
  private final Double lightYearDistance;
  private final Double rightAscensionRadians;
  private final Double declinationRadians;
  private final Double absoluteMagnitude;

  private final String rawStellarClassification;
  private final StellarClassification parsedStellarClassification;

  private final Coordinate cartesianCoordsInLys;

  public StarRecord(String primaryId,
      Map<DatasetName, String> allIdentifiers,
      Double lightYearDistance,
      Double rightAscensionRadians,
      Double declinationRadians,
      Double absoluteMagnitude,
      String stellarClass) {
    this.primaryId = primaryId;
    this.allIdentifiers = allIdentifiers;
    this.lightYearDistance = lightYearDistance;
    this.rightAscensionRadians = rightAscensionRadians;
    this.declinationRadians = declinationRadians;
    this.absoluteMagnitude = absoluteMagnitude;
    this.rawStellarClassification = stellarClass;
    this.parsedStellarClassification = StarClassHelper.parseClass(stellarClass);
    this.cartesianCoordsInLys = AstroConvert.equatorialToCartesian(rightAscensionRadians, declinationRadians, lightYearDistance);
  }

  public Map<DatasetName, String> getIdentifiers() {
    return allIdentifiers;
  }

  public Double getLightYearDistance() {
    return lightYearDistance;
  }

  public Double getRightAscensionRadians() {
    return rightAscensionRadians;
  }

  public Double getDeclinationRadians() {
    return declinationRadians;
  }

  public Double getAbsoluteMagnitude() {
    return absoluteMagnitude;
  }

  public Coordinate getCartesianCoordsInLys() {
    return cartesianCoordsInLys;
  }

  public Map<DatasetName, String> getAllIdentifiers() {
    return allIdentifiers;
  }

  public String getPrimaryId() {
    return primaryId;
  }

  public String getRawStellarClassification() {
    return rawStellarClassification;
  }

  public StellarClassification getParsedStellarClassification() {
    return parsedStellarClassification;
  }


  @Override
  public String toString() {
    return "StarRecord{" +
        "allIdentifiers=" + allIdentifiers +
        ", primaryId='" + primaryId + '\'' +
        ", lightYearDistance=" + lightYearDistance +
        ", rightAscensionRadians=" + rightAscensionRadians +
        ", declinationRadians=" + declinationRadians +
        ", absoluteMagnitude=" + absoluteMagnitude +
        ", rawStellarClassification='" + rawStellarClassification + '\'' +
        ", parsedStellarClassification=" + parsedStellarClassification +
        ", cartesianCoordsInLys=" + cartesianCoordsInLys +
        '}';
  }
}
