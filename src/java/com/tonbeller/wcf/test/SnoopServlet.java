package com.tonbeller.wcf.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SnoopServlet extends HttpServlet {

  // dump stream
  protected void xxdoPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    InputStream is = req.getInputStream();
    int c = is.read();
    out.print("Stream Content:");
    while (c >= 0) {
      out.print(Integer.toHexString(c) + " [" + (char)c + "] ");
      c = is.read();
    }
    out.print("done");
    out.close();
  }

  // dump string
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    String s = req.getParameter("text");
    out.print("String Content:" + s);
    out.print("String Bytes:");
    char[] arr = s.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      int c = arr[i];
      out.print(Integer.toHexString(c) + " [" + arr[i] + "] ");
    }
    out.print("done");
    out.close();
  }
}
