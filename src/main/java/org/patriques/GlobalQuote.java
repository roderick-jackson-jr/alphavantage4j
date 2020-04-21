package org.patriques;

import org.patriques.input.Function;
import org.patriques.input.Symbol;
import org.patriques.output.quote.GlobalQuoteResponse;

/**
 * The Batch Stock Quotes api provides stock quotes give a list of stock symbols.
 */
public class GlobalQuote {

  private final ApiConnector apiConnector;

  /**
   * Constructs a Batch Stock Quotes api endpoint with the help of an {@link ApiConnector}
   *
   * @param apiConnector the connection to the api
   */
  public GlobalQuote(ApiConnector apiConnector) {
    this.apiConnector = apiConnector;
  }

  /**
   * This API returns stock quotes updated realtime.
   *
   * @param symbol the stock symbols to lookup
   * @return {@link GlobalQuoteResponse} stock quote data
   */
  public GlobalQuoteResponse quote(String symbol) {
    String json = apiConnector.getRequest(new Symbol(symbol), Function.GLOBAL_QUOTE);
    return GlobalQuoteResponse.from(json);
  }
}
