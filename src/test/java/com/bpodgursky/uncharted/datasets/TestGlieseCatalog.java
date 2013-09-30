package com.bpodgursky.uncharted.datasets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGlieseCatalog {
  @Test
  public void testCatalog() throws Exception {
    GlieseCatalog catalog = new GlieseCatalog();
    assertEquals("STAR LUYTEN COLOR CLASS M", catalog.getStar("GJ 1131").getStarClass());

    //  Proxima Centauri
    Star priximaCentari = catalog.getStar("Gl 551");
    assertEquals(priximaCentari.getLightYearDistance(), 4.22, .01);

  }
}
