package ru.stqa.mfp.sandbox;

import org.junit.Test;
import org.testng.Assert;

public class PointTests {
  @Test
  public void testDistance() {
    Point p1 = new Point(3, -6);
    Point p2 = new Point(-4, 5);
    Assert.assertEquals(p1.distance(p2),13.038,0.001 );
  }
  @Test
  public void testDistance2() {
    Point p1 = new Point(5, -6);
    Point p2 = new Point(-4, 5);
    Assert.assertEquals(p1.distance(p2),14.212, 0.001 );
  }
}
