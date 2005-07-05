<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page session="true" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>
<c:set var="pageBody" value="charenc.jsp"/>
<c:import url="${pageBody}" charEncoding="${pageContext.response.characterEncoding}"/>
