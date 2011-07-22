<%@ page session="true" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Scroller Test</title>
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/tree/xtree.css">
  <script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF Scroller Test</h2>
Demonstrates how WCF keeps the scroll position of the browser when navigating.

<p>

<jsp:useBean id="scrTabModel" class="com.tonbeller.wcf.table.TestModel" scope="session" />
<jsp:useBean id="scrCatModel" class="com.tonbeller.wcf.catedit.TestCategoryModel" scope="session" />

<wcf:table id="scrTab" model="#{scrTabModel}" editable="false"/>
<wcf:tree id="scrTree"/>
<wcf:catedit id="scrCat" model="#{scrCatModel}" validate="true"/>

<form action="scroller.jsp" method="post" id="form01">
  <wcf:scroller/>

  Click <a href="#top">here</a>.

  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~
  <br>~

  <a name="top"></a>

  <p><hr></p>

  <p>Try expanding, moving or deleting tree nodes</p>
  <wcf:render ref="#{scrTree}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

  <p><hr></p>

  <p>Try sorting table columns. Closing the table will NOT keep the position</p>
  <wcf:render ref="#{scrTab}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

  <p><hr></p>

  <p>Try changing categories</p>
  <wcf:render ref="#{scrCat}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=treeform&amp;iehack=file.xml">show xml</a>
</body>
</html>
