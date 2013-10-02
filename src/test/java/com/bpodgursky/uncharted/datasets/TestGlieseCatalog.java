package com.bpodgursky.uncharted.datasets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGlieseCatalog {
  @Test
  public void testCatalog() throws Exception {
    GlieseCatalog catalog = new GlieseCatalog();
    Star star = catalog.getStar("GJ 1131");

    assertEquals(
        new StellarClassification().setMainClass(SpectralClass.M),
        star.getStellarClassification()
    );

    //  Proxima Centauri
    Star priximaCentari = catalog.getStar("Gl 551");
    assertEquals(priximaCentari.getLightYearDistance(), 4.22, .01);

  }
//
//  @Test
//  public void testParseSpectral() throws Exception {
//    assertParse("k-m", "K5");
//    assertParse("", "");
//    assertParse("m", "M");
//    assertParse("K3.5", "K3.5");
//  }
//
//  private void assertParse(String spectClass, String pretty){
//    assertEquals(pretty, GlieseCatalog.parseClass(spectClass).getShortName());
//  }
}
