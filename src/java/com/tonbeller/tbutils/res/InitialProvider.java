package com.tonbeller.tbutils.res;


/**
 * Base interface for the initial provider. I.e. the provider
 * that looks up the names of resourcebundles etc.
 * 
 * @author av
 */
public interface InitialProvider extends ResourceProvider {
  public static final String USER_PROPERTIES = "user";
  public static final String RESFACTORY_PROPERTIES = "resfactory";
}