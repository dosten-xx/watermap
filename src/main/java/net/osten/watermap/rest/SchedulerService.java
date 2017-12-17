package net.osten.watermap.rest;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.osten.watermap.batch.JobScheduler;

/**
 * Service for managing the scheduler.
 */
@Path("scheduler")
public class SchedulerService
{
   @EJB
   JobScheduler scheduler;
   
   /**
    * Runs a job.
    * 
    * @param job job to run
    * @return job status
    */
   @POST
   @Produces(MediaType.TEXT_PLAIN)
   public String runJob(@FormParam("job") String job)
   {
      String result = "job started";

      if (job == null) {
         result = "Job not provided";
      }
      else {
         switch (job) {
         case "fetchpct":
            scheduler.fetchPCT();
            break;
         case "fetchpctwaypoints":
            scheduler.fetchPCTWaypoints();
            break;
         case "fetchsang":
            scheduler.fetchSanG();
            break;
         default:
            result = "Unknown job " + job;
         }
      }
      
      return result;
   }
}
