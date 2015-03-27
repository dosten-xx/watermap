/**
 * 
 */
package net.osten.watermap.convert;

import net.osten.watermap.model.WaterState;

/**
 * Converts text into a state.
 */
public final class WaterStateParser
{
   private WaterStateParser()
   {}

   /**
    * Returns a state for a given text.
    * 
    * @param lcDesc text to parse
    * @return water state
    */
   public static WaterState parseState(String lcDesc)
   {
      lcDesc = lcDesc.toLowerCase();
      
      // DEO goes from pessismistic to optimistic so to be 
      // the most non-misleading
      
      if (lcDesc.contains("no water")
               || lcDesc.contains("nothing")
               || lcDesc.contains("dry")) {
         return WaterState.DRY;
      }
      else if (lcDesc.contains("barely")
               || lcDesc.contains("stagnant")) {
         return WaterState.LOW;
      }
      else if (lcDesc.contains("sufficient")
               || lcDesc.contains("is flowing")
               || lcDesc.contains("has water")
               || lcDesc.contains("decent")) {
         return WaterState.MEDIUM;
      }
      else if (lcDesc.contains("good")
         || lcDesc.contains("excellent")
         || lcDesc.contains("plenty")
         || lcDesc.contains("great")
         || lcDesc.contains("nicely")
         || lcDesc.contains("full")
         || lcDesc.contains("lot of water")
         || lcDesc.contains("well")) {
         return WaterState.HIGH;
      }

      return WaterState.UNKNOWN;
   }
}
