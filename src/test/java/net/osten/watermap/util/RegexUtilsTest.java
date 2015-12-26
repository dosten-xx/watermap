/*
The MIT License (MIT)
Copyright (c) 2015 Darren Osten
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package net.osten.watermap.util;

import static org.junit.Assert.*;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexUtilsTest
{
   private static final String DATALINE = "8.3 8.3 Tub Spring (aka Bathtub Spring) spring 3 full tub; good trickle3/28/15 3/28/15 Bird Food 4/5/15";

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

   @Test
   public void testMatchLastOccurance()
   {
      Pattern p = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{1,4}");
      MatchResult result = RegexUtils.matchLastOccurence(DATALINE, p);
      assertEquals("4/5/15", result.group());

      result = RegexUtils.matchLastOccurance(DATALINE, p, 10);
      assertEquals("4/5/15", result.group());

      result = RegexUtils.matchLastOccurance("ofijweoifjwaeofjwe", p, 10);
      assertNull(result);
   }

   @Test
   public void testMatchFirstOccurance()
   {
      Pattern p = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{1,4}");
      MatchResult result = RegexUtils.matchFirstOccurance(DATALINE, p);
      assertEquals("3/28/15", result.group());

      result = RegexUtils.matchFirstOccurance(DATALINE, p, result.end());
      assertEquals("3/28/15", result.group());

      result = RegexUtils.matchFirstOccurance(DATALINE, p, result.end());
      assertEquals("4/5/15", result.group());

      result = RegexUtils.matchFirstOccurance("wefpijwefjiefoijwe", p);
      assertNull(result);

      result = RegexUtils.matchFirstOccurance("wefpijwefjiefoijwe", p, 5);
      assertNull(result);
   }
}
