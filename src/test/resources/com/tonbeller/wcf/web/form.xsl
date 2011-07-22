<?xml version="1.0" encoding="ISO-8859-1"?>

<!--  dieses filter ist nicht ganz so großzügig wie das filter.xsl aus tbutils -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" indent="yes"/>

<xsl:param name="id"/>

<!-- alles ausser dem gesuchten element ignorieren -->
<xsl:template match="/">
  <xsl:apply-templates select="//*[@id=$id]"/>
</xsl:template>

<!-- wcf:scroller -->
<xsl:template match="script[@src]"/>
<xsl:template match="input[@name='wcfXCoord']"/>
<xsl:template match="input[@name='wcfYCoord']"/>

<!-- id, name, href attribute ignorieren -->
<xsl:template match="@id"/>
<xsl:template match="@name"/>
<xsl:template match="@target"/>
<xsl:template match="option/@value"/>
<xsl:template match="input[@title='please enter a date/time']"/>
<xsl:template match="input[@title='please enter a date']"/>

<!-- jfreechart generiert geringfuegig verschiedene image maps unter windows und linux -->
<xsl:template match="@coords"/>
<!-- jfreechart servlet generiert auch random ids -->
<xsl:template match="img/@usemap[contains(., 'jfreechart')]"/>
<xsl:template match="img/@src[contains(., 'jfreechart')]"/>

<!-- rest kopieren -->
<xsl:template match="*|@*|text()">
  <xsl:copy>
    <xsl:apply-templates select="*|@*|text()"/>
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>
