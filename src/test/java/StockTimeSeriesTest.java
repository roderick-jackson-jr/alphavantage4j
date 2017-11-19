/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Before;
import org.junit.Test;

public class StockTimeSeriesTest {
  private static Settings DUMMY_SETTINGS = new Settings("key", 0);
  private StockTimeSeries stockTimeSeries;

  @Before
  public void setUp() throws Exception {
    ApiConnector testApiConnector = new TestApiConnector();
    stockTimeSeries = new StockTimeSeries(DUMMY_SETTINGS, testApiConnector);
  }

  @Test
  public void intraDay() {

  }


}