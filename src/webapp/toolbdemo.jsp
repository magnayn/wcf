<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Toolbar Test</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
</head>
<body bgcolor=white>

<h2>WCF Toolbar Test</h2>

<jsp:useBean id="toolbean" class="com.tonbeller.wcf.form.TestBean" scope="session" />

<wcf:toolbar id="toolbar01" globalButtonIds="true" bundle="com.tonbeller.wcf.toolbar.resources">
  <wcf:separator/>
  <wcf:scriptbutton id="button1" tooltip="testlabel" img="button" model="#{toolbean.checkBox1}"/>
  <wcf:separator/>
  <wcf:scriptbutton id="button2" img="button" model="#{toolbean.radioButton1}" radioGroup="radio1" visibleRef="#{toolbean.checkBox1}"/>
  <wcf:scriptbutton id="button3" img="button" model="#{toolbean.radioButton2}" radioGroup="radio1" visibleRef="#{toolbean.checkBox1}"/>
  <wcf:separator/>
  <wcf:pushbutton id="button4" img="button" visibleRef="#{toolbean.checkBox1}"/>
  <wcf:imgbutton  id="button5" img="button" href="/some/path" visibleRef="#{toolbean.checkBox1}"/>
  <%-- visible to members of the tomcat role only --%>
  <wcf:pushbutton role="tomcat" id="button6" img="button" tooltip="tomcat"/>
  <%-- visible to everbody except members of the tomcat role --%>
  <wcf:pushbutton role="!tomcat" id="button7" img="button" tooltip="!tomcat"/>
  <wcf:separator/>
</wcf:toolbar>

<form action="toolbdemo.jsp" method="post" id="form01">
  <wcf:render ref="#{toolbar01}" xslUri="/WEB-INF/wcf/htoolbar.xsl" xslCache="true"/>
</form>

<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=toolbar01&amp;iehack=file.xml">show xml</a>
</body>
</html>
