package net.osten.watermap.util;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import net.osten.watermap.model.WaterReport;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterators;

public class WaterReportNamePredicateTest
{
   private WaterReport wr1 = new WaterReport("test", "some source");
   private WaterReport wr2 = new WaterReport("test", "some source");
   private WaterReport wr3 = new WaterReport("test2", "some source");
   private WaterReport wr4 = new WaterReport("test", "some source2");
   private WaterReport wr5 = new WaterReport("test", "some source");
   private WaterReport wr6 = new WaterReport(null, "some source");
   private Set<WaterReport> wrmap = new HashSet<WaterReport>();
   private WaterReportNamePredicate pred = null;
   
   @Before
   public void setUp() throws Exception
   {
      pred = new WaterReportNamePredicate(wr1);
      
      wrmap.add(wr1);
      wrmap.add(wr2);
      wrmap.add(wr3);
      wrmap.add(wr4);
      wrmap.add(wr5);
      wrmap.add(wr6);
   }

   @Test
   public void testApply()
   {
      try {
         assertFalse(pred.apply(null));
         fail("Should be NPE due to null argument");
      }
      catch (NullPointerException e) {
         // expected
      }
      assertFalse(pred.apply(new WaterReport()));
      assertTrue(pred.apply(wr1));
      assertTrue(pred.apply(wr2));
      assertFalse(pred.apply(wr3));
      assertTrue(pred.apply(wr4));
      assertTrue(pred.apply(wr5));
      assertFalse(pred.apply(wr6));
   }
   
   @Test
   public void testIterators()
   {
      Iterator<WaterReport> iterator = wrmap.iterator();
      assertNotNull(Iterators.find(iterator, pred));
      
      pred = new WaterReportNamePredicate(new WaterReport("test4", null));
      try {
         Iterators.find(iterator, pred);
         fail("Should get NoSuchElementException here");
      }
      catch (NoSuchElementException e) {
         // expected
      }
   }
}
