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
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import net.osten.watermap.model.WaterReport;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * Parser for San Gorgonio water report.
 */
@Singleton
@Startup
public class SanGorgonioReport
{
   private static Map<String, String> locationCoords = null;
   private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
   private static final String SOURCE_TITLE = "San Gorgonio Water Report";
   private static final String SOURCE_URL = "http://www.howlingduck.com/cgi-local/display_water_data.pl";
   private String filePath = null;
   private URL fileURL = null;
   private boolean ready = false;
   private Logger log = Logger.getLogger(this.getClass().getName());

   /**
    * Default constructor.
    */
   public SanGorgonioReport()
   {
      initialize();
   }

   /**
    * Converts the SanG datafile to water reports.
    *
    * @return set of water reports
    */
   public Set<WaterReport> convert()
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

      try {
         ImmutableList<String> lines = filePath != null ? Files.asCharSource(new File(filePath), Charsets.UTF_8).readLines() : Resources.asCharSource(fileURL, Charsets.UTF_8).readLines();
         log.fine("found " + lines.size() + " lines");

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
                  wr.setLon(new BigDecimal(coords.get(0)));
                  wr.setLat(new BigDecimal(coords.get(1)));
               }
               else {
                  log.fine("==> cannot find coords for " + wr.getName());
               }

               wr.setState(WaterStateParser.parseState(wr.getDescription()));

               boolean added = results.add(wr);
               if (!added) {
                  results.remove(wr);
                  results.add(wr);
               }
            }
            catch (java.text.ParseException e) {
               log.severe(e.getLocalizedMessage());
            }
         }
      }
      catch (IOException e) {
         log.severe(e.getLocalizedMessage());
      }

      return results;
   }

   /**
    * Loads the coordinates.
    */
   public void initialize()
   {
      log.info("initializing SanG report...");

      setFilePath(System.getenv("OPENSHIFT_DATA_DIR") + File.separator + "datafile.txt");

      locationCoords = new HashMap<String, String>();
      // Lodgepole Spring UTM 3775231N 516367E
      // estimated coords are from TH map
      locationCoords.put("High Creek Trailcamp", "-116.8470,34.0857,0");
      locationCoords.put("Halfway Trailcamp", "-116.8655,34.0843,0");
      locationCoords.put("Big Tree", "-116.801242,34.101698,0"); // estimated
      locationCoords.put("Fish Creek", "-116.7869,34.1234,0");
      locationCoords.put("Fish Creek Saddle", "-116.809153,34.117793,0"); // estimated
      locationCoords.put("Mineshaft Flat (.3 miles down trail)", "-116.823916,34.109732,0");
      locationCoords.put("Johns Meadow", "-116.921426,34.142788,0");
      locationCoords.put("Lodgepole Spring", "-116.822523,34.117793,0");
      locationCoords.put("South Fork Meadows", "-116.8435,34.1307,0");
      locationCoords.put("Trailfork", "-116.8644,34.1300,0");
      locationCoords.put("Jackstraw", "-116.9022,34.1411,0");
      locationCoords.put("Dobbs Trailcamp", "-116.8884,34.0945,0");
      locationCoords.put("Vivian Trailcamp", "-116.8850,34.0876,0");
      locationCoords.put("Dollar Lake", "-116.853084,34.121955,0");
      locationCoords.put("Limber Pine (.3 mi.west)", "-116.934475,34.127509,0");
      locationCoords.put("Saxton Trailcamp (.2 mi.west)", "-116.8821,34.1115,0");
      locationCoords.put("Columbine", "-116.940868,34.135575,0");
      locationCoords.put("Alger TrailCamp", "-116.9028,34.1035,0");
      locationCoords.put("High Meadow Springs", "-116.8646,34.1263,0");

      ready = true;
      log.info("done initializing SanG report");
   }

   /**
    * Always returns true since initialization is safe.
    *
    * @return ready state
    */
   public boolean isReady()
   {
      return ready;
   }

   /**
    * Full path to the data file.
    *
    * @param filePath file path
    */
   public void setFilePath(String filePath)
   {
      this.filePath = filePath;
   }

   /**
    * Sets the URL to load the file from.
    *
    * @param file URL
    * @deprecated use {@link #setFilePath(String)}
    */
   @Deprecated
   public void setFileURL(URL fileURL)
   {
      this.fileURL = fileURL;
   }
}
