import java.io.ByteArrayOutputStream;

import org.apache.xmlbeans.XmlOptions;

import es.caib.sistra2.commons.xml.pago.v1.model.CONFIRMACIONPAGO;
import es.caib.sistra2.commons.xml.pago.v1.model.CONTRIBUYENTE;
import es.caib.sistra2.commons.xml.pago.v1.model.DATOSPAGO;
import es.caib.sistra2.commons.xml.pago.v1.model.PAGO;
import es.caib.sistra2.commons.xml.pago.v1.model.PAGODocument;

public class TestPago {

    public static void main(String[] args) throws Exception {

        final PAGODocument fd = PAGODocument.Factory.newInstance();
        final PAGO pago = fd.addNewPAGO();

        pago.setIDENTIFICADOR("identificador");

        final DATOSPAGO datosPago = pago.addNewDATOSPAGO();
        datosPago.setENTIDADID("E1");
        datosPago.setPASARELAID("ATIB");
        datosPago.setSIMULADO(true);
        datosPago.setIDIOMA("es");
        datosPago.setMODELO("modelo");
        datosPago.setCONCEPTO("concepto");
        datosPago.setIMPORTE(1000);
        datosPago.setTASA("tasa");
        datosPago.setDETALLEPAGO("detallepago");
        final CONTRIBUYENTE contribuyente = datosPago.addNewCONTRIBUYENTE();
        contribuyente.setNIF("11111111H");
        contribuyente.setNOMBRE("Jose Garcia Gutierrez");
        datosPago.setCONTRIBUYENTE(contribuyente);
        datosPago.setORGANISMO("organismo");

        final CONFIRMACIONPAGO confirmacionPago = pago.addNewCONFIRMACIONPAGO();
        confirmacionPago.setFECHAPAGO("20181022110122");
        confirmacionPago.setLOCALIZADOR("1234");

        byte[] xml = null;
        final XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setCharacterEncoding("UTF-8");
        xmlOptions.setSavePrettyPrint();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
        fd.save(baos, xmlOptions);
        xml = baos.toByteArray();
        baos.close();
        System.out.println("XML GENERADO");
        final String xmlStr = new String(xml, "UTF-8");
        System.out.println(xmlStr);
        System.out.println("-------------------------------");

        System.out.println("XML LEIDO");
        final PAGODocument doc = PAGODocument.Factory.parse(xmlStr);
        final ByteArrayOutputStream baos2 = new ByteArrayOutputStream(500);
        doc.save(baos2);
        System.out.println(new String(baos2.toByteArray(), "UTF-8"));
        baos2.close();

    }

}
