package net.osten.watermap.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexUtilsTest
{
   @Test
   public void testAddLeadingZerosToWaypoint()
   {
      String result = RegexUtils.addLeadingZerosToWaypoint("WRC292");
      assertEquals("WRC0292", result);
      result = RegexUtils.addLeadingZerosToWaypoint("WRC0292");
      assertEquals("WRC00292", result);
      result = RegexUtils.addLeadingZerosToWaypoint("WRC");
      assertNull(result);
   }
}
