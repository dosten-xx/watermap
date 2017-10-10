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

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class JobScheduler
{
   private static Logger log = Logger.getLogger(JobScheduler.class.getName());

   @Schedule(dayOfWeek = "*", hour = "1", minute = "0", second = "0", persistent = false)
   public void fetchPCT()
   {
      log.info("Starting PCT fetch...");
      BatchRuntime.getJobOperator().start("FetchPCT", new Properties());
   }

   @Schedule(month = "*", dayOfMonth = "*", hour = "*", minute = "*/10", second = "0", persistent = false)
   public void fetchPCTWaypoints()
   {
      log.info("Starting PCT Waypoint fetch...");
      BatchRuntime.getJobOperator().start("FetchPCTWaypoints", new Properties());
   }

   @Schedule(dayOfWeek = "*", hour = "4", minute = "0", second = "0", persistent = false)
   public void fetchSanG()
   {
      log.info("Starting SanG fetch...");
      BatchRuntime.getJobOperator().start("FetchSanG", new Properties());
   }

   @Schedule(hour="*", minute = "*/5", second = "0", persistent = false)
   public void scheduleDump()
   {
      log.log(Level.INFO, "Current jobs:");
      JobOperator scheduler = BatchRuntime.getJobOperator();
      for (String name : scheduler.getJobNames()) {
         for (JobInstance instance : scheduler.getJobInstances(name, 0, 1)) {
            for (JobExecution execution : scheduler.getJobExecutions(instance)) {
               log.log(Level.INFO, "\tJob {0} [{1}] started {2} with status {3}", new Object[] { name, execution.getExecutionId(), execution.getStartTime(), execution.getBatchStatus() });
            }
         }
      }
   }
}
