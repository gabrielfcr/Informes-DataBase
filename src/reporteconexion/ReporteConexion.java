/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporteconexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Gabriel
 */
public class ReporteConexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //1 CONEXION A LA BASE DE DATOS
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/informesdi", "root", "root");
            System.out.println("Funciona conexion");
            //2 CARGAR EL ARCHIVO JASPER GENERADO USAMOS UN "JLOADER" = (reporte)---->("sesssion.jasper")
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("sesion1.jasper");
            //3 LLENAR EL reporte CON LOS DATOS QUE OBTENDRA DE LA BASE DE DATOS  "JASFILLMANAGER" = (jasperPrint) ----->(reporte, ?????, conexionBD)
            //(lo obtenido es la representacion de nuestro reporte "CON DATOS" ----IMPRIMIR--- O ----MOSTRAR POR PANTALLA----)
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);

            /* SE LE PUEDE PASAR EL OBJETO  jasperPrint A UN OBJETO SWINT
             (JasperViewer viewer = new JasperViewer(jasperPrint);
             this.panel .add(viewer.getContentPane());
             * ES RECOMENDABLE USAR QUE SEA JFRAME
             */
            //4 GUARDAR ESTE REPORTE A UN ARCHIVO DEBEMOS  USAR "JREXPORTER"   
            //PDF
            //4.1 GUARDAMOS EN PDF CREANDO UN OBJETO "JRPdfExporter" Y LO ASIGNAMOS A NUESTRA REFERNECIA  "exporter"
            JRExporter exporter =  new JRPdfExporter();
            //4.2 ASIGNA NUESTRO REPORTE (exporter) ---------(JASPER_PRINT, jasperPrint)
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //4.3 CUAL SERA EL NOMBRE DEL ARCHIVO GENERADO
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reportePDF.pdf"));
            //4.4 REALIZA EL PROCESO DE EXPORTACION
            exporter.exportReport();
            System.out.println("todo finalizo correctamente");
            
//            //HTML
//            JRExporter exporter;
//            exporter =  new JRHtmlExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, japerPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reporteHTML.html"));
//            exporter.exportReport();
//            System.out.println("todo finalizo correctamente");
            
//            //TXT
//            JRExporter exporter;
//            exporter =  new JRTextExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, japerPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reportePDF.txt"));
//            exporter.exportReport();
//            System.out.println("todo finalizo correctamente");
            

        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }

}
