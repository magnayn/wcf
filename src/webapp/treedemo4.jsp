<%@ page session="true" contentType="text/html; charset=UTF-8"
   import="com.tonbeller.wcf.tree.*, 
           com.tonbeller.wcf.selection.*, 
           java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<html>
<head>
<title>Bounded Tree Model</title>
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/form/xform.css">
<link rel="stylesheet" type="text/css" href="../../wcf-war/src/main/webapp/wcf/tree/xtree.css">

<script src="../../wcf-war/src/main/webapp/wcf/scroller.js" language="JavaScript1.2" type="text/javascript"></script>

</head>
<body bgcolor=white>

<h2>Bounded Tree Model</h2>

<form action="treedemo4.jsp" method="post" id="form01">

<wcf:scroller/>

<%! 
  List visible;
  EnumBoundedTreeModelDecorator boundedTreeModel;
  MutableTreeModelDecorator changeOrderTreeModel;
%>

<c:if test="${param['init'] != null}">
<h2>initializing ...</h2>
<%
  TreeModel treeModel = new TestTreeModel(new int[]{2, 10, 10});
  visible = new ArrayList();
  Object B = treeModel.getRoots()[1];
  for (int i = 1; i < 6; i++) {
    Object Bi = treeModel.getChildren(B)[i];
    visible.add(treeModel.getChildren(Bi)[3]);
    visible.add(treeModel.getChildren(Bi)[4]);
    visible.add(treeModel.getChildren(Bi)[5]);
  }
  System.out.println("Tree prepared");
  treeModel = new CachingTreeModelDecorator(treeModel);
  boundedTreeModel = new EnumBoundedTreeModelDecorator(treeModel);
  boundedTreeModel.setVisible(visible);

  DefaultLabelProvider labelProvider = new DefaultLabelProvider();
  GroupingTreeModelDecorator groupingTreeModel = new GroupingTreeModelDecorator(labelProvider, boundedTreeModel, 4);
  changeOrderTreeModel = new MutableTreeModelDecorator(groupingTreeModel);
  pageContext.setAttribute("tree04model", changeOrderTreeModel, PageContext.SESSION_SCOPE);
  pageContext.removeAttribute("tree04");
%>
</c:if>

<wcf:tree id="tree04" model="tree04model"/>

<c:if test="${param['init'] != null}">
  <c:set target="${tree04}" property="cutPasteMode" value="true"/>
  <% 
    TreeComponent tc = (TreeComponent)pageContext.findAttribute("tree04");
    tc.getSelectionModel().clear();
    tc.getSelectionModel().addAll(visible);
    //tc.setChangeOrderModel(changeOrderTreeModel);
    tc.setBounding(boundedTreeModel);
    tc.expandSelected(false);
  %>
</c:if>

<wcf:render ref="#{tree04}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</form>

<p><a href="index.jsp">back to index</a>
<br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=tree04&amp;iehack=file.xml">show xml</a>
</body>
</html>
