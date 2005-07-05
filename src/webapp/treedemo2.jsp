<%@ page session="true" contentType="text/html; charset=UTF-8"
   import="com.tonbeller.wcf.tree.*, com.tonbeller.wcf.selection.*"%>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>WCF Treenode Grouping Test</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">
<script src="wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>
</head>
<body bgcolor=white>

<h2>WCF Treenode Grouping Test</h2>

The Tree inserts intermediate levels into the tree model hierarchy to prevent the
user from opening very large amount of children. Elements from
intermediate levels are not selectable by default because the do not contain
application specific node types.

<form action="treedemo2.jsp" method="post" id="form01">

<wcf:scroller/>
<%
  LabelProvider lp = new DefaultLabelProvider();
  TreeModel tm = new TestTreeModel(new int[]{20, 19});
  tm = new GroupingTreeModelDecorator(lp, tm, 5);
  pageContext.setAttribute("tree02model", tm, PageContext.SESSION_SCOPE);
%>
<wcf:tree id="tree02" model="tree02model"/>

<wcf:render ref="#{tree02}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<p><a href="index.jsp">back to index</a>
<br><a href="wcf/showxml.jsp?render=tree02&amp;iehack=file.xml">show xml</a>
</body>
</html>
