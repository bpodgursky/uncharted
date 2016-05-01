package com.bpodgursky.uncharted.datasets;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bpodgursky.uncharted.datasets.catalogs.GlieseCatalog;
import com.bpodgursky.uncharted.util.MapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * Look, I'm not happy about this class. Nobody would be.  It's a massive hack around a really confusingly defined
 * classification system, trying to squish things into little boxes.  Someone else please fix this.
 */
public class StarClassHelper {
  private static final Logger LOG = LoggerFactory.getLogger(GlieseCatalog.class);


  private static final Pattern SPEC_BASE = Pattern.compile("([obafgkmlty])\\+?");

  private static final Pattern SPEC_LUMINOSITY_PATTERN2 = Pattern.compile("(?<class>[OBAFGKMLTY])[\\+ \\-]*(?<range>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?[\\+ \\-]*\\(?(?<luminosity>[IV]+([\\-/][IV]+)?)?\\)?[\\+ \\-]*[CNG:Vnv PwkmpKabesJ]*\\.*");

  private static final Pattern SPEC_WHITE_DWARF = Pattern.compile("(?<type>D[ABOQZCX]):?(?<temp>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?\\+?[CNG:Vnv wkPmpKsabeJ]*\\.*");

  private static final Pattern DWARF_CLASSIFICATION = Pattern.compile("d(?<class>[OBAFGKMLTY])(?<range>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?[CNG:VnKv wkPmpabeJ]*\\.*");

  private static final Pattern SUB_DWARF_CLASSIFICATION = Pattern.compile("sd(?<class>[OBAFGKMLTY])(?<range>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?[CNG:VnKv wkPmpabeJ]*\\.*");


  //  from https://en.wikipedia.org/wiki/Stellar_classification, taking medians
  private static final Map<SpectralClass, Integer> SPEC_CLASS_TO_TEMP = MapBuilder.of(SpectralClass.O, 30000)
      .put(SpectralClass.B, 20000) //  10,000–30,000 K
      .put(SpectralClass.A, 8750) //  7,500–10,000
      .put(SpectralClass.F, 6750) //  6,000–7,500
      .put(SpectralClass.G, 5600) //  5,200–6,000
      .put(SpectralClass.K, 4450) //  3,700–5,200
      .put(SpectralClass.M, 3050) //  2,400–3,700

      //  TODO brown dwarfs -- no idea
      .put(SpectralClass.L, 1000)
      .put(SpectralClass.T, 1000)
      .put(SpectralClass.Y, 1000)

      //  TODO most of this white dwarf temperature is made up.  get real numbers
      .put(SpectralClass.DA, 15000)
      .put(SpectralClass.DAV, 15000)
      .put(SpectralClass.DZ, 15000)
      .put(SpectralClass.DQP, 15000)
      .put(SpectralClass.DX, 15000)
      .put(SpectralClass.DZA, 15000)
      .put(SpectralClass.DZQ, 15000)
      .put(SpectralClass.DQ, 15000)
      .put(SpectralClass.DO, 75000)
      .put(SpectralClass.DB, 20000)
      .put(SpectralClass.DC, 10000)

      .get();

  //  this is a fallback if we have no bv index
  public static double getTemperatureEstimate(StellarClassification classification) {

    //  TODO lolidk
    if(classification.getMainClass() == null){
      return 10000;
    }

    //  TODO use range as well?
    return SPEC_CLASS_TO_TEMP.get(classification.getMainClass());

  }

  public static StellarClassification parseClass(String starClass) {

    if (starClass.equals("")) {
      return new StellarClassification();
    }

    if (starClass.equals("pec")) {
      return new StellarClassification();
    }

    //  TODO I'm not an astronomer, are these next three ok?  Call it halfway

    if (starClass.equals("k-m")) {
      return new StellarClassification()
          .setMainClass(SpectralClass.K)
          .setRange(5.0);
    }

    if (starClass.equals("g-k")) {
      return new StellarClassification()
          .setMainClass(SpectralClass.G)
          .setRange(5.0);
    }

    if (starClass.equals("f-g")) {
      return new StellarClassification()
          .setMainClass(SpectralClass.F)
          .setRange(5.0);
    }

    Matcher specBase = SPEC_BASE.matcher(starClass);
    if (specBase.matches()) {

      return new StellarClassification()
          .setMainClass(SpectralClass.valueOf(specBase.group(1).toUpperCase()));
    }

    Matcher specDwarf = DWARF_CLASSIFICATION.matcher(starClass);
    if (specDwarf.matches()) {
      String aClass = specDwarf.group("class");
      String range = specDwarf.group("range");

      StellarClassification star = new StellarClassification();
      star.setMainClass(SpectralClass.valueOf(aClass));

      if (range != null) {
        star.setRange(Double.parseDouble(range.split("[\\-/]")[0]));
      }

      star.setLuminosityClass(LuminosityClass.V);

      return star;
    }
    Matcher specSubDwarf = SUB_DWARF_CLASSIFICATION.matcher(starClass);
    if (specSubDwarf.matches()) {
      String aClass = specSubDwarf.group("class");
      String range = specSubDwarf.group("range");

      StellarClassification star = new StellarClassification();
      star.setMainClass(SpectralClass.valueOf(aClass));

      if (range != null) {
        star.setRange(Double.parseDouble(range.split("[\\-/]")[0]));
      }

      star.setLuminosityClass(LuminosityClass.VI);

      return star;
    }

    Matcher specLumin2 = SPEC_LUMINOSITY_PATTERN2.matcher(starClass);
    if (specLumin2.matches()) {
      String mainClass = specLumin2.group("class");
      String range = specLumin2.group("range");
      String luminosity = specLumin2.group("luminosity");

      StellarClassification star = new StellarClassification()
          .setMainClass(SpectralClass.valueOf(mainClass));

      if (range != null) {
        //  for "K3/4 V" take the first one
        star.setRange(Double.parseDouble(range.split("[\\-/]")[0]));
      }

      if (luminosity != null) {
        //  for "III-IV" take the first one
        star.setLuminosityClass(LuminosityClass.valueOf(luminosity.split("[\\-/]")[0]));
      }

      return star;
    }

    Matcher specWD = SPEC_WHITE_DWARF.matcher(starClass);
    if (specWD.matches()) {
      String type = specWD.group("type");
      String temp = specWD.group("temp");

      StellarClassification star = new StellarClassification();
      star.setMainClass(SpectralClass.valueOf(type));

      if (temp != null) {
        star.setWhiteDwarfTemp(Double.parseDouble(temp.split("[\\-/]")[0]));
      }

      return star;
    }

    LOG.warn("Warning: could not parse star classification: " + starClass);
    return new StellarClassification();
  }

}
