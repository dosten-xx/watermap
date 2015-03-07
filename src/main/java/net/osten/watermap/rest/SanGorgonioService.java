/**
 * 
 */
package net.osten.watermap.rest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.osten.geojson.Feature;
import net.osten.geojson.FeatureCollection;
import net.osten.geojson.Point;
import net.osten.watermap.convert.SanGorgonioReport;
import net.osten.watermap.model.WaterReport;

/**
 * San Gorgonio REST service.
 */
@Path("sang")
public class SanGorgonioService
{
   @EJB
   private SanGorgonioReport converter;
   
   /**
    * Returns GeoJSON for San Gorgonio.
    * 
    * @see http://geojson.org
    * @return GeoJSON FeatureCollection
    */
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public FeatureCollection getReport()
   {
      FeatureCollection results = new FeatureCollection();

      Set<WaterReport> wrs = converter.convert();

      for (WaterReport wr : wrs) {
         Point point = new Point();
         point.setCoords(wr.getLat(), wr.getLon(), null);
         
         Feature feature = new Feature();
         feature.setGeometry(point);
         feature.addProperty("name", wr.getName());
         feature.addProperty("location", wr.getLocation());
         feature.addProperty("state", wr.getState());
         feature.addProperty("description", wr.getDescription());
         feature.addProperty("lastReport", wr.getLastReport());
         feature.addProperty("source", wr.getSource());
         feature.addProperty("url", wr.getUrl());
         
         // DEO test properties for GeoJSON
         //feature.addProperty("decimal", new BigDecimal("55.55"));
         //feature.addProperty("integer", new BigInteger("77"));
         
         results.getFeatures().add(feature);
      }
      
      return results;
   }
}
