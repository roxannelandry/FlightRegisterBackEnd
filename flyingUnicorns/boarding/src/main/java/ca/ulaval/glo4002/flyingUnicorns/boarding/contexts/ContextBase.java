package ca.ulaval.glo4002.flyingUnicorns.boarding.contexts;

import javax.ws.rs.core.Application;

public abstract class ContextBase {

  public abstract Application createApplication();

}