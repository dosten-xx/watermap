/**
 *
 */
package net.osten.watermap.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.pct.xml.GpxType;
import net.osten.watermap.pct.xml.WptType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * Parser for PCT water report.
 */
public class PCTReport
{
   private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("M/d/yy");
   private static final String SOURCE_TITLE = "PCT Water Report";
   private static final String SOURCE_URL = "http://pctwater.com/";
   private URL fileUrl = null;
   private String dataDir = null;
   private char[] sectionChars = new char[] {'a','b','c','d','e','f','g'};
   private List<WptType> waypoints = new ArrayList<WptType>();

   public PCTReport()
   {}

   public Set<WaterReport> convert() throws IOException
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      // parse waypoints XML file
      if (dataDir != null) {
         for (char sectionChar : sectionChars) {
            try {
               JAXBContext jc = JAXBContext.newInstance("net.osten.watermap.pct.xml");
               Unmarshaller u = jc.createUnmarshaller();
               @SuppressWarnings("unchecked")
               GpxType waypointList = (GpxType) ((JAXBElement<GpxType>) u.unmarshal(new FileInputStream(dataDir + File.separator + "ca_sec_" + sectionChar + "_waypoints.gpx"))).getValue();
               System.out.println("found " + waypointList.getWpt().size() + " waypoints for section " + sectionChar);
               waypoints.addAll(waypointList.getWpt());
            }
            catch (Exception e) {
               // TODO handle exception
               e.printStackTrace();
            }
         }
      }
      
      // parse report files
      if (dataDir == null && fileUrl != null) {
         // parse single file 
         String htmlSource = Resources.asCharSource(fileUrl, Charset.forName("UTF-8")).read();
         Document reportDoc = Jsoup.parse(htmlSource);
         results.addAll(parseDocument(reportDoc));
      }
      else if (dataDir != null) {
         System.out.println("dataDir=" + dataDir);
         // look for multiple report files - usually 3: a, c, e
         for (char sectionChar : sectionChars) {
            String fileName = "pct-" + sectionChar + ".htm";
            System.out.println("fileName=" + fileName);
            File htmlFile = new File(dataDir + File.separator + fileName);
            if (htmlFile.exists() && htmlFile.canRead()) {
               System.out.println("reading html file " + htmlFile);
               String htmlSource = Files.toString(htmlFile, Charset.forName("UTF-8"));
               Document reportDoc = Jsoup.parse(htmlSource);
               results.addAll(parseDocument(reportDoc));
            }
         }
      }

      return results;
   }

   public void setDataDir(String dataDir)
   {
      this.dataDir = dataDir;
   }

   public void setFileUrl(URL fileUrl)
   {
      this.fileUrl = fileUrl;
   }
   
   private Set<WaterReport> parseDocument(Document reportDoc)
   {
      Set<WaterReport> results = new HashSet<WaterReport>();

      System.out.println("reportDoc children=" + reportDoc.children().size());

      Element mainTable = reportDoc.getElementById("tblMain");
      // System.out.println("mainTable=" + mainTable);
      Elements rows = mainTable.getElementsByTag("tr");
      System.out.println("found " + rows.size() + " rows");

      for (Element row : rows) {
         Elements cells = row.getElementsByTag("td");
         // - for each waypoint with an entry (e.g. 'WR001')
         // - report column is description
         // - date is report date
         // - look up waypoint in XML file to get coordinates
         String waypoint = cells.get(3).text();
         if (waypoint != null && !waypoint.isEmpty() && waypoint.startsWith("WR")) {
            System.out.println("waypoint=" + waypoint);
            
            String desc = cells.get(4).text();

            String date = cells.get(6).text();

            String rpt = cells.get(5).text();

            WaterReport report = new WaterReport();
            report.setDescription(rpt + ". " + desc);
            report.setLocation(waypoint);
            report.setName(waypoint);
            if (date != null && !date.isEmpty()) {
               try {
                  report.setLastReport(dateFormatter.parse(date));
               }
               catch (ParseException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            }
            report.setSource(SOURCE_TITLE);
            report.setUrl(SOURCE_URL);

            // TODO set the state based on rpt

            // TODO replace with something more elegant
            for (WptType wpt : waypoints) {
               if (wpt.getName().equals(waypoint)) {
                  //System.out.println("found matching lat/lon");
                  // TODO check to see if these are reversed
                  report.setLon(wpt.getLat());
                  report.setLat(wpt.getLon());
               }
            }

            results.add(report);
         }
      }
      
      return results;
   }
}
