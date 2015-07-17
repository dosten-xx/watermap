/**
 * 
 */
package net.osten.watermap.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex utilities. 
 */
public final class RegexUtils
{
   private RegexUtils() {}
   
   public static String addLeadingZerosToWaypoint(String waypoint)
   {
      String result = null;
      Pattern p = Pattern.compile("([A-Z]+)(\\d+)");
      Matcher m = p.matcher(waypoint);
      boolean b = m.matches();
      if (b) {
         //System.out.println("group0=" + m.group(0));
         //System.out.println("group1=" + m.group(1));
         //System.out.println("group2=" + m.group(2));
         result = m.group(1) + "0" + m.group(2);
      }
      
      return result;
   }
}
