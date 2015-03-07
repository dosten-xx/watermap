/**
 *
 */
package net.osten.watermap.convert;

import java.util.Set;

import net.osten.watermap.model.WaterReport;

/**
 * Convert service interface.
 */
public interface ConvertService
{
   /**
    * Convert source data to list of water reports.
    *
    * @return set of water reports
    */
   Set<WaterReport> convert();

   /**
    * Used for initialization such as preloading coordinates.
    */
   void initialize();

   /**
    * Is the service ready to convert.
    *
    * @return true if ready; false if the service cannot convert due to initialization failure
    */
   boolean isReady();
}
