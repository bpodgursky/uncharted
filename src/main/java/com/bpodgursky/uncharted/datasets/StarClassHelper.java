package com.bpodgursky.uncharted.datasets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * Look, I'm not happy about this class. Nobody would be.  It's a massive hack around a really confusingly defined
 * classification system, trying to squish things into little boxes.  Someone else please fix this.
 */
public class StarClassHelper {
  private static final Logger LOG = LoggerFactory.getLogger(GlieseCatalog.class);


  private static final Pattern SPEC_BASE = Pattern.compile("([obafgkmlty])\\+?");

  private static final Pattern SPEC_LUMINOSITY_PATTERN2 = Pattern.compile("(?<class>[OBAFGKMLTY])[\\+ \\-]*(?<range>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?[\\+ \\-]*\\(?(?<luminosity>[IV]+([\\-/][IV]+)?)?\\)?[\\+ \\-]*[CNG:Vnv PwkmpKabesJ]*");

  private static final Pattern SPEC_WHITE_DWARF = Pattern.compile("(?<type>D[ABOQZCX]):?(?<temp>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?\\+?[CNG:Vnv wkPmpKsabeJ]*");

  private static final Pattern DWARF_CLASSIFICATION = Pattern.compile("d(?<class>[OBAFGKMLTY])(?<range>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?[CNG:VnKv wkPmpabeJ]*");

  private static final Pattern SUB_DWARF_CLASSIFICATION = Pattern.compile("sd(?<class>[OBAFGKMLTY])(?<range>\\d+\\.?\\d*([\\-/]\\d+\\.?\\d*)?)?[CNG:VnKv wkPmpabeJ]*");

  public static StellarClassification parseClass(String glieseType) {

    if (glieseType.equals("")) {
      return new StellarClassification();
    }

    if (glieseType.equals("pec")) {
      return new StellarClassification();
    }

    //  TODO I'm not an astronomer, are these next three ok?  Call it halfway

    if (glieseType.equals("k-m")) {
      return new StellarClassification()
          .setMainClass(SpectralClass.K)
          .setRange(5.0);
    }

    if (glieseType.equals("g-k")) {
      return new StellarClassification()
          .setMainClass(SpectralClass.G)
          .setRange(5.0);
    }

    if (glieseType.equals("f-g")) {
      return new StellarClassification()
          .setMainClass(SpectralClass.F)
          .setRange(5.0);
    }

    Matcher specBase = SPEC_BASE.matcher(glieseType);
    if (specBase.matches()) {

      return new StellarClassification()
          .setMainClass(SpectralClass.valueOf(specBase.group(1).toUpperCase()));
    }

    Matcher specDwarf = DWARF_CLASSIFICATION.matcher(glieseType);
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
    Matcher specSubDwarf = SUB_DWARF_CLASSIFICATION.matcher(glieseType);
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

    Matcher specLumin2 = SPEC_LUMINOSITY_PATTERN2.matcher(glieseType);
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

    Matcher specWD = SPEC_WHITE_DWARF.matcher(glieseType);
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

    LOG.warn("Warning: could not parse star classification: "+glieseType);
    return new StellarClassification();
  }

}
