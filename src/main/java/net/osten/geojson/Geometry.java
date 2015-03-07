/**
 * 
 */
package net.osten.geojson;

import java.math.BigDecimal;

/**
 * GeoJSON Geometry interface.
 */
public interface Geometry
{
   String getType();
   
   BigDecimal[] getCoordinates();
}
