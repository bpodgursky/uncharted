package com.bpodgursky.uncharted.datasets;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarClassifier2 {
  private static final Logger LOG = LoggerFactory.getLogger(StarClassifier2.class);

  private static final Set<String> CLASSES = Sets.newHashSet(
      "O", "B", "A", "F", "G", "K", "M", "L", "T", "Y"
  );

  //  DA, DAV, DB, DC, DO, DQ, DQP, DX, DZ, DZA, DZQ
  private static final Pattern WHITE_DWARN_PATTERN = Pattern.compile("(D[AVBCOQPXZGKM]+)([0-9.]*)");

  public static ParsedClassification parseClass(String starClass) {

    //  ignore subdwarf designator
    if (starClass.startsWith("sd")) {
      starClass = starClass.substring(2);
    }

    String trimmedClass = starClass
        .replaceAll("(w|wk|wl)$", "")
        .replaceAll("[ :.!+es]+$", "")
        .toUpperCase();

    if (trimmedClass.isEmpty()) {
      return new ParsedClassification();
    }

    String classLetter = trimmedClass.substring(0, 1);
    if (CLASSES.contains(classLetter)) {
      return new ParsedClassification()
          .setMainClass(SpectralClass.valueOf(classLetter));
    }

    //  old red dwarf designator
    if (starClass.startsWith("dM")) {
      return new ParsedClassification()
          .setMainClass(SpectralClass.dM);
    }


    Matcher whiteDwarf = WHITE_DWARN_PATTERN.matcher(trimmedClass);
    if (whiteDwarf.matches()) {
      ParsedClassification parsedClassification = new ParsedClassification()
          .setMainClass(SpectralClass.valueOf(whiteDwarf.group(1)));

      if (!whiteDwarf.group(2).equals("")) {
        parsedClassification.setWhiteDwarfTempIndex(Double.parseDouble(whiteDwarf.group(2)));
      }

      return parsedClassification;
    }

    //  TODO: this covers about 116134 of 116564 in the HYG dataset.  there's random stuff like
    //  carbon stars which aren't covered.  needs to be finished.

    LOG.warn("Warning: could not parse star classification: " + starClass);
    return new ParsedClassification();
  }

  public static void main(String[] args) {
    System.out.println("DA wk".replaceAll("(wk|wl)$", ""));
  }

}
