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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.Batchlet;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Request;

import com.google.common.base.Charsets;

import net.osten.watermap.application.WatermapConfig;

@Dependent
@Named("FetchSanGJob")
public class FetchSanGJob implements Batchlet
{
   private static Logger log = Logger.getLogger(FetchSanGJob.class.getName());
   private static String[] URLS = new String[] { "SanG" };

   @Inject
   private JobContext context;

   @Inject
   private WatermapConfig config;

   private File outputDir = null;

   /**
    * Fetch the San Gorgonio files and save in output directory.
    *
    * @see javax.batch.api.Batchlet#process()
    * @return FAILED if the files cannot be downloaded or can't be written to disk; COMPLETED otherwise
    */
   @Override
   public String process() throws Exception
   {
      outputDir = new File(config.getString("output_dir"));

      // curl -o $od/datafile.txt http://www.howlingduck.com/triterra/gorgonio/datafile.txt
      if (!outputDir.isDirectory()) {
         log.log(Level.WARNING, "Output directory [{0}] is not a directory.", outputDir);
         return BatchStatus.FAILED.toString();
      }
      else if (!outputDir.canWrite()) {
         log.log(Level.WARNING, "Output directory [{0}] is not writable.", outputDir);
         return BatchStatus.FAILED.toString();
      }

      for (String url : URLS) {
         log.log(Level.FINE, "Fetching PCT to {0}", new Object[] { url });
         String response = Request.Get(context.getProperties().getProperty(url)).execute().returnContent().asString();
         File outputFile = new File(outputDir.getAbsolutePath() + File.separator + "datafile.txt");
         FileUtils.writeStringToFile(outputFile, response, Charsets.UTF_8);
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
}
