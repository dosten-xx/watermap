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
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.lang3.time.FastDateFormat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.model.WaterState;

/**
 * San Mateo Wilderness report.
 */
@Startup
@Singleton
public class SanMateoWildernessReport
{
   // private static Map<String, String> locationCoords = null;
   private static final FastDateFormat dateFormatter = FastDateFormat.getInstance("M/dd/yy");
   private static final String SOURCE_TITLE = "San Mateo Wilderness Water Report";
   private static final String SOURCE_URL = "http://blog.osten.net";
   private String filePath = null;
   private URL fileURL = null;
   private Logger log = Logger.getLogger(this.getClass().getName());

   /**
    * Default constructor.
    */
   public SanMateoWildernessReport()
   {}

   /**
    * Returns water reports from text file.
    * Text file format: name, description, state, last report, reported by, lat, lon.
    *
    * @return set of water reports
    */
   public Set<WaterReport> convert()
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      int lineNumber = 1;

      try {
         ImmutableList<String> lines = filePath != null ? Files.asCharSource(new File(filePath), Charsets.UTF_8).readLines() : Resources.asCharSource(fileURL, Charsets.UTF_8).readLines();
         log.fine("found " + lines.size() + " lines");

         for (String line : lines) {

            // find start of data line section
            WaterReport wr = null;
            log.fine("parsing line[" + lineNumber + "]=" + line);
            wr = parseDataLine(line);

            if (wr != null && wr.getName() != null) {
               log.fine("adding wr=" + wr);
               boolean added = results.add(wr);
               if (!added) {
                  results.remove(wr);
                  results.add(wr);
               }
            }
            else {
               log.finer("did not add wr for line " + lineNumber);
            }

            lineNumber++;
         }
      }
      catch (IOException e) {
         log.severe(e.getLocalizedMessage());
      }

      return results;
   }

   private WaterReport parseDataLine(String line)
   {
      WaterReport result = new WaterReport();

      // Example line:
      // name, description, state, last report, reported by, lat, lon.
      // Pigeon Springs, trough had nasty water, LOW,2/27/16, darren@osten.net, 1, 2

      try {
         String[] fields = line.split(",");
         result.setName(fields[0]);
         result.setDescription(fields[1]);
         result.setState(mapWaterState(fields[2]));
         result.setLastReport(dateFormatter.parse(fields[3].trim()));
         result.setSource(fields[4]);
         result.setLat(new BigDecimal(fields[5].trim()));
         result.setLon(new BigDecimal(fields[6].trim()));
      }
      catch (ParseException e) {
         log.warning(e.getLocalizedMessage());
      }

      return result;
   }

   private WaterState mapWaterState(String state)
   {
      switch (state) {
      case "LOW":
         return WaterState.LOW;
      case "MEDIUM":
         return WaterState.MEDIUM;
      case "HIGH":
         return WaterState.HIGH;
      case "DRY":
         return WaterState.DRY;
      default:
         return WaterState.UNKNOWN;
      }
   }

   /**
    * Set the file path.
    */
   public void initialize()
   {
      log.info("initializing SanMateoWilderness report...");

      setFilePath(System.getenv("OPENSHIFT_DATA_DIR") + File.separator + "sanmateowilderness.txt");
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
