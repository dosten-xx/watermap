/**
 * 
 */
package net.osten.geojson;

import java.util.ArrayList;
import java.util.List;

/**
 * GeoJSON FeatureCollection.
 */
public class FeatureCollection
{
   private String type = "FeatureCollection";
   private List<Feature> features = new ArrayList<Feature>();

   public String getType()
   {
      return type;
   }

   public List<Feature> getFeatures()
   {
      return features;
   }

   public void setFeatures(List<Feature> features)
   {
      this.features = features;
   }

}
