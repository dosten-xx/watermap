/**
 * 
 */
package net.osten.geojson;

import java.math.BigDecimal;

/**
 * @author Darren
 *
 */
public class Point implements Geometry
{
   private String type = "Point";
   private BigDecimal coords[] = null;

   @Override
   public String getType()
   {
      return type;
   }

   public void setCoords(BigDecimal lat, BigDecimal lon, BigDecimal alt)
   {
      if (alt == null) {
         coords = new BigDecimal[] { lat, lon };
      }
      else {
         coords = new BigDecimal[] { lat, lon, alt };
      }
   }

   @Override
   public BigDecimal[] getCoordinates()
   {
      return coords;
   }
}
