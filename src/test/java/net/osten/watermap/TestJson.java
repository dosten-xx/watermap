package net.osten.watermap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.osten.watermap.model.WaterReport;
import net.osten.watermap.model.WaterState;

import org.junit.Test;

import com.google.gson.Gson;

public class TestJson
{
   private Gson gson = new Gson();

   @Test
   public void test()
   {
      WaterReport wr = new WaterReport();
      wr.setDescription("example description");
      wr.setLastReport(new Date());
      wr.setLat(new BigDecimal("32.6007"));
      wr.setLon(new BigDecimal("-160.3434"));
      wr.setLocation("PCT-A");
      wr.setName("WR001TEST");
      wr.setState(WaterState.LOW);
      wr.setSource("pctwater.org");

      String json = gson.toJson(wr);
      System.out.println("json=" + json);

      List<WaterReport> wrs = new ArrayList<WaterReport>();
      wrs.add(wr);

      WaterReport wr2 = new WaterReport();
      wr2.setDescription("example description");
      wr2.setLastReport(new Date());
      wr2.setLat(new BigDecimal("32.6007"));
      wr2.setLon(new BigDecimal("-160.3434"));
      wr2.setLocation("PCT-B");
      wr2.setName("WR002TEST");
      wr2.setState(WaterState.HIGH);
      wr2.setSource("pctwater.org");

      wrs.add(wr2);

      json = gson.toJson(wrs);
      System.out.println("json=" + json);
   }
}
