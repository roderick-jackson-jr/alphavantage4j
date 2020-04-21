package org.patriques;

import org.junit.Test;
import org.patriques.output.quote.GlobalQuoteResponse;
import org.patriques.output.quote.data.StockQuote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BatchStockQuotesTest {

  @Test
  public void singleStockQuote() {
//    String json = "" +
//            "{\n" +
//            "    \"Meta Data\": {\n" +
//            "        \"1. Information\": \"Batch Stock Market Quotes\",\n" +
//            "        \"2. Notes\": \"IEX Real-Time Price provided for free by IEX (https://iextrading.com/developer/).\",\n" +
//            "        \"3. Time Zone\": \"US/Eastern\"\n" +
//            "    },\n" +
//            "    \"Stock Quotes\": [\n" +
//            "        {\n" +
//            "            \"1. symbol\": \"MSFT\",\n" +
//            "            \"2. price\": \"96.3850\",\n" +
//            "            \"3. volume\": \"987654321\",\n" +
//            "            \"4. timestamp\": \"2018-05-18 15:59:48\"\n" +
//            "        }\n" +
//            "    ]\n" +
//            "}";

    String json ="{\r\n    \"Global Quote\": {\r\n        \"01. symbol\": \"IBM\",\r\n        \"02. open\": \"114.0000\",\r\n        \"03. high\": \"116.8500\",\r\n        \"04. low\": \"112.0600\",\r\n        \"05. price\": \"115.8200\",\r\n        \"06. volume\": \"10604560\",\r\n        \"07. latest trading day\": \"2020-04-21\",\r\n        \"08. previous close\": \"120.4100\",\r\n        \"09. change\": \"-4.5900\",\r\n        \"10. change percent\": \"-3.8120%\"\r\n    }\r\n}";
    GlobalQuote batchStockQuotes = new GlobalQuote(parameters -> json);
    GlobalQuoteResponse resp = batchStockQuotes.quote("DUMMY");


    StockQuote stockQuote = resp.getStockQuote();

    assertThat(stockQuote.getSymbol(), is(equalTo("IBM")));
    assertThat(stockQuote.getPrice(), is(equalTo(115.82)));
    assertThat(stockQuote.getVolume(), is(equalTo(10604560L)));
    assertThat(stockQuote.getTimestamp(), is(equalTo(LocalDateTime.of(2020, 04, 21, 00, 00, 00))));
  }

  @Test
  public void stockNoVolume() {
    String json = "" +
            "{\n" +
            "    \"Meta Data\": {\n" +
            "        \"1. Information\": \"Batch Stock Market Quotes\",\n" +
            "        \"2. Notes\": \"IEX Real-Time Price provided for free by IEX (https://iextrading.com/developer/).\",\n" +
            "        \"3. Time Zone\": \"US/Eastern\"\n" +
            "    },\n" +
            "    \"Stock Quotes\": [\n" +
            "        {\n" +
            "            \"1. symbol\": \"MSFT\",\n" +
            "            \"2. price\": \"96.3850\",\n" +
            "            \"3. volume\": \"--\",\n" +
            "            \"4. timestamp\": \"2018-05-18 15:59:48\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    GlobalQuote batchStockQuotes = new GlobalQuote(parameters -> json);
    GlobalQuoteResponse resp = batchStockQuotes.quote("DUMMY");

    StockQuote stockQuote = resp.getStockQuote();
    assertThat(stockQuote.getVolume(), is(equalTo(0L)));
  }
}
