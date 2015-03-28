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
import java.text.SimpleDateFormat;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.io.Files;

/**
 * Parser for PCT water report.
 */
@Singleton
@Startup
public class PCTReport
{
   private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("M/d/yy");
   private static final String SOURCE_TITLE = "PCT Water Report";
   private static final String SOURCE_URL = "http://pctwater.com/";
   private String dataDir = null;
   private char[] sectionChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
   private List<WptType> waypoints = new ArrayList<WptType>();
   private Logger log = Logger.getLogger(this.getClass().getName());

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

      // parse report files
      log.fine("dataDir=" + dataDir);
      // look for multiple report files - usually 3: a, c, e
      for (char sectionChar : sectionChars) {
         String fileName = "pct-" + sectionChar + ".htm";
         log.fine("fileName=" + fileName);
         File htmlFile = new File(dataDir + File.separator + fileName);
         if (htmlFile.exists() && htmlFile.canRead()) {
            log.fine("reading html file " + htmlFile);
            String htmlSource = Files.toString(htmlFile, Charset.forName("UTF-8"));
            Document reportDoc = Jsoup.parse(htmlSource);
            results.addAll(parseDocument(reportDoc));
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
         for (char sectionChar : sectionChars) {
            try {
               JAXBContext jc = JAXBContext.newInstance("net.osten.watermap.pct.xml");
               Unmarshaller u = jc.createUnmarshaller();
               @SuppressWarnings("unchecked")
               GpxType waypointList = ((JAXBElement<GpxType>) u.unmarshal(new FileInputStream(dataDir + File.separator + "CA_Sec_" + sectionChar + "_waypoints.gpx"))).getValue();
               log.fine("found " + waypointList.getWpt().size() + " waypoints for section " + sectionChar);
               waypoints.addAll(waypointList.getWpt());
            }
            catch (JAXBException | IOException e) {
               log.severe(e.getLocalizedMessage());
            }
         }
      }

      log.info("imported " + waypoints.size() + " waypoints");

      log.info("done initializing PCT report");
   }

   private Set<WaterReport> parseDocument(Document reportDoc)
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      log.finer("reportDoc children=" + reportDoc.children().size());

      Element mainTable = reportDoc.getElementById("tblMain");
      // System.out.println("mainTable=" + mainTable);
      Elements rows = mainTable.getElementsByTag("tr");
      log.finer("found " + rows.size() + " rows");

      for (Element row : rows) {
         Elements cells = row.getElementsByTag("td");
         // - for each waypoint with an entry (e.g. 'WR001')
         // - report column is description
         // - date is report date
         // - look up waypoint in XML file to get coordinates
         String waypoint = cells.get(3).text();
         if (waypoint != null && !waypoint.isEmpty() && waypoint.startsWith("WR")) {

            // TODO handle names with commas (e.g. WR127, B)

            log.finer("waypoint=" + waypoint);

            String desc = cells.get(4).text();

            String date = cells.get(6).text();

            String rpt = cells.get(5).text();

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

            // TODO replace with something more elegant
            for (WptType wpt : waypoints) {
               if (wpt.getName().equals(waypoint)) {
                  // System.out.println("found matching lat/lon");
                  report.setLat(wpt.getLat());
                  report.setLon(wpt.getLon());
                  break;
               }
            }

            if (report.getLat() == null) {
               // try with leading 0 in waypoint (i.e. WR0213); this happens in Section B waypoint file
               String modifiedWaypoint = "WR0" + waypoint.substring(2);
               for (WptType wpt : waypoints) {
                  if (wpt.getName().equals(modifiedWaypoint)) {
                     // System.out.println("found matching lat/lon");
                     report.setLat(wpt.getLat());
                     report.setLon(wpt.getLon());
                     break;
                  }
               }
            }

            if (report.getLat() == null) {
               log.fine("cannot find coords for " + waypoint);
            }

            log.finest(report.toString());
            results.add(report);
         }
      }

      log.fine("returning " + results.size() + " pct reports");
      return results;
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
}
