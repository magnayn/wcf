<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page
  session="true"
  contentType="text/html"
  import="java.util.Enumeration,com.tonbeller.wcf.charset.CharsetFilter"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=<wcf:charset/>">
</head>
<body bgcolor="white">
<h2>Character Encoding</h2>

<form method="post" action="charencmain.jsp" id="form01">
fmt:message: <fmt:message key="message.key"/><br />
Request Encoding: <%= request.getCharacterEncoding() %><br />
Response Encoding: <%= response.getCharacterEncoding() %><br />
Filter Encoding: <%= CharsetFilter.getEncoding() %><br />
WCF Encoding: <wcf:charset/><br />
Umlaute: &uuml;<br />
Parameter: <c:out value="${param.text}"/>
<p />
<TEXTAREA name="text" rows="10" cols="20">
ru-abc = &#1092;&#1080;&#1089;
gr-abc = &#945;&#946;&#947;
ysp = &#376;&#353;&#968;
arrow = &#8592;
uuml = &uuml;&Uuml;
</TEXTAREA>
<p />
<INPUT type="submit" id="submit" name="submit"/>
</form>

<p />
URLEncoder.encode(..., "<a href="charenc.jsp?text=%3F%3F%3F%3F%C4%D6%DC%3F%3F%3F%3F">ISO-8859-1</a>")
<br />
URLEncoder.encode(..., "<a id="a02" href="charenc.jsp?text=%E2%82%AC%CE%B1%CE%B2%CE%B3%C3%84%C3%96%C3%9C%C5%B8%C5%A1%CF%88%E2%86%90">UTF-8</a>")

<p/>
Header:
<pre>
<%
   Enumeration en = request.getHeaderNames();
   //JspWriter out = pageContext.getOut();
   while (en.hasMoreElements()) {
     String name = (String)en.nextElement();
     out.print(name + "=");
     Enumeration ev = request.getHeaders(name);
     while (ev.hasMoreElements()) {
       out.print(ev.nextElement());
       if (ev.hasMoreElements())
         out.print(", ");
     }
     out.println();
   }
%>
</pre>

</body>
</html>
