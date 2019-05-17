<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html> 
<body>
  <h2>Etudiant</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th style="text-align:left">Nom</th>
      <th style="text-align:left">Prénom</th>
	  <th style="text-align:left">Adresse</th>
	  <th style="text-align:left">Formation</th>
	  <th style="text-align:left">Statut</th>
    </tr>
    <xsl:for-each select="universites/universite/etablissements/etablissement/etudiants/etudiant">	
    <tr>
      <td><xsl:value-of select="nom"/></td>
      <td><xsl:value-of select="prenom"/></td>
	  <td><xsl:value-of select="concat(adresse/numero, ' ')"/><xsl:value-of select="concat(adresse/voie, ' ')"/> <xsl:value-of select="concat(adresse/codePostal,' ')"/> <xsl:value-of select="adresse/ville"/></td>
	  <td><xsl:value-of select="formation/intitule"/></td>
	  <td><xsl:value-of select="statut"/></td>
    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
