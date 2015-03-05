/**
 * 
 */
package net.osten.watermap.convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.model.WaterState;

/**
 * Parser for San Gorgonio water report.
 */
public class SanGorgonioReport
{
   private static Map<String, String> locationCoords = null;
   private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
   private static final String SOURCE_TITLE = "San Gorgonio Water Report";
   private static final String SOURCE_URL = "http://www.howlingduck.com/cgi-local/display_water_data.pl";

   static {
      locationCoords = new HashMap<String, String>();
      // Lodgepole Spring UTM 3775231N 516367E
      // estimated coords are from TH map
      locationCoords.put("High Creek Trailcamp", "-116.857248,34.076686,0");
      locationCoords.put("Halfway Trailcamp", "0,0,0");
      locationCoords.put("Big Tree", "-116.801242,34.101698,0"); // estimated
      locationCoords.put("Fish Creek", "0,0,0");
      locationCoords.put("Fish Creek Saddle", "-116.809153,34.117793,0"); // estimated
      locationCoords.put("Mineshaft Flat (.3 miles down trail)", "-116.823916,34.109732,0");
      locationCoords.put("Johns Meadow", "-116.921426,34.142788,0");
      locationCoords.put("Lodgepole Spring", "-116.822523,34.117793,0");
      locationCoords.put("South Fork Meadows", "0,0,0");
      locationCoords.put("Trailfork", "0,0,0");
      locationCoords.put("Jackstraw", "0,0,0");
      locationCoords.put("Dobbs Trailcamp", "0,0,0");
      locationCoords.put("Vivian Trailcamp", "0,0,0");
      locationCoords.put("Halfway Trailcamp", "0,0,0");
      locationCoords.put("Dollar Lake", "-116.853084,34.121955,0");
      locationCoords.put("Limber Pine (.3 mi.west)", "-116.934475,34.127509,0");
      locationCoords.put("Saxton Trailcamp (.2 mi.west)", "0,0,0");
      locationCoords.put("Columbine", "-116.940868,34.135575,0");
      locationCoords.put("Alger TrailCamp", "0,0,0");
      locationCoords.put("High Meadow Springs", "0,0,0");
   }

   private String filePath = null;
   private URL fileURL = null;

   public URL getFileURL()
   {
      return fileURL;
   }

   public void setFileURL(URL fileURL)
   {
      this.fileURL = fileURL;
   }

   public String getFilePath()
   {
      return filePath;
   }

   public void setFilePath(String filePath)
   {
      this.filePath = filePath;
   }

   public SanGorgonioReport()
   {}

   public Set<WaterReport> convert() throws IOException
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      /*
      Multiset<String> liness = HashMultiset.create(
         Splitter.on('\t')
         .trimResults()
         .omitEmptyStrings()
         .split(
            (filePath != null ? 
               Files.asCharSource(new File(filePath), Charsets.UTF_8).read()
               : Resources.asCharSource(fileURL, Charsets.UTF_8).read())));
      System.out.println("found " + liness.size() + " lines");
      */
      
      ImmutableList<String> lines =
               (filePath != null ? 
                        Files.asCharSource(new File(filePath), Charsets.UTF_8).readLines()
                        : Resources.asCharSource(fileURL, Charsets.UTF_8).readLines());
      System.out.println("found " + lines.size() + " lines");

      for (String line : lines) {
         List<String> fields = Splitter.on('\t').trimResults().splitToList(line);

         /* Layout of datafile.txt -
          * String postDate = nextLine[0];
          * String location = nextLine[1];
          * String comment = nextLine[2];
          * String logDate = nextLine[3];
          * String user = nextLine[4];
          */

         WaterReport wr = new WaterReport();
         try {
            wr.setLastReport(dateFormatter.parse(fields.get(3)));
            wr.setLocation("San Gorgonio");
            wr.setDescription(fields.get(2));
            wr.setName(fields.get(1));
            wr.setSource(SOURCE_TITLE);
            wr.setUrl(SOURCE_URL);

            if (locationCoords.containsKey(wr.getName())) {
               List<String> coords = Splitter.on(',').splitToList(locationCoords.get(wr.getName()));
               wr.setLat(new BigDecimal(coords.get(0)));
               wr.setLon(new BigDecimal(coords.get(1)));
            }
            else {
               System.out.println("==> cannot find coords for " + wr.getName());
            }

            // parse the condition
            String lcDesc = wr.getDescription().toLowerCase();
            if (lcDesc.contains("good")
                     || lcDesc.contains("excellent")
                     || lcDesc.contains("plenty")
                     || lcDesc.contains("nicely")
                     || lcDesc.contains("well")) {
               wr.setState(WaterState.HIGH);
            }
            else if (lcDesc.contains("sufficient")
                     || lcDesc.contains("decent")) {
               wr.setState(WaterState.MEDIUM);
            }
            else if (lcDesc.contains("barely")) {
               wr.setState(WaterState.LOW);
            }
            else if (lcDesc.contains("no water")
                     || lcDesc.contains("dry")) {
               wr.setState(WaterState.DRY);
            }

            boolean added = results.add(wr);
            if (!added) {
               results.remove(wr);
               results.add(wr);
            }
         }
         catch (java.text.ParseException e) {
            // log.error(e);
            e.printStackTrace();
         }
      }

      return results;
   }
}
