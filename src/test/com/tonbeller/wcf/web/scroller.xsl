<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" indent="yes"/>

<xsl:param name="id"/>

<!-- alles ausser dem gesuchten element ignorieren -->
<xsl:template match="/">
  <test>
    <xsl:apply-templates select="//*[@id=$id]"/>
  </test>
</xsl:template>

<xsl:template match="form/input[@type='hidden']">
  <input name="{@name}" value="{@value}"/>
</xsl:template>

<!-- rest ignorieren -->
<xsl:template match="*|@*|text()">
  <xsl:apply-templates/>
</xsl:template>

</xsl:stylesheet>
