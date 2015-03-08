/**
 * 
 */
package net.osten.watermap.rest;

import java.io.IOException;
import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.osten.geojson.Feature;
import net.osten.geojson.FeatureCollection;
import net.osten.geojson.Point;
import net.osten.watermap.convert.PCTReport;
import net.osten.watermap.model.WaterReport;

/**
 * PCT Water Report RESTful service.
 */
@Path("pct")
public class PCTService
{
   @EJB
   private PCTReport converter;
   
   /**
    * Returns GeoJSON for PCT.
    * 
    * @see http://geojson.org
    * @return GeoJSON FeatureCollection
    * @throws IOException if error occurs during conversion 
    */
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public FeatureCollection getReport() throws IOException
   {
      FeatureCollection results = new FeatureCollection();

      Set<WaterReport> wrs = converter.convert();

      for (WaterReport wr : wrs) {
         Point point = new Point();
         point.setCoords(wr.getLon(), wr.getLat(), null);
         
         Feature feature = new Feature();
         feature.setGeometry(point);
         feature.addProperty("name", wr.getName());
         feature.addProperty("location", wr.getLocation());
         feature.addProperty("state", wr.getState());
         feature.addProperty("description", wr.getDescription());
         feature.addProperty("lastReport", wr.getLastReport());
         feature.addProperty("source", wr.getSource());
         feature.addProperty("url", wr.getUrl());
         
         results.getFeatures().add(feature);
      }
      
      return results;
   }
}
