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
package net.osten.watermap.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.batch.api.Batchlet;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Request;

import net.osten.watermap.application.WatermapConfig;

@Dependent
@Named("FetchPCTWaypointsJob")
public class FetchPCTWaypointsJob implements Batchlet
{
   private static Logger log = Logger.getLogger(FetchPCTWaypointsJob.class.getName());
   // private static char[] CA_SECS = new char[]
   // {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r'};
   private static String[] URLS = new String[] { "CA", "OR", "WA" };

   @Inject
   private JobContext context;

   @EJB
   private WatermapConfig config;

   private File outputDir = null;

   /**
    * Fetch the PCT waypoint files and save in output directory.
    *
    * @see javax.batch.api.Batchlet#process()
    * @return FAILED if the files cannot be downloaded or can't be written to disk; COMPLETED otherwise
    */
   @Override
   public String process() throws Exception
   {
      outputDir = new File(config.getString("output_dir"));

      if (!outputDir.isDirectory()) {
         log.log(Level.WARNING, "Output directory [{0}] is not a directory.", outputDir);
         return BatchStatus.FAILED.toString();
      }
      else if (!outputDir.canWrite()) {
         log.log(Level.WARNING, "Output directory [{0}] is not writable.", outputDir);
         return BatchStatus.FAILED.toString();
      }

      for (String url : URLS) {
         log.log(Level.FINE, "Fetching PCT waypoints from {0}", new Object[] { url });

         byte[] response = Request.Get(context.getProperties().getProperty(url)).execute().returnContent().asBytes();
         File outputFile = new File(outputDir.getAbsolutePath() + File.separator + url + "_state_gps.zip");
         FileUtils.writeByteArrayToFile(outputFile, response);

         if (outputFile.exists()) {
            unZipIt(outputFile, outputDir);
         }
      }

      return BatchStatus.COMPLETED.toString();
   }

   /**
    * Does nothing.
    * 
    * @see javax.batch.api.Batchlet#stop()
    */
   @Override
   public void stop() throws Exception
   {
      log.info("Stopping...");
   }

   private void unZipIt(File zipFile, File outputFolder)
   {
      byte[] buffer = new byte[1024];

      try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
         // get the zipped file list entry
         ZipEntry ze = zis.getNextEntry();
         while (ze != null) {
            String fileName = ze.getName();
            File newFile = new File(outputFolder.getAbsolutePath() + File.separator + fileName);

            log.log(Level.FINER, "file unzip : {0}", new Object[] { newFile.getAbsoluteFile() });

            // create all non existing folders else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            try (FileOutputStream fos = new FileOutputStream(newFile)) {
               int len;
               while ((len = zis.read(buffer)) > 0) {
                  fos.write(buffer, 0, len);
               }
            }
            ze = zis.getNextEntry();
            
            // move newFile to data directory
            try {
               // have to delete first since FileUtils does not overwrite
               File destinationFile = new File(outputFolder + File.separator + newFile.getName());
               if (destinationFile.exists()) {
                  destinationFile.delete();
               }
               FileUtils.moveFileToDirectory(newFile, outputFolder, false);
            }
            catch (FileExistsException ioe) {
               log.warning(ioe.getLocalizedMessage());
            }
            catch (IOException ioe) {
               log.warning(ioe.getLocalizedMessage());
            }
         }

         // close the last entry
         zis.closeEntry();
      }
      catch (IOException e) {
         log.warning(e.getLocalizedMessage());
      }
   }
}
