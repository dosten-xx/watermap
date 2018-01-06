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
package net.osten.watermap.convert;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.Set;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.model.WaterState;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for PCT water report.
 */
public class PCTReportTest
{
   private PCTReport converter = null;

   @Before
   public void setUp() throws Exception
   {
      converter = new PCTReport();
   }

   @Test
   public void testConvertDir() throws Exception
   {
      //converter.setDataDir(System.getProperty("OPENSHIFT_DATA_DIR"));
      Set<WaterReport> results = converter.convert();
      assertNotNull(results);
      System.out.println("got " + results.size() + " results");
      assertThat(results.size(), is(512));
      
      assertTrue(results.contains(new WaterReport("WR001", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("WR004", "PCT Water Report")));
      //assertTrue(results.contains(new WaterReport("WACS016", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("LkMorenaCG", "PCT Water Report")));
      
      int reportsTested = 0;

      for (WaterReport wr : results) {
         System.out.println("wr=" + wr);
         
         assertNotNull(wr.getLat());
         assertNotNull(wr.getLon());
         assertNotNull(wr.getName());
         assertNotNull(wr.getSource());
         
         // test individual reports
         

         GregorianCalendar lr = new GregorianCalendar();
         if (wr.getLastReport() != null) {
            lr.setTimeInMillis(wr.getLastReport().getTime());
         }
         
         if (wr.getName().equals("WR001")) {
            assertEquals(WaterState.DRY, wr.getState());
            reportsTested++;
         }
         else if (wr.getName().equals("WR004")) {
            assertEquals(WaterState.UNKNOWN, wr.getState());
            reportsTested++;
         }
         else if (wr.getName().equals("WACS016")) {
            //assertEquals(22, lr.get(Calendar.DAY_OF_MONTH));
            //assertEquals(3, lr.get(Calendar.MONTH));
            assertEquals(WaterState.DRY, wr.getState());
            assertEquals(32.6725, wr.getLat().doubleValue(), 0.001);
            assertEquals(-116.5677, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
         else if (wr.getName().equals("LkMorenaCG")) {
            assertEquals(WaterState.UNKNOWN, wr.getState());
            assertEquals(32.6825, wr.getLat().doubleValue(), 0.001);
            assertEquals(-116.5179, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
         else if (wr.getName().equals("WRCS091")) {
            reportsTested++;
         }
         else if (wr.getName().equals("WR127")) {
            reportsTested++;
         }
         else if (wr.getName().equals("WR127B")) {
            reportsTested++;
         }
         else if (wr.getName().equals("WA1749B")) {
            assertEquals(WaterState.HIGH, wr.getState());
            assertEquals(42.2172, wr.getLat().doubleValue(), 0.001);
            assertEquals(-122.3668, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
         else if (wr.getName().equals("WACS2080")) {
            //assertEquals(WaterState.HIGH, wr.getState());
            assertEquals(45.19733, wr.getLat().doubleValue(), 0.001);
            assertEquals(-121.75379, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
      }

      assertEquals(new Integer(8), new Integer(reportsTested));
   }
}
