package com.redhat.gpte.training.camel;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;

public class EnricherRoute extends RouteBuilder {

  private static SampleAggregator aggregationStrategy = new SampleAggregator();

  @Override
  public void configure() throws Exception {
    //@formatter:off
    from("timer:enrich?period=5s")
      .setBody().constant("message")
      .log(">> Before enrichment. My body is : ${body}")
      .enrich("direct:resource", aggregationStrategy)
      .log(">> After enrichment. My body is : ${in.body}");
    
    from("direct:resource")
      .setExchangePattern(ExchangePattern.InOut)
      .transform().constant("blah");
    //@formatter:on
  }

}
