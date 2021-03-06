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

import net.osten.watermap.pct.xml.WptType;

import com.google.common.base.Predicate;

import static com.google.common.base.Preconditions.*;

/**
 * Predicate that compares a WptType by its name.
 */
public class WptTypeNamePredicate implements Predicate<WptType>
{
   private WptType obj = null;

   /**
    * Constructor with WptType.
    * 
    * @param obj report to compare to
    */
   public WptTypeNamePredicate(WptType obj)
   {
      checkNotNull(obj);
      checkNotNull(obj.getName());
      this.obj = obj;
   }

   /**
    * Compare to another report.
    * 
    * @param input object to compare to
    * @return true if input has a name equals using String.equals(); false otherwise
    */
   public boolean apply(WptType input)
   {
      checkNotNull(input);
      return (obj.getName().equals(input.getName()));
   }
}
