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

   public void addProperty(String name, Object value)
   {
      properties.put(name, value);
   }

   public Geometry getGeometry()
   {
      return geometry;
   }

   public Map<String, Object> getProperties()
   {
      return properties;
   }

   public String getType()
   {
      return type;
   }

   public void setGeometry(Geometry geometry)
   {
      this.geometry = geometry;
   }
}
