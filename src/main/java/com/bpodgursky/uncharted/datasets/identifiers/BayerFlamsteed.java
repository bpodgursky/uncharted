package com.bpodgursky.uncharted.datasets.identifiers;

import com.bpodgursky.uncharted.datasets.ConstellationCorpus;
import com.bpodgursky.uncharted.datasets.Constellation;
import com.bpodgursky.uncharted.datasets.GreekLetter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BayerFlamsteed {

  private Integer flamsteedDesignation;
  private String bayerDesignation;
  private Integer number;
  private Constellation constellation;

  private String prettyName;

  public BayerFlamsteed(Integer designation, String bayerDesignation, Integer number, Constellation constellation){
    this.flamsteedDesignation = designation;
    this.bayerDesignation = bayerDesignation;
    this.constellation = constellation;
    this.number = number;

    List<String> parts = Lists.newArrayList();
    if(designation != null){
      parts.add(designation.toString());
    }
    if(bayerDesignation != null){
      parts.add(bayerDesignation);
    }
    if(number != null){
      parts.add(number.toString());
    }
    if(constellation != null){
      parts.add(constellation.getGenitive());
    }

    this.prettyName = StringUtils.join(parts, " ");
  }


  private static final Pattern PATTERN = Pattern.compile("(?<flam>[\\d]+)? *(?<bayer>[a-zA-Z]+)? *(?<number>[\\d]+)? *(?<cons>[a-zA-Z]+)?");

  public static BayerFlamsteed parse(String s) throws IOException {

    Matcher matcher = PATTERN.matcher(s);

    if(matcher.matches()){

      String flam = matcher.group("flam");
      String bayer = matcher.group("bayer");
      String numberStr = matcher.group("number");
      String constellation = matcher.group("cons");

      Integer flamNum= null;
      if(flam != null){
         flamNum = Integer.parseInt(flam);
      }

      Integer number = null;
      if(numberStr != null){
        number = Integer.parseInt(numberStr);
      }

      return new BayerFlamsteed(flamNum, GreekLetter.getByAbbr(bayer), number, ConstellationCorpus.get().get(constellation));
    }


    throw new RuntimeException("Unable to parse BF designation: "+s);


  }

  @Override
  public String toString() {
    return "BayerFlamsteed{" +
        "flamsteedDesignation=" + flamsteedDesignation +
        ", bayerDesignation='" + bayerDesignation + '\'' +
        ", number=" + number +
        ", constellation=" + constellation +
        ", prettyName='" + prettyName + '\'' +
        '}';
  }
}
