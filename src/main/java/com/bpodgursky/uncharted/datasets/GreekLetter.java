package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.util.MapBuilder;

import java.util.Map;

//  mapping from http://www.skyviewcafe.com/bayer_flamsteed.html
public class GreekLetter {

  private static final Map<String, String> abbrToLetter = new MapBuilder<String, String>()
      .put("Alp", "alpha")
      .put("Bet", "beta")
      .put("Gam", "gamma")
      .put("Del", "delta")
      .put("Eps", "epsilon")
      .put("Zet", "zeta")
      .put("Eta", "eta")
      .put("The", "theta")
      .put("Iot", "iota")
      .put("Kap", "kappa")
      .put("Lam", "lambda")
      .put("Mu", "mu")
      .put("Nu", "nu")
      .put("Xi", "xi")
      .put("Omi", "omicron")
      .put("Pi", "pi")
      .put("Rho", "rho")
      .put("Sig", "sigma")
      .put("Tau", "tau")
      .put("Ups", "upsilon")
      .put("Phi", "phi")
      .put("Chi", "chi")
      .put("Psi", "psi")
      .put("Ome", "omega")
      .get();

  public static String getByAbbr(String abbr){
    return abbrToLetter.get(abbr);
  }
}
