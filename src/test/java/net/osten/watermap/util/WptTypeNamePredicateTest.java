package net.osten.watermap.util;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import net.osten.watermap.pct.xml.ObjectFactory;
import net.osten.watermap.pct.xml.WptType;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterators;

public class WptTypeNamePredicateTest
{
   private ObjectFactory of = new ObjectFactory();
   private WptType wr1 = of.createWptType();
   private WptType wr2 = of.createWptType();
   private WptType wr3 = of.createWptType();
   private WptType wr4 = of.createWptType();
   private WptType wr5 = of.createWptType();
   private WptType wr6 = of.createWptType();
   private Set<WptType> wrmap = new HashSet<WptType>();
   private WptTypeNamePredicate pred = null;
   
   @Before
   public void setUp() throws Exception
   {
      wr1.setName("test");
      wr2.setName("test");
      wr3.setName("test2");
      wr4.setName("test3");
      wr5.setName("test4");
      wr6.setName(null);
      
      pred = new WptTypeNamePredicate(wr1);
      
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
      assertFalse(pred.apply(new WptType()));
      assertFalse(pred.apply(of.createWptType()));
      assertTrue(pred.apply(wr1));
      assertTrue(pred.apply(wr2));
      assertFalse(pred.apply(wr3));
      assertFalse(pred.apply(wr4));
      assertFalse(pred.apply(wr5));
      assertFalse(pred.apply(wr6));
   }
   
   @Test
   public void testIterators()
   {
      Iterator<WptType> iterator = wrmap.iterator();
      assertNotNull(Iterators.find(iterator, pred));
      
      WptType newWr = of.createWptType();
      newWr.setName("some name");
      pred = new WptTypeNamePredicate(newWr);
      try {
         Iterators.find(iterator, pred);
         fail("Should get NoSuchElementException here");
      }
      catch (NoSuchElementException e) {
         // expected
      }
   }
}
