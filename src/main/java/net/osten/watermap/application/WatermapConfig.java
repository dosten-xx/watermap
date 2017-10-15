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
package net.osten.watermap.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;

@Singleton
@Startup
@Named("config")
/**
 * Application wide configuration. Reads from the following sources in precedence order:
 * <ol>
 * <li>Environment variables</li>
 * <li>System properties</li>
 * </ol>
 */
public class WatermapConfig
{
   private static Logger log = Logger.getLogger(WatermapConfig.class.getName());
   private CompositeConfiguration config = null;
   
   public static final String OUTPUT_DIR = "output_dir";

   /**
    * Default constructor.
    */
   public WatermapConfig()
   {
      SystemConfiguration sysConfig = new SystemConfiguration();
      EnvironmentConfiguration envConfig = new EnvironmentConfiguration();

      config = new CompositeConfiguration();
      config.addConfiguration(envConfig);
      config.addConfiguration(sysConfig);

      dump();
   }

   /**
    * Dumps all the configuration keys and values to log at INFO level.
    */
   public void dump()
   {
      config.getKeys().forEachRemaining(k -> log.log(Level.INFO, "{0}={1}", new Object[] { k, config.getString(k) }));
   }

   /**
    * Gets a configuration as an int.
    *
    * @param key configuration key
    * @param dflt default value
    * @return key value or default value if key does not exist
    */
   public int getInt(String key, int dflt)
   {
      return config.getInt(key, dflt);
   }

   /**
    * Gets a configuration as an Integer.
    *
    * @param key configuration key
    * @param dflt default value; can be null
    * @return key value or default value if key does not exist
    */
   public Integer getInteger(String key, Integer dflt)
   {
      return config.getInteger(key, dflt);
   }

   /**
    * Gets a configuration as a string.
    *
    * @param key configuration key
    * @return key value or null if key does not exist
    */
   public String getString(String key)
   {
      return config.getString(key);
   }
}
