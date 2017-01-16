package com.bpodgursky.uncharted.datasets.identifiers;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bpodgursky.uncharted.datasets.Constellation;
import com.bpodgursky.uncharted.datasets.ConstellationCorpus;
import com.bpodgursky.uncharted.datasets.GreekLetter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

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

  public String getPrettyName() {
    return prettyName;
  }

  public Integer getFlamsteedDesignation() {
    return flamsteedDesignation;
  }

  public String getBayerDesignation() {
    return bayerDesignation;
  }

  public Integer getNumber() {
    return number;
  }

  public Constellation getConstellation() {
    return constellation;
  }

  private static final Pattern PATTERN1 = Pattern.compile("(?<flam>[\\d]+) *(?<bayer>[a-zA-Z]+) *(?<number>[\\d]+) *(?<cons>[a-zA-Z]+)");
  private static final Pattern PATTERN2 = Pattern.compile("(?<bayer>[a-zA-Z]+) *(?<cons>[a-zA-Z]+)");
  private static final Pattern PATTERN3 = Pattern.compile("(?<bayer>[a-zA-Z]+) *(?<number>[\\d]+) *(?<cons>[a-zA-Z]+)");
  private static final Pattern PATTERN4 = Pattern.compile("(?<flam>[\\d]+) *(?<cons>[a-zA-Z]+)");
  private static final Pattern PATTERN5 = Pattern.compile("(?<flam>[\\d]+) *(?<bayer>[a-zA-Z]+) *(?<cons>[a-zA-Z]+)");

  public static BayerFlamsteed parse(String s) throws IOException {

    String flam= null;
    String bayer= null;
    String numberStr= null;
    String constellation = null;

    Matcher matcher1 = PATTERN1.matcher(s);
    Matcher matcher2 = PATTERN2.matcher(s);
    Matcher matcher3 = PATTERN3.matcher(s);
    Matcher matcher4 = PATTERN4.matcher(s);
    Matcher matcher5 = PATTERN5.matcher(s);

    if(matcher1.matches()){
      flam = matcher1.group("flam");
      bayer = matcher1.group("bayer");
      numberStr = matcher1.group("number");
      constellation = matcher1.group("cons");
    } else if(matcher2.matches()){
      bayer = matcher2.group("bayer");
      constellation = matcher2.group("cons");
    }else if(matcher3.matches()){
      bayer = matcher3.group("bayer");
      numberStr = matcher3.group("number");
      constellation = matcher3.group("cons");
    }else if(matcher4.matches()){
      flam = matcher4.group("flam");
      constellation = matcher4.group("cons");
    }else if(matcher5.matches()){
      flam = matcher5.group("flam");
      bayer = matcher5.group("bayer");
      constellation = matcher5.group("cons");
    }else{
      return null;
    }

    Integer flamNum= null;
    if(flam != null){
       flamNum = Integer.parseInt(flam);
    }

    Integer number = null;
    if(numberStr != null){
      number = Integer.parseInt(numberStr);
    }

    Constellation constellation1 = ConstellationCorpus.get().get(constellation);
    if(constellation == null || flam == null && bayer == null){
      throw new RuntimeException(s);
    }

    return new BayerFlamsteed(flamNum, GreekLetter.getByAbbr(bayer), number, constellation1);

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BayerFlamsteed)) {
      return false;
    }

    BayerFlamsteed that = (BayerFlamsteed)o;

    if (flamsteedDesignation != null ? !flamsteedDesignation.equals(that.flamsteedDesignation) : that.flamsteedDesignation != null) {
      return false;
    }
    if (bayerDesignation != null ? !bayerDesignation.equals(that.bayerDesignation) : that.bayerDesignation != null) {
      return false;
    }
    if (number != null ? !number.equals(that.number) : that.number != null) {
      return false;
    }
    if (constellation != null ? !constellation.equals(that.constellation) : that.constellation != null) {
      return false;
    }
    return prettyName != null ? prettyName.equals(that.prettyName) : that.prettyName == null;

  }

  @Override
  public int hashCode() {
    int result = flamsteedDesignation != null ? flamsteedDesignation.hashCode() : 0;
    result = 31 * result + (bayerDesignation != null ? bayerDesignation.hashCode() : 0);
    result = 31 * result + (number != null ? number.hashCode() : 0);
    result = 31 * result + (constellation != null ? constellation.hashCode() : 0);
    result = 31 * result + (prettyName != null ? prettyName.hashCode() : 0);
    return result;
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
