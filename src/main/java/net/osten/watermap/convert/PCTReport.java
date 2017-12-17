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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.pct.xml.GpxType;
import net.osten.watermap.pct.xml.WptType;
import net.osten.watermap.util.RegexUtils;

import org.apache.commons.lang3.time.FastDateFormat;

import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Parser for PCT water report based on the PCT Google Sheets downloaded using the API.
 */
@Singleton
@Startup
public class PCTReport
{
   private static final FastDateFormat dateFormatter = FastDateFormat.getInstance("M/dd/yy");
   private static final String SOURCE_TITLE = "PCT Water Report";
   private static final String SOURCE_URL = "http://pctwater.com/";
   private String dataDir = null;
   private String[] stateChars = new String[] { "CA", "OR", "WA" };
   private char[] sectionChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G' , 'M', 'N', 'O', 'P', 'Q', 'R'};
   private List<WptType> waypoints = new ArrayList<WptType>();
   private static Logger log = Logger.getLogger(PCTReport.class.getName());

   /**
    * Default constructor.
    */
   public PCTReport()
   {
      initialize();
   }

   /**
    * Parse the PCT water report Google docs files.
    *
    * @return set of water reports
    */
   public synchronized Set<WaterReport> convert() throws IOException
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      log.info("dataDir=" + dataDir);
      
      // parse json files
      if (dataDir != null) {
         for (String stateChar : stateChars) {
            for (char sectionChar : sectionChars) {
               try {
                  String fileName = "pct-" +stateChar + "-" + sectionChar + ".json";
                  File jsonFile = new File(dataDir + File.separator + fileName);
                  if (jsonFile.exists() && jsonFile.canRead()) {
                     log.info("reading json file " + jsonFile);
                     String htmlSource = Files.toString(jsonFile, Charset.forName("UTF-8"));
                     JsonParser parser = new JsonParser();
                     JsonElement root = parser.parse(htmlSource);
                     log.info("json root is obj=" + root.isJsonObject());
                     results.addAll(parseDocument(root.getAsJsonObject()));
                  }
               }
               catch (IOException e) {
                  log.severe(e.getLocalizedMessage());
               }
            }
         }
      }
      
      return results;
   }

   public void initialize()
   {
      log.info("initializing PCT report...");

      setDataDir(System.getenv("OPENSHIFT_DATA_DIR"));

      // parse waypoints XML files
      if (dataDir != null) {
         for (String stateChar : stateChars) {
            for (char sectionChar : sectionChars) {
               try {
                  JAXBContext jc = JAXBContext.newInstance("net.osten.watermap.pct.xml");
                  Unmarshaller u = jc.createUnmarshaller();
                  @SuppressWarnings("unchecked")
                  GpxType waypointList = ((JAXBElement<GpxType>) u.unmarshal(new FileInputStream(dataDir + File.separator + stateChar + "_Sec_" + sectionChar + "_waypoints.gpx"))).getValue();
                  log.info("found " + waypointList.getWpt().size() + " waypoints for section " + sectionChar);
                  waypoints.addAll(waypointList.getWpt());
               }
               catch (JAXBException | IOException e) {
                  log.severe(e.getLocalizedMessage());
               }
            }
         }
      }

      log.info("imported " + waypoints.size() + " waypoints");

      log.info("done initializing PCT report");
   }

   /**
    * Sets the directory where the data files are.
    *
    * @param dataDir data directory
    */
   public void setDataDir(String dataDir)
   {
      this.dataDir = dataDir;
   }

   /**
    * There are discrepencies between some of the water report names and the waypoint names.
    *
    * @param waterReportName name from the water report
    * @return mapped name in waypoint file
    */
   private String fixNames(String waterReportName)
   {
      String result = null;

      if (waterReportName.equalsIgnoreCase("LkMorenaCG")) {
         result = "LakeMorenaCG";
      }
      else if (waterReportName.equalsIgnoreCase("BoulderOaksCG")) {
         result = "BoulderOakCG";
      }
      else if (waterReportName.equalsIgnoreCase("WRCS091")) {
         result = "WR091";
      }
      else if (waterReportName.equalsIgnoreCase("WR016B")) {
         result = "WR106B";
      }
      else if (waterReportName.equalsIgnoreCase("CS183B")) {
         result = "CS0183";
      }
      else if (waterReportName.equalsIgnoreCase("Fuller Ridge")) {
         result = "FullerRidgeTH";
      }
      else if (waterReportName.equalsIgnoreCase("WRCS219")) {
         result = "WhitewaterTr";
      }
      else if (waterReportName.equalsIgnoreCase("WR233")) {
         result = "WR234";
      }
      else if (waterReportName.equalsIgnoreCase("WR239")) {
         result = "WRCS0239";
      }
      else if (waterReportName.equalsIgnoreCase("WR256")) {
         result = "WRCS056";
      }

      return result;
   }

   private Set<WaterReport> parseDocument(JsonObject reportJson)
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      log.info("json children=" + reportJson.getAsJsonArray("values").size());

      JsonArray currentRow = null;
      
      for (JsonElement row : reportJson.getAsJsonArray("values")) {
         if (row.isJsonArray()) {
            currentRow = row.getAsJsonArray();
            if (currentRow.size() >= 6) {
               String waypoint = currentRow.get(2).getAsJsonPrimitive().getAsString();
               String desc = currentRow.get(3).getAsJsonPrimitive().getAsString();
               String rpt = currentRow.get(4).getAsJsonPrimitive().getAsString();
               String date = currentRow.get(5).getAsJsonPrimitive().getAsString();

               String[] names = waypoint.split(",");
               for (String name : names) {
                  name = name.trim();
                  
                  WaterReport report = processWaypoint(name, desc, date, rpt);
                  if (report.getLat() == null) {
                     // DEO try prefixing the name (this is for split names: "WR127,B")
                     name = names[0] + name;
                     report = processWaypoint(name, desc, date, rpt);
                     if (report.getLat() == null) {
                        log.fine("cannot find coords for " + waypoint);
                     }
                     else {
                        log.info("Adding " + report.toString());
                        results.add(report);
                     }
                  }
                  else {
                     log.info("Adding " + report.toString());
                     results.add(report);
                  }
               }
            }
            else {
               log.info("skipping row " + row.toString());
            }
         }
         else {
            log.warning("row is not json array but " + row.getClass().getName());
         }
      }

      log.info("returning " + results.size() + " pct reports");
      return results;
   }
   
   private WaterReport processWaypoint(String waypoint, String desc, String date, String rpt)
   {
      log.finer("waypoint=" + waypoint);

      WaterReport report = new WaterReport();
      report.setDescription(rpt);
      report.setLocation(desc);
      report.setName(waypoint);
      
      if (date != null && !date.isEmpty()) {
         try {
            report.setLastReport(dateFormatter.parse(date));
         }
         catch (ParseException e) {
            log.severe(e.getLocalizedMessage());
         }
      }
      report.setSource(SOURCE_TITLE);
      report.setUrl(SOURCE_URL);

      report.setState(WaterStateParser.parseState(rpt));

      // TODO replace with something more elegant than this brute force search
      for (WptType wpt : waypoints) {
         if (wpt.getName().equalsIgnoreCase(waypoint)) {
            // System.out.println("found matching lat/lon");
            report.setLat(wpt.getLat());
            report.setLon(wpt.getLon());
            break;
         }
      }

      // if coords not found, try adding leading 0 (e.g. WRCS292 -> WRCS0292)
      if (report.getLat() == null) {
         String modifiedWaypoint = RegexUtils.addLeadingZerosToWaypoint(waypoint);
         for (WptType wpt : waypoints) {
            if (wpt.getName().equalsIgnoreCase(modifiedWaypoint)) {
               // System.out.println("found matching lat/lon");
               report.setLat(wpt.getLat());
               report.setLon(wpt.getLon());
               break;
            }
         }
      }
      
      // if coords not found, try with leading 0 in waypoint (i.e. WR0213); this happens in Section B waypoint file
      if (report.getLat() == null && waypoint.length() > 2) {
         String modifiedWaypoint = "WR0" + waypoint.substring(2);
         for (WptType wpt : waypoints) {
            if (wpt.getName().equalsIgnoreCase(modifiedWaypoint)) {
               // System.out.println("found matching lat/lon");
               report.setLat(wpt.getLat());
               report.setLon(wpt.getLon());
               break;
            }
         }
      }

      // if coords still not found, try with mapped name
      if (report.getLat() == null) {
         String modifiedName = fixNames(report.getName());
         for (WptType wpt : waypoints) {
            if (wpt.getName().equalsIgnoreCase(modifiedName)) {
               // System.out.println("found matching lat/lon");
               report.setLat(wpt.getLat());
               report.setLon(wpt.getLon());
               break;
            }
         }
      }

      return report;
   }
}
