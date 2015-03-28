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
package net.osten.watermap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Model for a water report.
 */
public class WaterReport implements Serializable
{
   private static final long serialVersionUID = 1L;
   private String name = null;
   private String description = null;
   private BigDecimal lat = null;
   private BigDecimal lon = null;
   private WaterState state = WaterState.UNKNOWN;
   private String source = null;
   private Date lastReport = null;
   private String location = null;
   private String url = null;

   /**
    * Default construtor.
    */
   public WaterReport()
   {}

   /**
    * Construtor with name and source.  Use this for set comparisons.
    * 
    * @param name name
    * @param source source
    */
   public WaterReport(String name, String source)
   {
      this.name = name;
      this.source = source;
   }

   /**
    * Checks for equality based on name and source.
    * 
    * @param obj object to compare to
    * @return true if obj is a WaterReport with same name and source; false otherwise
    */
   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof WaterReport)) {
         return false;
      }
      WaterReport other = (WaterReport) obj;
      if (name == null) {
         if (other.name != null) {
            return false;
         }
      }
      else if (!name.equals(other.name)) {
         return false;
      }
      if (source == null) {
         if (other.source != null) {
            return false;
         }
      }
      else if (!source.equals(other.source)) {
         return false;
      }
      return true;
   }

   public String getDescription()
   {
      return description;
   }

   public Date getLastReport()
   {
      return lastReport;
   }

   public BigDecimal getLat()
   {
      return lat;
   }

   public String getLocation()
   {
      return location;
   }

   public BigDecimal getLon()
   {
      return lon;
   }

   public String getName()
   {
      return name;
   }

   public String getSource()
   {
      return source;
   }

   public WaterState getState()
   {
      return state;
   }

   public String getUrl()
   {
      return url;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (source == null ? 0 : source.hashCode());
      return result;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public void setLastReport(Date lastReport)
   {
      this.lastReport = lastReport;
   }

   public void setLat(BigDecimal lat)
   {
      this.lat = lat;
   }

   public void setLocation(String location)
   {
      this.location = location;
   }

   public void setLon(BigDecimal lon)
   {
      this.lon = lon;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setSource(String source)
   {
      this.source = source;
   }

   public void setState(WaterState state)
   {
      this.state = state;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }

   @Override
   public String toString()
   {
      StringBuilder builder = new StringBuilder();
      builder.append("WaterReport \n   ");
      if (name != null) {
         builder.append("name=");
         builder.append(name);
         builder.append("\n   ");
      }
      if (description != null) {
         builder.append("description=");
         builder.append(description);
         builder.append("\n   ");
      }
      if (lat != null) {
         builder.append("lat=");
         builder.append(lat);
         builder.append("\n   ");
      }
      if (lon != null) {
         builder.append("lon=");
         builder.append(lon);
         builder.append("\n   ");
      }
      if (state != null) {
         builder.append("state=");
         builder.append(state);
         builder.append("\n   ");
      }
      if (source != null) {
         builder.append("source=");
         builder.append(source);
         builder.append("\n   ");
      }
      if (lastReport != null) {
         builder.append("lastReport=");
         builder.append(lastReport);
         builder.append("\n   ");
      }
      if (location != null) {
         builder.append("location=");
         builder.append(location);
         builder.append("\n   ");
      }
      if (url != null) {
         builder.append("url=");
         builder.append(url);
      }
      return builder.toString();
   }
}
