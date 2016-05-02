package com.bpodgursky.uncharted.datasets;

public class StarRecord {

  private final StarIdentifiers identifiers;
  private final ExternalLinks links;

  private final Double lightYearDistance;
  private final Double rightAscensionRadians;
  private final Double declinationRadians;
  private final Double absoluteMagnitude;
  private final String rawStellarClassification;
  private final StellarClassification parsedStellarClassification;
  private final Coordinate cartesianCoordsInLys;


  private final Double temperatureEstimate;
  private final Double radiusInLys;

  public StarRecord(StarIdentifiers identifiers,
                    ExternalLinks links,
                    Double lightYearDistance,
                    Double rightAscensionRadians,
                    Double declinationRadians,
                    Double absoluteMagnitude,
                    String stellarClass,
                    Double colorIndex,
                    Double luminosity) {
    this.identifiers = identifiers;
    this.links = links;
    this.lightYearDistance = lightYearDistance;
    this.rightAscensionRadians = rightAscensionRadians;
    this.declinationRadians = declinationRadians;
    this.absoluteMagnitude = absoluteMagnitude;
    this.rawStellarClassification = stellarClass;
    this.parsedStellarClassification = StarClassHelper.parseClass(stellarClass);
    this.cartesianCoordsInLys = AstroConvert.equatorialToCartesian(rightAscensionRadians, declinationRadians, lightYearDistance);


    if (colorIndex == null) {
      this.temperatureEstimate = StarClassHelper.getTemperatureEstimate(parsedStellarClassification);
    } else {
      this.temperatureEstimate = AstroConvert.bvToTemperature(colorIndex);
    }

    this.radiusInLys = AstroConvert.getRadiusLys(luminosity, temperatureEstimate)*1000000;
    System.out.println(radiusInLys);

  }

  public StarIdentifiers getIdentifiers() {
    return identifiers;
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

  public String getRawStellarClassification() {
    return rawStellarClassification;
  }

  public StellarClassification getParsedStellarClassification() {
    return parsedStellarClassification;
  }

  public Double getTemperatureEstimate() {
    return temperatureEstimate;
  }
}
