package com.pharma.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
/**
 * This Util class contains a collection of functions whose code is used to generate and print the report in the format such as  PDF, HTML, XLS, RTF, etc.
 *
 */
public class JasperReportUtil {

	/**
	 * This method downloads the report in the excel format
	 * @param request
	 * @param response
	 * @param reportPath
	 * @param outputFileName
	 * @param jrDataSource
	 * @param map
	 * @throws JRException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void getDownloadExcelReportWithDatasource(HttpServletRequest request, HttpServletResponse response, String reportPath, 
			String outputFileName, JRDataSource jrDataSource, Map map)  throws JRException, IOException {

		// uncomment this codes if u are want to use servlet output stream
		// ServletOutputStream servletOutputStream = response.getOutputStream();

		// get real path for report
		//String path = request.getSession().getServletContext().getRealPath("\\JASP-RPT\\reports\\"+reportPath+".jrxml");
		
		//  String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\reports\\xxx.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, jrDataSource);

		JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		//xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "xxx.xlsx");
		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName+".xlsx");

		// uncomment this codes if u are want to use servlet output stream
		// xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);

		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
		xlsxExporter.exportReport();

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		//response.setHeader("Content-Disposition", "attachment; filename=xxx.xlsx");
		response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

		// uncomment this codes if u are want to use servlet output stream
		// servletOutputStream.write(os.toByteArray());

		response.getOutputStream().write(os.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		response.flushBuffer();
	}
	/**
	 * This method downloads the report in the excel format
	 * @param request
	 * @param response
	 * @param reportPath
	 * @param outputFileName
	 * @param map
	 * @throws JRException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void getDownloadExcelReportWithDatabase(HttpServletRequest request, HttpServletResponse response, String reportPath, 
			String outputFileName, Map map) throws JRException, IOException {

		// uncomment this codes if u are want to use servlet output stream
		// ServletOutputStream servletOutputStream = response.getOutputStream();
		Connection con;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// "jdbc:mysql://localhost:3306/pharmacyportal?autoReconnect=true&amp;createDatabaseIfNotExist=true&amp;useSSL=false&amp;zeroDateTimeBehavior=convertToNull>
			
			// Class.forName("org.gjt.mm.mysql.Driver");
			String dbUrl = "jdbc:mysql://pharmacyportal.mysql.database.azure.com:3306/pharmacyportal?useSSL=true&amp;requireSSL=false";
			String userName = "admin_cre8";
			String pass = "pharmacy@2021";
			con = DriverManager.getConnection(dbUrl, userName, pass);
		
			// con = getDatasourceConn();
			// get real path for report
			//String path = request.getSession().getServletContext().getRealPath("\\JASP-RPT\\reports\\"+reportPath+".jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

			JRXlsxExporter xlsxExporter = new JRXlsxExporter();
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			
			
			xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			//xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "xxx.xlsx");
			xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName+".xlsx");

			// uncomment this codes if u are want to use servlet output stream
			// xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);

			xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
			xlsxExporter.exportReport();

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			//response.setHeader("Content-Disposition", "attachment; filename=xxx.xlsx");
			response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

			// uncomment this codes if u are want to use servlet output stream
			// servletOutputStream.write(os.toByteArray());

			response.getOutputStream().write(os.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		
	}
	
	
	
	
	/**
	 * This method downloads the report in the excel format
	 * @param report
	 * @param param
	 * @param session
	 * @param reportName
	 * @return
	 */
	public static int runJasperExcelReport(String report, Map param, HttpSession session, String reportName) {
		int totpages = 0;
		try {
			//System.out.println("run jasper XLS report started........");

			JasperPrint jasperPrint = fillReport(report, param, session);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String realpath = session.getServletContext().getRealPath("/");
			
			int lastslashindex = report.lastIndexOf("\\");
			int lastdot = report.lastIndexOf(".");

			File f = new File(realpath + reportName + ".xls");
			OutputStream out = new FileOutputStream(f);

			List jlist = jasperPrint.getPages();
			totpages = jlist.size();

			JRXlsExporter exporterXLS = new JRXlsExporter();

			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
			exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_X, 0);
			exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_Y, 0);
			exporterXLS.setParameter(JRXlsExporterParameter.START_PAGE_INDEX, 0);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, false);
			exporterXLS.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 65000);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, false);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, false);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);

			exporterXLS.exportReport();
			out.write(outputStream.toByteArray());

			//System.out.println("output file written ......");

			byte[] buf2 = new byte[(int) f.length()];

			//System.out.println("run jasper report ends............. exporting....");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return totpages;

	}

	
	
	/**
	 * This method fills the jasper report with data
	 * @param report
	 * @param param
	 * @param sessionhttp
	 * @return
	 */
	public static JasperPrint fillReport(String report, Map param, HttpSession sessionhttp) {
		//System.out.println("fill report started......");
		Connection con = null;
		JasperPrint jp = null;
		try {
			JasperReport jr = null;
			jr = (JasperReport) JRLoader.loadObject(new File(report));

			// create new HashMap to send to JasperReports in order to fix serialization problems
			Map jasperReportMap = null;
			if (param != null)
				jasperReportMap = new HashMap(param);

			/*con = JDBCConn.getDatasourceConn();
			if (con == null || con.isClosed()) {
				con = JDBCConn.getDatasourceConn();
			}
			if (con != null) {
				jp = JasperFillManager.fillReport(jr, jasperReportMap, con);
			}
			JDBCConn.closeDatasourceConn(con);*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("fill report endssss.........  returning jasper print");
		return jp;
	}
	
	
}
