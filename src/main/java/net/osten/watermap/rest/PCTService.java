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
package net.osten.watermap.rest;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

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
   private static Logger log = Logger.getLogger(PCTService.class.getName());

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
      log.info("Getting PCT report...");
      
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
