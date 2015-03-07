package net.osten.watermap.convert;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Set;

import net.osten.watermap.model.WaterReport;

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
      assertTrue(results.size() == 51);
      for (WaterReport wr : results) {
         System.out.println("wr=" + wr);
      }
   }
}
