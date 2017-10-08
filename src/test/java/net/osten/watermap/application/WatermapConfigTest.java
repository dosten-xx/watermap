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
package net.osten.watermap.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

/**
 * WatermapConfig test.
 */
public class WatermapConfigTest
{
   @Inject
   private WatermapConfig config = null;

   @Before
   public void setup()
   {
      config = new WatermapConfig();
   }

   @Test
   public void testGetInt()
   {
      assertTrue(config.getInt("nokey", 5) == 5);
   }

   @Test
   public void testGetInteger()
   {
      assertThat(config.getInteger("nokey", null), is(nullValue()));
      assertThat(config.getInteger("nokey", new Integer(5)), is(new Integer(5)));

   }

   @Test
   public void testGetString()
   {
      assertThat(config.getString("nokey"), is(nullValue()));
      assertThat(config.getString("java.version"), is(notNullValue()));
   }
}
