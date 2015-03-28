package net.osten.watermap.convert;

import static org.junit.Assert.*;

import java.io.File;
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
      converter.setDataDir(System.getProperty("user.dir") + File.separator + "src/test/resources");
      Set<WaterReport> results = converter.convert();
      assertNotNull(results);
      System.out.println("got " + results.size() + " results");
      assertTrue(results.size() == 125);
      
      assertTrue(results.contains(new WaterReport("WR001", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("WR004", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("WACS016", "PCT Water Report")));
      assertTrue(results.contains(new WaterReport("LkMorenaCG", "PCT Water Report")));
      
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
            assertEquals(1, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(3, lr.get(Calendar.MONTH) + 1);
            assertEquals(2015, lr.get(Calendar.YEAR));
            assertEquals(WaterState.HIGH, wr.getState());
         }
         else if (wr.getName().equals("WR004")) {
            assertEquals(7, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(3, lr.get(Calendar.MONTH) + 1);
            assertEquals(2015, lr.get(Calendar.YEAR));
            assertEquals(WaterState.DRY, wr.getState());
         }
         else if (wr.getName().equals("WACS016")) {
            assertEquals(22, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(3, lr.get(Calendar.MONTH) + 1);
            assertEquals(2015, lr.get(Calendar.YEAR));
            assertEquals(WaterState.MEDIUM, wr.getState());
            assertEquals(32.6725, wr.getLat().doubleValue(), 0.001);
            assertEquals(-116.5677, wr.getLon().doubleValue(), 0.001);
         }
         else if (wr.getName().equals("LkMorenaCG")) {
            assertEquals(22, lr.get(Calendar.DAY_OF_MONTH));
            assertEquals(3, lr.get(Calendar.MONTH) + 1);
            assertEquals(2015, lr.get(Calendar.YEAR));
            assertEquals(WaterState.HIGH, wr.getState());
            assertEquals(32.6843, wr.getLat().doubleValue(), 0.001);
            assertEquals(-116.5179, wr.getLon().doubleValue(), 0.001);
         }
      }
   }
}
