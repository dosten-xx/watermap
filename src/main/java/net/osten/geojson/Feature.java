/**
 * 
 */
package net.osten.geojson;

import java.util.HashMap;
import java.util.Map;

/**
 * GeoJSON Feature.
 */
public class Feature
{
   private String type = "Feature";
   private Geometry geometry = null;
   private Map<String, Object> properties = new HashMap<String, Object>();

   public String getType()
   {
      return type;
   }

   public Geometry getGeometry()
   {
      return geometry;
   }

   public void setGeometry(Geometry geometry)
   {
      this.geometry = geometry;
   }

   public Map<String, Object> getProperties()
   {
      return properties;
   }

   public void addProperty(String name, Object value)
   {
      properties.put(name, value);
   }
}
