package net.osten.watermap.convert;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.model.WaterState;

import org.junit.Before;
import org.junit.Test;

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
      assertThat(results.size(), is(337));
      
      assertTrue(results.contains(new WaterReport("WR001", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("WR004", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("WACS016", "PCT Water Report")));
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
            assertEquals(6, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(3 - 1, lr.get(Calendar.MONTH));
            assertEquals(2016, lr.get(Calendar.YEAR));
            assertEquals(WaterState.DRY, wr.getState());
            reportsTested++;
         }
         else if (wr.getName().equals("WR004")) {
            assertEquals(12, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(2, lr.get(Calendar.MONTH));
            assertEquals(2016, lr.get(Calendar.YEAR));
            assertEquals(WaterState.DRY, wr.getState());
            reportsTested++;
         }
         else if (wr.getName().equals("WACS016")) {
            //assertEquals(22, lr.get(Calendar.DAY_OF_MONTH));
            //assertEquals(3, lr.get(Calendar.MONTH));
            assertEquals(2016, lr.get(Calendar.YEAR));
            assertEquals(WaterState.HIGH, wr.getState());
            assertEquals(32.6725, wr.getLat().doubleValue(), 0.001);
            assertEquals(-116.5677, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
         else if (wr.getName().equals("LkMorenaCG")) {
            //assertEquals(22, lr.get(Calendar.DAY_OF_MONTH));
            //assertEquals(3, lr.get(Calendar.MONTH));
            assertEquals(2016, lr.get(Calendar.YEAR));
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
            assertEquals(30, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(7 - 1, lr.get(Calendar.MONTH));
            assertEquals(2015, lr.get(Calendar.YEAR));
            assertEquals(WaterState.UNKNOWN, wr.getState());
            assertEquals(42.2172, wr.getLat().doubleValue(), 0.001);
            assertEquals(-122.3668, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
         else if (wr.getName().equals("WACS2080")) {
            assertEquals(16, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(8, lr.get(Calendar.MONTH));
            assertEquals(2015, lr.get(Calendar.YEAR));
            //assertEquals(WaterState.HIGH, wr.getState());
            assertEquals(45.19733, wr.getLat().doubleValue(), 0.001);
            assertEquals(-121.75379, wr.getLon().doubleValue(), 0.001);
            reportsTested++;
         }
      }

      assertEquals(new Integer(9), new Integer(reportsTested));
   }
}
