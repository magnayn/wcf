<%@ page session="true" contentType="text/html; charset=UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Tabbed</title>
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <link rel="stylesheet" type="text/css" href="wcf/tabbed/xtabbed.css">
</head>
<body bgcolor=white>
  <f:view>
    <h:form id="form01">
      <h2>WCF Tabbed</h2>
      <wcf:form id="tabbedForm" xmlUri="/WEB-INF/tabbed.xml" model="#{jsfTestBean}"/>
      <wcf:render ref="#{tabbedForm}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="false"/>
      <p />
      <!-- does not strip of the /faces prefix -->
      <!-- h:commandButton id="index" action="index" value="back to index"/-->
      <a href="..">back to index</a>
    </h:form>
  </f:view>
<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=tabbedForm&amp;iehack=file.xml">show xml</a>
</body>
</html>
