<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" indent="yes" />

   <!-- Recueração de Password SCMGI -->
    <xsl:template match="ResetPassword">
        <html>
            Exmo(a). Senhor(a),<br/><br/>
            Foi efetuado um pedido de geração de passwod para o seu utilizador.<br/>
            A nova password é:  <xsl:value-of select="password"/> .<br/><br/>
           	Esta mensagem é gerada automaticamente. Por favor, não responda a este e-mail.
			Para mais informações ou esclarecimentos, não hesite em contactar a nossa Linha de Apoio.<br/><br/>
			Com os melhores cumprimentos,<br/><br/>
            UAL - Gestão de Identidades<br/>			
        </html>
    </xsl:template>
	
   	<xsl:template match="ForgotPassword">
        <html>
            Exmo(a). Senhor(a),<br/><br/>
            Foi efetuado um pedido de recuperaão de passwod para o seu utilizador.<br/>
            A sua resposta secreta é:  <xsl:value-of select="hintAnswer"/> .<br/><br/>
           	Esta mensagem é gerada automaticamente. Por favor, não responda a este e-mail.
			Para mais informações ou esclarecimentos, não hesite em contactar a nossa Linha de Apoio.<br/><br/>
			Com os melhores cumprimentos,<br/><br/>
            UAL - Gestão de Identidades<br/>			
        </html>
    </xsl:template>
   	
</xsl:stylesheet>

