<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
<title>evenodd Testpage</title>
</head>
<body bgcolor=white>

<%
  String[] strings = {"a", "b", "c"};
  pageContext.setAttribute("strings", strings);
%>

<blockquote id="list">

<ul>
<c:forEach var="s" items="${strings}">
  <li>
    <c:out value="${s}"/>
    <wcf:evenOdd clazz="clazz"/>
    <wcf:evenOdd even="even" odd="odd"/>
    <wcf:evenOdd />
  </li>
</c:forEach>
</ul>

<p />

<wcf:paramLinkGroup id="id01">
  <wcf:paramLink page="blah.jsp" paramName="KUNDE_ID" sqlValue="abc">
    Hier bitte ein Link
  </wcf:paramLink>
</wcf:paramLinkGroup>

Ein Sort Button: <wcf:sqlTable id="t1" orderBy="A">
  <wcf:sqlTableSortButton column="B"/>
</wcf:sqlTable>
<p />

Not in Excel Mode:
<wcf:renderParam test="mode" value="excel">
  Error
</wcf:renderParam>
<wcf:renderParam test="mode" value="!excel">
  OK.
</wcf:renderParam>

<p />

<wcf:renderParam name="mode" value="excel" scope="request"/>

<wcf:paramLinkGroup id="id02">
  <wcf:paramLink page="blah.jsp" paramName="KUNDE_ID" sqlValue="abc">
    Hier bitte kein Link
  </wcf:paramLink>
</wcf:paramLinkGroup>

Kein Sort Button: <wcf:sqlTable id="t2" orderBy="A">
  <wcf:sqlTableSortButton column="B"/>
</wcf:sqlTable>

<p />
We are in Excel Mode:
<wcf:renderParam test="mode" value="excel">
  OK.
</wcf:renderParam>
<wcf:renderParam test="mode" value="!excel">
  Error
</wcf:renderParam>

</blockquote>

</body>
</html>
