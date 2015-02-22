package net.osten.watermap.convert;

import static org.junit.Assert.*;

import java.util.Set;

import net.osten.watermap.model.WaterReport;

import org.junit.Before;
import org.junit.Test;

public class SanGorgonioReportTest
{
   private SanGorgonioReport converter = null;
   
   @Before
   public void setUp() throws Exception
   {
      converter = new SanGorgonioReport();
   }

   @Test
   public void testConvert() throws Exception
   {
      //converter.setFilePath("F:\\Unencrypted Folder\\dev\\GraybackWaterMap\\temp\\datafile.txt");
      converter.setFileURL(this.getClass().getClassLoader().getResource("datafile.txt"));
      Set<WaterReport> results = converter.convert();
      assertNotNull(results);
      System.out.println("got " + results.size() + " results");
      assertTrue(results.size() == 19);
      for (WaterReport wr : results) {
         System.out.println("wr=" + wr);
      }
   }
}
