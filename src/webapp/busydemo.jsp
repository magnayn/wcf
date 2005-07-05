<%@ page
  session="true"
  contentType="text/html; charset=UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
<title>WCF Slow Computation</title>
</head>
<body bgcolor=white>

<c:if test="${param['work'] != null}">
<h2>Running a 10 seconds job ...</h2>
<%
  try {
    System.out.println("running job ...");
    Thread.sleep(10000);
    System.out.println("done.");
  } catch (Exception e) {
  }
%>
</c:if>


  <h2>Busy page demo</h2>

  Click <a href="busydemo.jsp?work=true">here</a>, to start a job that
  needs about 10 seconds to complete. Try to click
  multiple times (as impatient users may do), then you will be redirected
  to a "busy page". The job will run only once.

</body>
</html>
