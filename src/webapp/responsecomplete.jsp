<%@ page
  session="true"
  contentType="text/html; charset=UTF-8"
  import="com.tonbeller.wcf.controller.*,
         javax.servlet.*, javax.servlet.http.*, java.io.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>

<html>
<head>
<title>WCF ResponseComplete</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/status/status.css">
</head>
<body bgcolor="white">

<h2>WCF StatusLine</h2>

<%
  RequestListener r = new RequestListener() {
    public void request(RequestContext context) throws Exception {
      PrintWriter pw = null;
      try {
        pw = context.getResponse().getWriter();
        pw.print(" ");
        pw.close();
      } finally {
         if(pw!=null)
           pw.close();
      }
      context.setResponseComplete(true);
    }
  };
  Dispatcher ds = new DispatcherSupport();
  ds.addRequestListener("complete", null, r);
  Controller.instance(pageContext.getSession()).addRequestListener(ds);
%>

Click <a href="?complete">here</a> to get no result.
Click <a href="?notcomplete">here</a> to get result.

</body>
</html>
