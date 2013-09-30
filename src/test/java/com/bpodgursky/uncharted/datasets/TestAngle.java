package com.bpodgursky.uncharted.datasets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAngle {


  @Test
  public void testConvert() throws Exception {
    Angle angle = new Angle(16, 33, 29.6);
    assertEquals(0.28899549, angle.getRadians(), .00001);
  }
}
