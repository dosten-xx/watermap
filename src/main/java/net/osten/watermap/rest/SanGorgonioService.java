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
 * San Gorgonio RESTful service.
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

         // DEO test properties for GeoJSON
         // feature.addProperty("decimal", new BigDecimal("55.55"));
         // feature.addProperty("integer", new BigInteger("77"));

         results.getFeatures().add(feature);
      }

      return results;
   }
}
