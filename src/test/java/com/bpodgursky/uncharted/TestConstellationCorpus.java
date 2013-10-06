package com.bpodgursky.uncharted;

import com.bpodgursky.uncharted.datasets.ConstellationCorpus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestConstellationCorpus {
  @Test
  public void testCorpus() throws Exception {
    ConstellationCorpus constellationCorpus = new ConstellationCorpus();
    assertEquals("Canum Venaticorum", constellationCorpus.get("CVn").getGenitive());
  }
}
