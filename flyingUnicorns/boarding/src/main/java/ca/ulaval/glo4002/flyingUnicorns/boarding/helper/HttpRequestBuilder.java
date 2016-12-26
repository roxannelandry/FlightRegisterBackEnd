package ca.ulaval.glo4002.flyingUnicorns.boarding.helper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class HttpRequestBuilder {

  public static Invocation.Builder buildHttpRequestFromUrl(String url) {
    return prepareRequest(url);
  }

  private static Builder prepareRequest(String url) {
    WebTarget webTarget = createWebTarget(url);
    Invocation.Builder invocationBuilder = setParamForBuilder(webTarget);

    return invocationBuilder;
  }

  private static WebTarget createWebTarget(String url) {
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target(url);

    return webTarget;
  }

  private static Builder setParamForBuilder(WebTarget webTarget) {
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

    return invocationBuilder;
  }

}