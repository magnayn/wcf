package com.tonbeller.wcf.controller;

import junit.framework.TestCase;

public class RequestSynchronizerTest extends TestCase {

  RequestSynchronizer rsync;

  /**
   * Constructor for RequestSynchronizerTest.
   * @param arg0
   */
  public RequestSynchronizerTest(String arg0) {
    super(arg0);
  }

  abstract class MyRequest implements RequestSynchronizer.Handler, Runnable {
    String error = "nothing called";

    public void normalRequest() {
      error = "normalRequest";
    }

    public void recursiveRequest() throws Exception {
      error = "recursiveRequest";
    }

    public void showBusyPage(boolean redirect) throws Exception {
      error = "showBusyPage";
    }

    public void run() {
      try {
        rsync.handleRequest(this);
      } catch (Exception e) {
        fail(e.toString());
      }
    }

    public void validate() {
      assertNull(error);
    }

    public String getResultURI() {
      return null;
    }

    public boolean isBusyPage() {
      return false;
    }
  }

  class NormalRequest extends MyRequest {
    public void normalRequest() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
      error = null;
    }
  }

  class Previous extends MyRequest {
    public void showBusyPage(boolean redirect) throws Exception {
      error = null;
    }
  }

  class Recursive extends MyRequest {
    public void normalRequest() {
      error ="normalRequest";
      try {
        rsync.handleRequest(this);
      } catch (Exception e) {
        fail(e.toString());
      }
    }
    public void recursiveRequest() throws Exception {
      error = null;
    }
  }

  public void test1() throws Exception {
    NormalRequest n = new NormalRequest();
    Thread t = new Thread(n);
    t.start();
    t.join();
    n.validate();
  }

  public void testRecursive() throws Exception {
    Recursive n = new Recursive();
    Thread t = new Thread(n);
    t.start();
    t.join();
    n.validate();
  }
  
  public void test2() throws Exception {
    NormalRequest n = new NormalRequest();
    Thread t1 = new Thread(n);
    t1.start();

    // sicherstellen, dass t1 wirklich loslaeuft
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
    }

    Previous p = new Previous();
    Thread t2 = new Thread(p);
    t2.start();

    t1.join();
    t2.join();
    n.validate();
    p.validate();
  }

  public void setUp() {
    rsync = new RequestSynchronizer();
  }
}
