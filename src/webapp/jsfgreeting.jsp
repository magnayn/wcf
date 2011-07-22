<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib prefix="wcf" uri="http://www.tonbeller.com/wcf" %>

<HTML>
  <HEAD>
    <title>Hello</title>
    <link rel="stylesheet" type="text/css" href="../wcf/form/xform.css" />
    <link rel="stylesheet" type="text/css" href="../wcf/table/xtable.css" />
  </HEAD>
  <body bgcolor="white">
    <f:view>
      <h:form id="form01" >
        <h3>Hi. My name is Duke.  I'm thinking of a number from
        <h:outputText value="#{UserNumberBean.minimum}"/> to
        <h:outputText value="#{UserNumberBean.maximum}"/>.  Can you guess
        it?</h3>

        <h:graphicImage id="waveImg" url="/wave.med.gif" />
        <h:inputText id="userNo" value="#{UserNumberBean.userNumber}"
                    validator="#{UserNumberBean.validate}"/>          
        <h:commandButton id="submit" action="success" value="Submit" />
        <p>
        <h:message style="color: red; font-family: 'New Century Schoolbook', serif; font-style: oblique; text-decoration: overline" id="errors1" for="userNo"/>


        <p/>
        <h3>WCF Form inside JSF Application</h3>
        <wcf:form id="jsfFormComp" validate="true" xmlUri="/WEB-INF/jsfform.xml" model="#{jsfFormBean}"/>
        <wcf:render ref="#{jsfFormComp}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

        <p/>
        <h3>WCF Table inside a JSF Application</h3>
        <wcf:table id="jsfTableComp" validate="true" model="#{jsfTableModel}" editForm="/WEB-INF/wcf/tableproperties.xml" closable="false"/>
        <wcf:render ref="#{jsfTableComp}" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

      </h:form>
    </f:view>
    <p><a href="../index.jsp">back to index</a>
    <br><a href="../../wcf-war/src/main/webapp/wcf/showxml.jsp?render=jsfFormComp&amp;iehack=file.xml">show xml</a>
  </body>
</HTML>  
