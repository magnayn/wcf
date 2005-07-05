<%@ page session="true" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>PageStack Test</title>
  <link rel="stylesheet" type="text/css" href="wcf/wcf.css">
</head>

<body bgcolor="white" id="body">
<fmt:bundle basename="messages">

<h2>PageStack Test</h2>

<div class="pagestack">
<wcf:pageStack var="p" clear="true">
  [<a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>


<h4>Adding Page 1</h4>


<div class="pagestack">
<wcf:pageStack var="p" page="page1.jsp" title="Page 1">
  [<a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>


<h4>Adding Page 2</h4>

<div class="pagestack">
You are here
<wcf:pageStack var="p" page="page2.jsp" title="Page 2">
  :: <a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>
</wcf:pageStack>
</div>

<h4>Add Page 3</h4>
<div class="pagestack">
You are here
<wcf:pageStack var="p" varStatus="status" pageId="someID" page="page3.jsp" title="Page 3">
 :: <a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>
</wcf:pageStack>
</div>
<p>
Replace Page 3 with Page 3A
<p>
<div class="pagestack">
You are here
<wcf:pageStack var="p" varStatus="status" pageId="someID" page="page3a.jsp" title="Page 3A">
 :: <a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>
</wcf:pageStack>
</div>


<h4>Back to Page 2</h4>

<div class="pagestack">
<wcf:pageStack var="p" page="page2.jsp" title="Page 2">
  [<a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>

<h4>Back to Page 1</h4>

<div class="pagestack">
<wcf:pageStack var="p" page="page1.jsp" title="Page 1">
  [<a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>

<h4>Title from JSTL Resource Bundle</h4>
Expecting: <fmt:message key="message.key"/>
<p>
<div class="pagestack">
<wcf:pageStack var="p" page="page1.jsp" key="message.key">
  [<a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>

<h4>bundle</h4>
<div class="pagestack">
<wcf:pageStack var="p" clear="true" page="page1.jsp" key="test.message" bundle="com.tonbeller.wcf.test.resources">
  [<a href="<c:out value='${p.page}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>

<h4>Use Token</h4>
<div class="pagestack">
<wcf:pageStack clear="true" page="token1.jsp" title="Token 1" />
<wcf:pageStack page="token2.jsp?param=value" title="Token 2" />
<wcf:pageStack page="token3.jsp?p1=v1&p2=v2" title="Token 3" />
<wcf:pageStack var="p">
  [<a href="<c:out value='${p.pageHref}'/>"><c:out value="${p.title}"/></a>]
</wcf:pageStack>
</div>


</fmt:bundle>
</body>
</html>
