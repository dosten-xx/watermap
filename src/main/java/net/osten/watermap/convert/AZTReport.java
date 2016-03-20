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
import java.net.URL;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.FastDateFormat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.util.RegexUtils;

/**
 * Arizona Trail report.
 */
public class AZTReport
{
   // private static Map<String, String> locationCoords = null;
   private static final FastDateFormat dateFormatter = FastDateFormat.getInstance("M/d/yy");
   private static final String SOURCE_TITLE = "Arizona Trail Water Report";
   private static final String SOURCE_URL = "http://www.fredgaudetphotography.com/aztrail1.html";
   private String filePath = null;
   private URL fileURL = null;
   private Logger log = Logger.getLogger(this.getClass().getName());
   private Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{1,4}");
   private Pattern dataLinePattern = Pattern.compile("^\\d{1,3}\\.\\d{1,2} \\d{1,3}\\.\\d{1,2} ");
   private Pattern decimalPattern = Pattern.compile("(\\d{1,3}\\.\\d{1,2} ){2}");
   private Pattern typesPattern = Pattern.compile("spring|creek|windmill|store|(spring fed)|dirt tank|intermittent pools|pipe|well|Town|water trailer|Reavis Creek|cement dam|spigot|cement tank|faucet in corral|tank|large cow pond|faucet|Chimenea Ck|tank|large stock pond|big storage pond|reservoir|big pools|town|faucet/troughs|resupply box|large tank|dirt stock tank|stock trough|tank with float|river|Gila River|seep-stock pond|windmills/ponds|stock pond|two metal tanks|ForSer Info Cntr|small flows/pools|seep/pools|trough|lake|Flagstaff|pool|restaurant|water when staffed|Bright Angel Crk|water fountain|two blue 5 gallon|concrete drinker|lodge|2 dirt tanks|campers/day|pond");
   private Pattern histRelPattern1 = Pattern.compile("\\d");
   private Pattern histRelPattern2 = Pattern.compile("\\d\\s*to\\s*\\d");
   private Pattern histRelPattern3 = Pattern.compile("\\d\\s*\\-\\s*\\d");
   private Pattern headerEndPattern = Pattern.compile("^Miles Miles Reliabiity$");
   private Pattern passsageLinePattern = Pattern.compile("^\\d+\\.\\d+ Passage \\d");

   /**
    * Default constructor.
    */
   public AZTReport()
   {}

   /**
    * Converts the AT PDF datafile to water reports.
    *
    * @return set of water reports
    */
   public Set<WaterReport> convert()
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      boolean inData = false;
      int lineNumber = 1;

      try {
         ImmutableList<String> lines = filePath != null ? Files.asCharSource(new File(filePath), Charsets.UTF_8).readLines() : Resources.asCharSource(fileURL, Charsets.UTF_8).readLines();
         log.fine("found " + lines.size() + " lines");

         for (String line : lines) {

            // find start of data line section
            if (RegexUtils.matchFirstOccurance(line, dataLinePattern) != null) {
               WaterReport wr = null;
               log.fine("parsing line[" + lineNumber + "]=" + line);
               wr = parseDataLine(line);
               //log.fine("wr=" + wr);

               /*
                * if (locationCoords.containsKey(wr.getName())) { List<String> coords =
                * Splitter.on(',').splitToList(locationCoords.get(wr.getName())); wr.setLon(new
                * BigDecimal(coords.get(0))); wr.setLat(new BigDecimal(coords.get(1))); } else { log.fine(
                * "==> cannot find coords for " + wr.getName()); }
                */

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
/*                  if (RegexUtils.matchFirstOccurance(line, passsageLinePattern) != null) {
                     // some data sections have a passage section header
                     log.fine("found passage section header at line " + lineNumber);
                  }
                  else {
                     // end of data section
                     // TODO how to detect the end of a section?
                     //inData = false;
                     log.fine("end of data at line " + lineNumber);
                  }*/
               }
            }
            else {
               log.finest("skipping line " + lineNumber);
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

      try {
         // Example line:
         // 8.3 8.3 Tub Spring (aka Bathtub Spring) spring 3 full tub; good trickle3/28/15 3/28/15 Bird Food 4/5/15

         // Mileages = first two decimals
         MatchResult decimalsMatch = RegexUtils.matchFirstOccurance(line, decimalPattern);
         if (decimalsMatch == null) {
            log.fine("Mileages not found");
            return null;
         }
         int decimalsEnd = decimalsMatch.end();

         // Type = spring | creek | spring fed | windmill | store | dirt tank | pipe | Town | etc..
         MatchResult typeMatch = RegexUtils.matchFirstOccurance(line, typesPattern);
         if (typeMatch == null) {
            log.fine("Type not found");
            return null;
         }
         log.finer("type=" + typeMatch.group());
         int typeEnd = typeMatch.end();

         // Name = text from second decimal number to type (spring,creek,etc.)
         log.finer("decimalsEnd=" + decimalsEnd + " typeEnd=" + typeEnd);
         String name = line.substring(decimalsEnd, typeEnd);
         result.setName(name.trim());

         // Historic Reliability = int after Type (can be "1 to 2" or "0-2")
         MatchResult histRelMatch = RegexUtils.matchFirstOccurance(line, histRelPattern3, typeEnd);
         if (histRelMatch == null) {
            histRelMatch = RegexUtils.matchFirstOccurance(line, histRelPattern2, typeEnd);
            if (histRelMatch == null) {
               histRelMatch = RegexUtils.matchFirstOccurance(line, histRelPattern1, typeEnd);
               if (histRelMatch == null) {
                  log.fine("Historical Reliability not found");
                  return null;
               }
            }
         }
         log.finer("histRel=" + histRelMatch.group());
         String historicReliability = mapHistoricReliability(histRelMatch.group().trim());
         int histRelEnd = histRelMatch.end();

         // Report Date = second date from right
         int reportDateEnd = -1;
         int reportDateStart = -1;
         List<MatchResult> dates = RegexUtils.matchOccurences(line, datePattern);
         if (dates.size() >= 2) {
            reportDateEnd = dates.get(dates.size() - 2).end();
            reportDateStart = dates.get(dates.size() - 2).start();
         }
         else {
            log.fine("Only found " + dates.size() + " dates");
            reportDateStart = Math.max(line.length() - 1, histRelEnd);
         }

         // Report = Historic Reliability to Report Date
         log.finer("histRelEnd=" + histRelEnd + " reportDateStart=" + reportDateStart);
         if (histRelEnd >= 0 && reportDateStart >= 0 && reportDateStart >= histRelEnd) {
            String report = line.substring(histRelEnd, reportDateStart);
            result.setDescription(report.trim() + "<br />Historical Reliability:" + historicReliability);
         }
         else {
            log.fine("cannot find historic reliability");
         }

         // Post Date = first date from right
         int postDateStart = -1;
         MatchResult postDate = RegexUtils.matchLastOccurence(line, datePattern);
         if (postDate == null) {
            log.fine("Post Date not found");
         }
         else {
            result.setLastReport(dateFormatter.parse(postDate.group()));
            postDateStart = postDate.start();
            log.finer("postDate=" + postDate.group());
         }

         // Reported By = text between Report Date and Post Date
         if (postDateStart >= 0 && reportDateEnd >= 0 && postDateStart > reportDateEnd) {
            String reportedBy = line.substring(reportDateEnd, postDateStart);
            log.finer("reportedBy=" + reportedBy);
         }
         else {
            log.finer("cannot find reportedBy");
         }

         result.setState(WaterStateParser.parseState(result.getDescription()));
         result.setSource(SOURCE_TITLE);
         result.setUrl(SOURCE_URL);
      }
      catch (

      ParseException e)

      {
         log.fine("ParseException:" + e.getLocalizedMessage());
      }

      return result;

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

   private String mapHistoricReliability(String input)
   {
      String result = "Unknown";
      input = input.trim();

      if (input.equals("0")) {
         result = "not reliable";
      }
      else if (input.equals("0 to 1")) {
         result = "not reliable/seasonal";
      }
      else if (input.equals("1")) {
         result = "seasonal or iffy";
      }
      else if (input.equals("1 to 2")) {
         result = "seasonal/probable";
      }
      else if (input.equals("2")) {
         result = "probable";
      }
      else if (input.equals("2 to 3")) {
         result = "probable/fairly reliable";
      }
      else if (input.equals("3")) {
         result = "fairly reliable";
      }
      else if (input.equals("3 to 4")) {
         result = "fairly reliable/definite source";
      }
      else if (input.equals("4")) {
         result = "definite source";
      }

      return result;
   }
}
