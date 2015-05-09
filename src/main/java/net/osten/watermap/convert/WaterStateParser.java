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
package net.osten.watermap.convert;

import net.osten.watermap.model.WaterState;

/**
 * Converts text into a state.
 */
public final class WaterStateParser
{
   /**
    * Returns a state for a given text.
    *
    * @param lcDesc text to parse
    * @return water state
    */
   public static WaterState parseState(String lcDesc)
   {
      lcDesc = lcDesc.toLowerCase().trim();

      // DEO goes from pessismistic to optimistic so to be
      // the most non-misleading

      if (lcDesc.contains("no water")
               || lcDesc.contains("nothing")
               || lcDesc.contains("dry")) {
         return WaterState.DRY;
      }
      else if (lcDesc.contains("barely") 
               || lcDesc.contains("low flow")
               || lcDesc.contains("small pools")
               || lcDesc.contains("stagnant")) {
         return WaterState.LOW;
      }
      else if (lcDesc.contains("sufficient") 
               || lcDesc.contains("is flowing") 
               || lcDesc.contains("flowing water") 
               || lcDesc.contains("has water") 
               || lcDesc.contains("water available") 
               || lcDesc.contains("decent")
               || lcDesc.contains("water is available")
               || lcDesc.contains("steady flow")
               || lcDesc.equals("flowing")) {
         return WaterState.MEDIUM;
      }
      else if (lcDesc.contains("good") 
               || lcDesc.contains("excellent") 
               || lcDesc.contains("plenty") 
               || lcDesc.contains("great") 
               || lcDesc.contains("nicely") 
               || lcDesc.contains("full")
               || lcDesc.contains("faucet working")
               || lcDesc.contains("faucet on")
               || lcDesc.contains("spigot is on")
               || lcDesc.contains("faucet is on")
               || lcDesc.contains("faucets on")
               || lcDesc.contains("water on")
               || lcDesc.contains("lot of water") 
               || lcDesc.contains("lots of water")
               || lcDesc.contains("water everywhere")
               || lcDesc.contains("strong flow")
               || lcDesc.contains("flowing fine")
               || lcDesc.contains("flowing at more than")
               || lcDesc.contains("water flowing")
               || lcDesc.contains("water cache is stocked")
               || lcDesc.contains("well")) {
         return WaterState.HIGH;
      }

      return WaterState.UNKNOWN;
   }

   private WaterStateParser()
   {}
}
