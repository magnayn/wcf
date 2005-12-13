<%@ page
  session="true"
  contentType="text/html; charset=UTF-8"
  import="com.tonbeller.wcf.statusline.*,
          com.tonbeller.wcf.controller.*,
         javax.servlet.*, javax.servlet.http.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/wcf/wcf-tags.tld" prefix="wcf" %>

<html>
<head>
<title>WCF StatusLine</title>
<link rel="stylesheet" type="text/css" href="wcf/status/status.css">
</head>
<body bgcolor="white">

<h2>WCF StatusLine</h2>

<div id="xmlcontent">
<blockquote>
<%
  StatusLine sl = StatusLine.instance(pageContext.getSession());
  sl.setMessage("My Message <b>bold</b> \"a\" 'b' &amp; ");
%>
<wcf:statusline clear="false"/> (non empty)
<br />
<pre><wcf:statusline format="text" clear="true"/> (non empty)</pre>
<br />
<wcf:statusline/> (empty)
done.
<p />

<%
  try {
    throw new RuntimeException("Exception.getMessage returns HTML <h1>Title</h1>");
  }catch (Exception e) {
    sl.setError("Userfriendly Exception Type", e);
  }
%>
<wcf:statusline>
  default text is not visible!
</wcf:statusline>

<br />
<wcf:statusline>
  default text is visible!
</wcf:statusline>
done.

<p/>
<%
  try {
    throw new RuntimeException();
  }catch (Exception e) {
    sl.setError(null, e);
  }
%>
<wcf:statusline/> (non empty)
<br />
<wcf:statusline/> (empty)
done.
</blockquote>
</div>

</body>
</html>
