package org.patriques.output.quote;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.patriques.GlobalQuote;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.JsonParser;
import org.patriques.output.quote.data.StockQuote;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

/**
 * Representation of batch stock quote response from api.
 *
 * @see GlobalQuote
 */
public class GlobalQuoteResponse {
  private final StockQuote stockQuote;

  private GlobalQuoteResponse(final StockQuote stockQuote) {
    this.stockQuote = stockQuote;
  }


  /**
   * List of StockQuote
   *
   * @return list of {@link StockQuote}
   */
  public StockQuote getStockQuote() {
    return stockQuote;
  }

  /**
   * Creates {@code BatchStockQuotesResponse} instance from json.
   *
   * @param json string to parse
   * @return BatchStockQuotesResponse instance
   */
  public static GlobalQuoteResponse from(String json)  {
    Parser parser = new Parser();
    return parser.parseJson(json);
  }

  /**
   * Helper class for parsing json to {@code BatchStockQuotesResponse}.
   *
   * @see JsonParser
   */
  private static class Parser extends JsonParser<GlobalQuoteResponse> {

    @Override
    protected GlobalQuoteResponse resolve(final JsonObject rootObject) {
      Type dataType = new TypeToken<Map<String, String>>() {
      }.getType();

      try {
        Map<String, String> stockData = GSON.fromJson(rootObject.get("Global Quote"), dataType);
        StockQuote stockQuote = new StockQuote(stockData.get("01. symbol"),
                Double.parseDouble(stockData.get("05. price")),
                getVolume(stockData),
                LocalDate.parse(stockData.get("07. latest trading day"), SIMPLE_DATE_FORMAT).atStartOfDay());
        return new GlobalQuoteResponse(stockQuote);
      } catch (JsonSyntaxException e) {
        throw new AlphaVantageException("GLobalQuotes api change", e);
      }
    }

    private long getVolume(final Map<String, String> values) {
      try {
        return Long.parseLong(values.get("06. volume"));
      } catch (NumberFormatException e) {
        return 0L;
      }
    }
  }
}
