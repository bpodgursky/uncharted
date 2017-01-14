package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.datasets.catalogs.GlieseCatalog;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGlieseCatalog {
  @Test
  public void testCatalog() throws Exception {
    GlieseCatalog catalog = new GlieseCatalog();
    StarRecord star = catalog.getStar("GJ 1131");

    System.out.println(star);
    assertEquals(
        new ParsedClassification().setMainClass(SpectralClass.M),
        star.getParsedStellarClassification()
    );

    //  Proxima Centauri
    StarRecord priximaCentari = catalog.getStar("Gl 551");
    assertEquals(priximaCentari.getSolDistance(), 4.22, .01);

  }
}
