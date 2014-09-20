Para crear un DTE en base a orden de compra en prestashop en www.dsoft.cl

sh run.sh ShopInvoice plantilla_documento.xml cert_dsoft_2014.pfx bl52b3rd

Crea tambien PDF.

Para validar documento:

sh run.sh VerifyInvoice dte_33_105.xml
./../lib/xmlsec-1.4.3.jar:./../lib/xmlgraphics-commons-1.3.jar:./../lib/xml-apis-ext-1.3.04.jar:./../lib/xbean.jar:./../lib/xalan-2.7.0.jar:./../lib/serializer-2.7.0.jar:./../lib/not-yet-commons-ssl-0.3.11.jar:./../lib/log4j-1.2.17.jar:./../lib/jsr173_1.0_api.jar:./../lib/jargs.jar:./../lib/ini4j-0.5.2-SNAPSHOT.jar:./../lib/httpmime-4.0.jar:./../lib/httpcore-4.0.1.jar:./../lib/httpclient-4.0.jar:./../lib/fop.jar:./../lib/commons-logging-1.1.1.jar:./../lib/commons-lang3-3.1.jar:./../lib/commons-io-1.4.jar:./../lib/batik-all-1.7.jar:./../lib/barcode4j-fop-ext-complete.jar:./../lib/avalon-framework-4.2.0.jar:./../lib/apache-mime4j-0.6.jar:./../lib/OpenLibsDte.jar:/opt/apache-log4j-1.2.17/log4j-1.2.17.jar:/opt/ini4j-0.5.2-SNAPSHOT/ini4j-0.5.2-SNAPSHOT.jar:/opt/apache-log4j-1.2.17/log4j-1.2.17.jar:/opt/mysql-connector-java-5.1.22/mysql-connector-java-5.1.22-bin.jar:/opt/slf4j-1.7.5/slf4j-api-1.7.5.jar:/opt/slf4j-1.7.5/slf4j-simple-1.7.5.jar:/opt/guava-15.0.jar:/opt/commons-lang3-3.1/commons-lang3-3.1.jar:./../bin/StoreInvoice.jar
Documento: Timbre OK
Documento: Estructura XML Incorrecta: Estructura XML Incorrecta, causa: Expected element 'Emisor@http://www.sii.cl/SiiDte' instead of 'IdDoc@http://www.sii.cl/SiiDte' here in element Encabezado@http://www.sii.cl/SiiDte
Documento: Firma XML Incorrecta: El certificado no es valido para la fecha de generacion del documento XML. Fecha generacion "Sep 19, 2014", validez del certificado desde "Apr 25, 2013", hasta "Apr 26, 2014".
lfhernandez@ptt-studio-1558:~/workspace/java/ShopInvoice/scripts$ 

Pare generar EnvioDTE


