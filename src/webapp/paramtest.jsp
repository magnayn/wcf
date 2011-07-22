<%@ page session="true" contentType="text/html; charset=UTF-8"
   import="com.tonbeller.wcf.param.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
  <title>Parameter Tests</title>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/tree/xtree.css">
</head>
<body bgcolor=white>

<wcf:statusline/>

<h2>ParamTests</h2>

<%
  SessionParamPool pool = SessionParamPool.instance(pageContext);
  if (pool.getParam("P1") == null) {
    SqlAndExpr and = new SqlAndExpr();
    SqlEqualExpr op1 = new SqlEqualExpr();
    op1.setColumnId("op1");
    op1.setSqlValue("a");
    and.getOperands().add(op1);
    SqlEqualExpr op2 = new SqlEqualExpr();
    op2.setColumnId("op2");
    op2.setSqlValue("b");
    and.getOperands().add(op2);
    SessionParam p1 = new SessionParam();
    p1.setName("P1");
    p1.setSqlExpr(and);
    pool.setParam(p1);

    SessionParam p2 = new SessionParam();
    p2.setName("P2");
    p2.setSqlValue(null);
    pool.setParam(p2);
  }
%>

<h3>Change Value of P2</h3>
<wcf:paramLinkGroup id="group01">
  <ul>
    <li><wcf:paramLink paramName="P2" sqlValue="AAA">AAA</wcf:paramLink></li>
    <li><wcf:paramLink paramName="P2" sqlValue="BBB">BBB</wcf:paramLink></li>
    <li><wcf:paramLink paramName="P2" sqlValue="CCC">CCC</wcf:paramLink></li>
  </ul>
  Current value of P2: <c:out value="${paramPool.P2.sqlValue}"/>
</wcf:paramLinkGroup>

<h3>The generated SQL</h3>
<blockquote id="test01">
P1:
<wcf:paramSql param="P1">
  <wcf:paramSqlMapping column="op1" qname="T1.COL1"/>
  <wcf:paramSqlMapping column="op2" qname="T2.COL2"/>
</wcf:paramSql>
<p />
P2: <wcf:paramSql param="P2" qname="T.COL"/>
<p />
pushParam(P2, 123)
<wcf:pushParam paramName="P2" sqlValue="123">
P2: <wcf:paramSql param="P2" qname="T.COL"/>
</wcf:pushParam>
After pushParam
P2: <wcf:paramSql param="P2" qname="T.COL"/>
<p />
setParam(P2, 234)
<wcf:setParam paramName="P2" sqlValue="234" />
P2: <wcf:paramSql param="P2" qname="T.COL"/>
<p />
No Links
<c:set var="hideParam" value="true"/>
<wcf:paramLinkGroup id="group02" hideIf="#{hideParam}">
  <wcf:paramLink paramName="P2" sqlValue="BBB">BBB</wcf:paramLink>
</wcf:paramLinkGroup>
<p />
With links (#{showParam} is undefined):
<c:set var="hideParam" value="true"/>
<wcf:paramLinkGroup id="group02" hideIf="#{showParam}">
  <wcf:paramLink paramName="P2" sqlValue="BBB">BBB</wcf:paramLink>
</wcf:paramLinkGroup>

</blockquote>

<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=tree03&amp;iehack=file.xml">show xml</a>
</body>
</html>
