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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex utilities.
 */
public final class RegexUtils
{
   /**
    * Adds leading zeros to a waypoint number.
    *
    * @param waypoint waypoint, e.g. WRC292
    * @return waypoint with leading zeros, e.g. WRC0292
    */
   public static String addLeadingZerosToWaypoint(String waypoint)
   {
      String result = null;
      Pattern p = Pattern.compile("([A-Z]+)(\\d+)");
      Matcher m = p.matcher(waypoint);
      boolean b = m.matches();
      if (b) {
         // System.out.println("group0=" + m.group(0));
         // System.out.println("group1=" + m.group(1));
         // System.out.println("group2=" + m.group(2));
         result = m.group(1) + "0" + m.group(2);
      }

      return result;
   }

   /**
    * Match first occurrence of a pattern.
    *
    * @param input input to match against
    * @param pattern pattern
    * @return match; null if none found
    */
   public static MatchResult matchFirstOccurance(String input, Pattern pattern)
   {
      MatchResult result = null;

      Matcher m = pattern.matcher(input);
      boolean found = m.find(0);
      if (found) {
         result = m.toMatchResult();
         // System.out.println("result=" + result);
         // System.out.println("start=" + m.start() + " end=" + m.end());
      }

      return result;
   }

   /**
    * Match first occurrence of a pattern.
    *
    * @param input input to match against
    * @param pattern pattern
    * @param start starting index
    * @return match; null if none found
    */
   public static MatchResult matchFirstOccurance(String input, Pattern pattern, int start)
   {
      MatchResult result = null;

      Matcher m = pattern.matcher(input);
      boolean found = m.find(start);
      if (found) {
         result = m.toMatchResult();
         // System.out.println("result=" + result);
         // System.out.println("start=" + m.start() + " end=" + m.end());
      }

      return result;
   }

   /**
    * Match last occurrence of a pattern.
    *
    * @param input input to match against
    * @param pattern pattern
    * @param start index to start search at
    * @return match; null if none found
    */
   public static MatchResult matchLastOccurance(String input, Pattern pattern, int start)
   {
      MatchResult result = null;

      Matcher m = pattern.matcher(input);
      while (start < input.length()) {
         boolean found = m.find(start);
         if (found) {
            result = m.toMatchResult();
            // System.out.println("result=" + result);
            // System.out.println("start=" + m.start() + " end=" + m.end());
            start = m.end();
         }
         else {
            break;
         }
      }
      return result;
   }

   /**
    * Match last occurrence of a pattern.
    *
    * @param input input to match against
    * @param pattern pattern
    * @return match; null if none found
    */
   public static MatchResult matchLastOccurence(String input, Pattern pattern)
   {
      MatchResult result = null;

      Matcher m = pattern.matcher(input);
      int start = 0;
      while (start < input.length()) {
         boolean found = m.find(start);
         if (found) {
            result = m.toMatchResult();
            // System.out.println("result=" + result);
            // System.out.println("start=" + m.start() + " end=" + m.end());
            start = m.end();
         }
         else {
            break;
         }
      }
      return result;
   }

   /**
    * Match all occurrences of a pattern.
    *
    * @param input input to match against
    * @param pattern pattern
    * @return list of matches, won't be null but can be empty
    */
   public static List<MatchResult> matchOccurences(String input, Pattern pattern)
   {
      List<MatchResult> result = new ArrayList<MatchResult>();

      Matcher m = pattern.matcher(input);
      int start = 0;
      while (start < input.length()) {
         boolean found = m.find(start);
         if (found) {
            result.add(m.toMatchResult());
            // System.out.println("result=" + result);
            // System.out.println("start=" + m.start() + " end=" + m.end());
            start = m.end();
         }
         else {
            break;
         }
      }
      return result;
   }

   private RegexUtils()
   {}
}
