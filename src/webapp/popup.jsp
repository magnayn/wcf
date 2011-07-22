<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
  <title>PopupMenu</title>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/wcf.css">
  <script type="text/javascript" src="../../wcf-war/src/main/webapp/wcf/popup/popup.js">
/***********************************************
* Chrome CSS Drop Down Menu- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/
  </script>
</head>
<body bgcolor=white>

<jsp:useBean id="popup" class="com.tonbeller.wcf.popup.TestBean" scope="session" />

<h2>PopupMenu</h2>

<form action="popup.jsp" method="post" id="form01">
  <blockquote id="testme">
    Selected: <c:out value="${popup.lastItem}"/>
    <p />
    PopUp 0: <wcf:render ref="#{popup.popUp0}" xslUri="/WEB-INF/wcf/wcf.xsl"/>
  </blockquote>
  PopUp 1a: <wcf:render ref="#{popup.popUp1a}" xslUri="/WEB-INF/wcf/wcf.xsl"/> <br />
  PopUp 1b: <wcf:render ref="#{popup.popUp1b}" xslUri="/WEB-INF/wcf/wcf.xsl"/> <br />
  PopUp 2: <wcf:render ref="#{popup.popUp2}" xslUri="/WEB-INF/wcf/wcf.xsl"/> <br />
</form>

<p><a href="index.jsp">back to index</a>
</body>
</html>
