<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<html>
<head>
<title>WCF Tests</title>
<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
</head>
<body bgcolor="white" id="index">

  <h2>WCF Tests</h2>

  <dl>
    <dt><a href="catdemo.jsp">catdemo</a></dt>
    <dd>Category Editor Component - allows to assign items to categories
    and optionally change order of the items of a category</dd>

    <dt><a href="confirmdemo.jsp">confirmdemo</a></dt>
    <dd>Simple Test of the Confirm Component - does not do much</dd>

    <dt><a href="formdemo.jsp">formdemo</a></dt>
    <dd>demonstrates a HTML form. See the corresponding /WEB-INF/formdemo.xml to
       see
      <ul>
        <li>how input elements are connected to properties of a java bean</li>
        <li>how elements may be controlled by roles</li>
        <li>how buttons are connected to methods of the java bean</li>
      </ul>
    </dd>

    <dt><a href="multicolumn.jsp">multicolumn</a></dt>
    <dd>test for multi column form layout and required input fields
    </dd>

    <!--
    <dt><a href="jsfgreeting.facse">JSF Integration</a></dt>
    <dd>Shows a simple WCF Form and a WCF Table inside the "guess Number"
        JSF example. The WCF components have set their <tt>validate=true</tt>
        attribute to ensure, that user input is read when a JSF form is submitted.
        </dd>
    -->

    <dt><a href="logdemo.jsp">logdemo</a></dt>
    <dd>Simple, reuseable GUI for Apache log4j</dd>

    <dt><a href="roledemo.jsp">roledemo</a></dt>
    <dd>Shows or hides content depending on the users roles</dd>

    <!--
    <dt><a href="jsftabbed.faces">Tabbed Demo</a></dt>
    <dd>A JSF page showing an advanced form containing a tree, a tabbed pane and a table</dd>
    -->

    <dt><a href="tabledemo.jsp">tabledemo</a></dt>
    <dd>Testpage for the WCF table component</dd>

    <dt><a href="tableform.jsp">tableform</a></dt>
    <dd>A WCF table component inside a form</dd>

    <dt><a href="token1.jsp">Token Filter</a></dt>
    <dd>exercises the &lt;wcf:token&gt; tag which deals with browser history 'back' button</dd>

    <dt><a href="tomcat5.jspx">Tomcat 5 Scripting</a></dt>
    <dd>This JSP is written in XML and makes use of the new page directive
        <tt>isELIgnored</tt>. It demonstrates that WCF runs in a
        container with JSP 2.0 Expression Language enabled</dd>

    <dt><a href="toolbdemo.jsp">Tool Bar Test</a></dt>
    <dd>Testpage for Toolbar</dd>

    <dt><a href="treedemo.jsp">Tree Component</a></dt>
    <dd>Testpage for WCF Tree Component. See
      <a href="treedemo2.jsp">Tree Node Grouping Test</a> for
      intermediate levels to prevent the user from opening
      too large amount of children.
      See also <a href="treedemo3.jsp?init=true">Move Tree Nodes via Cut/Paste</a>.
    </dd>

    <dt><a href="treeform.jsp">Tree inside a form</a></dt>
    <dd>Testpage for a Tree component inside a Form component</dd>

    <dt>i18n test</dt>
    <dd>Set the locale from application:
      <a href="i18ndemo.jsp?lang=en">english</a>
      <a href="i18ndemo.jsp?lang=de">german</a>
    </dd>

    <dt><a href="statusline.jsp">status line</a></dt>
    <dd>Shows a message and an exception</dd>

    <dt><a href="busydemo.jsp">Busy Demo</a></dt>
    <dd>Redirects to a "busy" page if long running requests are interrupted</dd>

    <dt><a href="pagestack.jsp">PageStack</a></dt>
    <dd>Shows the user which page he is currently viewing</dd>

    <dt><a href="wizard.jsp">Wizard</a></dt>
    <dd>Wizard Demo</dd>

    <dt><a href="scroller.jsp">Scroller</a></dt>
    <dd>Scroller Demo</dd>

    <dt><a href="uploaddemo.jsp">uploaddemo</a></dt>
    <dd>Upload File Demo</dd>

    <dt><a href="paramtest.jsp">SQL Parameter Test</a></dt>
    <dd>SQL Parameter Test</dd>
    <dt><a href="sqltable.jsp">SQL Table related Tags + Tests</a></dt>
    <dd>Excel/PDF Render Modes</dd>

    <dt><a href="charenc.jsp">character encodings</a></dt>
    <dd>Test character encodings - requires CharacterEncodingFilter setup in web.xml</dd>

</body>
</html>
