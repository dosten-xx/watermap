/**
 *
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
      builder.append("WaterReport [");
      if (name != null) {
         builder.append("name=");
         builder.append(name);
         builder.append(", ");
      }
      if (description != null) {
         builder.append("description=");
         builder.append(description);
         builder.append(", ");
      }
      if (lat != null) {
         builder.append("lat=");
         builder.append(lat);
         builder.append(", ");
      }
      if (lon != null) {
         builder.append("lon=");
         builder.append(lon);
         builder.append(", ");
      }
      if (state != null) {
         builder.append("state=");
         builder.append(state);
         builder.append(", ");
      }
      if (source != null) {
         builder.append("source=");
         builder.append(source);
         builder.append(", ");
      }
      if (lastReport != null) {
         builder.append("lastReport=");
         builder.append(lastReport);
      }
      builder.append("]");
      return builder.toString();
   }
}
