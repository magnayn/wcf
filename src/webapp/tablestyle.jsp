<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>

<html>

Test class attribute in td/th from application and stylesheet.
<p>

<c:import url="/WEB-INF/tablestyle.xml" var="xml"/>
<c:import url="/WEB-INF/wcf/wcf.xsl" var="xslt"/>

<form id="form01">
  <x:transform xml="${xml}" xslt="${xslt}" xmlSystemId="/WEB-INF/tablestyle.xml" xsltSystemId="/WEB-INF/wcf/wcf.xsl"/>
</form>

</html>
