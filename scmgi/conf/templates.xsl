<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" indent="yes" />

   <!-- Recuera��o de Password SCMGI -->
    <xsl:template match="ResetPassword">
        <html>
            Exmo(a). Senhor(a),<br/><br/>
            Foi efetuado um pedido de gera��o de passwod para o seu utilizador.<br/>
            A nova password �:  <xsl:value-of select="password"/> .<br/><br/>
           	Esta mensagem � gerada automaticamente. Por favor, n�o responda a este e-mail.
			Para mais informa��es ou esclarecimentos, n�o hesite em contactar a nossa Linha de Apoio.<br/><br/>
			Com os melhores cumprimentos,<br/><br/>
            UAL - Gest�o de Identidades<br/>			
        </html>
    </xsl:template>
	
   	<xsl:template match="ForgotPassword">
        <html>
            Exmo(a). Senhor(a),<br/><br/>
            Foi efetuado um pedido de recupera�o de passwod para o seu utilizador.<br/>
            A sua resposta secreta �:  <xsl:value-of select="hintAnswer"/> .<br/><br/>
           	Esta mensagem � gerada automaticamente. Por favor, n�o responda a este e-mail.
			Para mais informa��es ou esclarecimentos, n�o hesite em contactar a nossa Linha de Apoio.<br/><br/>
			Com os melhores cumprimentos,<br/><br/>
            UAL - Gest�o de Identidades<br/>			
        </html>
    </xsl:template>
   	
</xsl:stylesheet>

