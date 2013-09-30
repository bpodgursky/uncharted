package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.AstroConvert;

public class Star {

  private final String name;
  private final Angle rightAscension;
  private final Angle declination;
  private final Double apparentMagnitude;
  private final String spectralType;
  private final Double resultParallax;
  private final Double resultParallaxError;
  private final String component;
  private final String distanceCode;
  private final Double galacticLongitude;
  private final Double galacticLatitude;
  private final Double totalProperMotion;
  private final String flagProperMotion;
  private final Double directionProperMotion;
  private final Double radialVelocity;
  private final String radialVelocityCode;
  private final String referenceSpectralType;
  private final String apparentMagnitudeCode;
  private final String apparentMagnitudeJoint;
  private final String bvColor;
  private final String bvColorCode;
  private final String bvColorJoint;
  private final String ubColor;
  private final String ubColorCode;
  private final String ubColorJoint;
  private final String riColor;
  private final String riColorCode;
  private final String riColorJoint;
  private final Double trigonometricParallax;
  private final Double trigonometricParallaxError;
  private final String resultParallaxCode;
  private final Double absoluteVisualMagnitude;
  private final String absoluteVisualMagnitudeCode;
  private final Double uVelocity;
  private final Double vVelocity;
  private final Double wVelocity;
  private final String henryDraperCatalogNumber;
  private final String durchmusterungNumber;
  private final String giclasNumber;
  private final String lhsNumber;
  private final String otherName;
  private final String remarks;
  private final String starClass;

  //  from the resulting parallax
  private final Double parsecDistance;
  private final Double lightYearDistance;

  private final Coordinate cartesianCoordsInLys;

  public Star(String name, Angle rightAscension, Angle declination, Double apparentMagnitude, String spectralType, Double resultParallax, Double resultParallaxError, String component, String distanceCode, Double galacticLongitude, Double galacticLatitude, Double totalProperMotion, String flagProperMotion, Double directionProperMotion, Double radialVelocity, String radialVelocityCode, String referenceSpectralType, String apparentMagnitudeCode, String apparentMagnitudeJoint, String bvColor, String bvColorCode, String bvColorJoint, String ubColor, String ubColorCode, String ubColorJoint, String riColor, String riColorCode, String riColorJoint, Double trigonometricParallax, Double trigonometricParallaxError, String resultParallaxCode, Double absoluteVisualMagnitude, String absoluteVisualMagnitudeCode, Double uVelocity, Double vVelocity, Double wVelocity, String henryDraperCatalogNumber, String durchmusterungNumber, String giclasNumber, String lhsNumber, String otherName, String remarks, String starClass) {
    this.name = name;
    this.rightAscension = rightAscension;
    this.declination = declination;
    this.apparentMagnitude = apparentMagnitude;
    this.spectralType = spectralType;
    this.resultParallax = resultParallax;
    this.resultParallaxError = resultParallaxError;
    this.component = component;
    this.distanceCode = distanceCode;
    this.galacticLongitude = galacticLongitude;
    this.galacticLatitude = galacticLatitude;
    this.totalProperMotion = totalProperMotion;
    this.flagProperMotion = flagProperMotion;
    this.directionProperMotion = directionProperMotion;
    this.radialVelocity = radialVelocity;
    this.radialVelocityCode = radialVelocityCode;
    this.referenceSpectralType = referenceSpectralType;
    this.apparentMagnitudeCode = apparentMagnitudeCode;
    this.apparentMagnitudeJoint = apparentMagnitudeJoint;
    this.bvColor = bvColor;
    this.bvColorCode = bvColorCode;
    this.bvColorJoint = bvColorJoint;
    this.ubColor = ubColor;
    this.ubColorCode = ubColorCode;
    this.ubColorJoint = ubColorJoint;
    this.riColor = riColor;
    this.riColorCode = riColorCode;
    this.riColorJoint = riColorJoint;
    this.trigonometricParallax = trigonometricParallax;
    this.trigonometricParallaxError = trigonometricParallaxError;
    this.resultParallaxCode = resultParallaxCode;
    this.absoluteVisualMagnitude = absoluteVisualMagnitude;
    this.absoluteVisualMagnitudeCode = absoluteVisualMagnitudeCode;
    this.uVelocity = uVelocity;
    this.vVelocity = vVelocity;
    this.wVelocity = wVelocity;
    this.henryDraperCatalogNumber = henryDraperCatalogNumber;
    this.durchmusterungNumber = durchmusterungNumber;
    this.giclasNumber = giclasNumber;
    this.lhsNumber = lhsNumber;
    this.otherName = otherName;
    this.remarks = remarks;
    this.starClass = starClass;

    this.parsecDistance = 1000 / resultParallax;
    this.lightYearDistance =  3.26163344 * parsecDistance;
    this.cartesianCoordsInLys = AstroConvert.equatorialToCartesian(rightAscension, declination, lightYearDistance);
  }

  public String getName() {
    return name;
  }

  public Angle getRightAscension() {
    return rightAscension;
  }

  public Angle getDeclination() {
    return declination;
  }

  public Double getApparentMagnitude() {
    return apparentMagnitude;
  }

  public String getSpectralType() {
    return spectralType;
  }

  public Double getResultParallax() {
    return resultParallax;
  }

  public Double getResultParallaxError() {
    return resultParallaxError;
  }

  public String getComponent() {
    return component;
  }

  public String getDistanceCode() {
    return distanceCode;
  }

  public Double getGalacticLongitude() {
    return galacticLongitude;
  }

  public Coordinate getCartesianCoordsInLys() {
    return cartesianCoordsInLys;
  }

  public Double getGalacticLatitude() {
    return galacticLatitude;
  }

  public Double getTotalProperMotion() {
    return totalProperMotion;
  }

  public String getFlagProperMotion() {
    return flagProperMotion;
  }

  public Double getDirectionProperMotion() {
    return directionProperMotion;
  }

  public Double getRadialVelocity() {
    return radialVelocity;
  }

  public String getRadialVelocityCode() {
    return radialVelocityCode;
  }

  public String getReferenceSpectralType() {
    return referenceSpectralType;
  }

  public String getApparentMagnitudeCode() {
    return apparentMagnitudeCode;
  }

  public String getApparentMagnitudeJoint() {
    return apparentMagnitudeJoint;
  }

  public String getBvColor() {
    return bvColor;
  }

  public String getBvColorCode() {
    return bvColorCode;
  }

  public String getBvColorJoint() {
    return bvColorJoint;
  }

  public String getUbColor() {
    return ubColor;
  }

  public String getUbColorJoint() {
    return ubColorJoint;
  }

  public String getRiColor() {
    return riColor;
  }

  public String getRiColorCode() {
    return riColorCode;
  }

  public String getRiColorJoint() {
    return riColorJoint;
  }

  public Double getTrigonometricParallax() {
    return trigonometricParallax;
  }

  public Double getParsecDistance() {
    return parsecDistance;
  }

  public Double getTrigonometricParallaxError() {
    return trigonometricParallaxError;
  }

  public String getResultParallaxCode() {
    return resultParallaxCode;
  }

  public Double getAbsoluteVisualMagnitude() {
    return absoluteVisualMagnitude;
  }

  public String getAbsoluteVisualMagnitudeCode() {
    return absoluteVisualMagnitudeCode;
  }

  public double getuVelocity() {
    return uVelocity;
  }

  public double getvVelocity() {
    return vVelocity;
  }

  public double getwVelocity() {
    return wVelocity;
  }

  public String getUbColorCode() {
    return ubColorCode;
  }

  public String getHenryDraperCatalogNumber() {
    return henryDraperCatalogNumber;
  }

  public String getDurchmusterungNumber() {
    return durchmusterungNumber;
  }

  public String getGiclasNumber() {
    return giclasNumber;
  }

  public String getLhsNumber() {
    return lhsNumber;
  }

  public String getOtherName() {
    return otherName;
  }

  public String getStarClass() {
    return starClass;
  }

  public String getRemarks() {
    return remarks;
  }

  @Override
  public String toString() {
    return "Star{" +
        "name='" + name + '\'' +
        ", rightAscension=" + rightAscension +
        ", declination=" + declination +
        ", apparentMagnitude=" + apparentMagnitude +
        ", spectralType='" + spectralType + '\'' +
        ", resultParallax=" + resultParallax +
        ", resultParallaxError=" + resultParallaxError +
        ", component='" + component + '\'' +
        ", distanceCode='" + distanceCode + '\'' +
        ", galacticLongitude=" + galacticLongitude +
        ", galacticLatitude=" + galacticLatitude +
        ", totalProperMotion=" + totalProperMotion +
        ", flagProperMotion='" + flagProperMotion + '\'' +
        ", directionProperMotion=" + directionProperMotion +
        ", radialVelocity=" + radialVelocity +
        ", radialVelocityCode='" + radialVelocityCode + '\'' +
        ", referenceSpectralType='" + referenceSpectralType + '\'' +
        ", apparentMagnitudeCode='" + apparentMagnitudeCode + '\'' +
        ", apparentMagnitudeJoint='" + apparentMagnitudeJoint + '\'' +
        ", bvColor='" + bvColor + '\'' +
        ", bvColorCode='" + bvColorCode + '\'' +
        ", bvColorJoint='" + bvColorJoint + '\'' +
        ", ubColor='" + ubColor + '\'' +
        ", ubColorCode='" + ubColorCode + '\'' +
        ", ubColorJoint='" + ubColorJoint + '\'' +
        ", riColor='" + riColor + '\'' +
        ", riColorCode='" + riColorCode + '\'' +
        ", riColorJoint='" + riColorJoint + '\'' +
        ", trigonometricParallax=" + trigonometricParallax +
        ", trigonometricParallaxError=" + trigonometricParallaxError +
        ", resultParallaxCode='" + resultParallaxCode + '\'' +
        ", absoluteVisualMagnitude=" + absoluteVisualMagnitude +
        ", absoluteVisualMagnitudeCode='" + absoluteVisualMagnitudeCode + '\'' +
        ", uVelocity=" + uVelocity +
        ", vVelocity=" + vVelocity +
        ", wVelocity=" + wVelocity +
        ", henryDraperCatalogNumber='" + henryDraperCatalogNumber + '\'' +
        ", durchmusterungNumber='" + durchmusterungNumber + '\'' +
        ", giclasNumber='" + giclasNumber + '\'' +
        ", lhsNumber='" + lhsNumber + '\'' +
        ", otherName='" + otherName + '\'' +
        ", remarks='" + remarks + '\'' +
        ", starClass='" + starClass + '\'' +
        ", parsecDistance=" + parsecDistance +
        ", lightYearDistance=" + lightYearDistance +
        ", cartesianCoordsInLys=" + cartesianCoordsInLys +
        '}';
  }

  public Double getLightYearDistance() {
    return lightYearDistance;
  }
}
