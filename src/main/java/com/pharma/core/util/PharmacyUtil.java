package com.pharma.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lowagie.text.Annotation;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfCopy.PageStamp;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
/**
 * This Util class contains a collection of static functions whose code can be reused again and again by passing the reference of the type as a parameter
 *
 */
public  class PharmacyUtil {
	
	public final static String statusActive = new String("Active");
	public final static String statusInactive = new String("Inactive");
	public final static String statusNew = new String("New");
	public final static String statusNewModified = new String("New Modifications");
	public final static String statusDenied = new String("Denied");
	public final static String statusApproved = new String("Approved");
	public final static String statusProfileCompleted = new String("Profile Completed");
	public final static String statusInfoCompleted = new String("Info Completed");
	
	public final static String userSuperAdmin = new String("Super Admin");
	public final static String userAdmin = new String("Admin");
	public final static String userAdministrator = new String("Administrator");
	
	public final static String userPhysician = new String("Physician");
	public final static String userPhysicianAssistant = new String("Physician Assistant");
	public final static String userPatient = new String("Patient");
	public final static String userGroupDirector = new String("Group Director");
	
	public final static String deleteFlagYes = new String("Y");
	public final static String deleteFlagNo = new String("N");
	
	
	public final static String pagePhysician = new String("Physician");
	public final static String pagePrescription = new String("Prescription");
	public final static String pagePatient = new String("Patient");
	public final static String pageOrder = new String("Order");
	public final static String pageInvoice = new String("Invoice");

	
	
	//Date Related Utility Methods
	
	public static Timestamp getCurrentDateAndTime() {
		Instant timestamp = Instant.now();
		Timestamp ts = new Timestamp(timestamp.toEpochMilli());
		
		return ts;
	}
	
	public static java.sql.Date getSqlDateFromString(String date) {
		SimpleDateFormat inDate = new SimpleDateFormat("MM/dd/yyyy");
		
		java.sql.Date dt = null;
		try {
			dt = new java.sql.Date( inDate.parse(date).getTime() );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dt;
	}
	
	public static String getStringDateFromSqlDate(java.sql.Date date) {
		String strDt = null;
		SimpleDateFormat inDate = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			if (date != null) {
				Date dt = new Date( date.getTime() );
				strDt = inDate.format(dt);	
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return strDt;
	}

	public static Date getIndianTime() {
		Date date = null;
		try {
			String DATE_FORMAT = "MM/dd/yyyy hh:mm:ss";
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
			formatter.setTimeZone(tz);

			String sIndianDate = formatter.format(new Date());
			date = formatter.parse(sIndianDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	// End Date Related Utility Methods 

	// Image uploading 
	public static String getRootFolderForPhoto(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("file.photo_path");
	}
	
	public static String getRootFolderForInstructionManual(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("file.instruction_manual_folder");
	}

	public static String getRootFolderForPrescriptionPDF(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("prescription.pdf.folder.path");
	}
	
	public static String getRootFolderForInvoicePDF(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("invoice.pdf.folder.path");
	}
	
	public static String getRootFolderForOrderPDF(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("order.pdf.folder.path");
	}
	public static String getRootFolderForPhysicianPDF(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("physician.pdf.folder.path");
	}
	public static String getRootFolderForLogo(HttpSession session, Environment env) {
		return session.getServletContext().getRealPath("/") + File.separator + "resources" +  File.separator + env.getProperty("file.logo_path");
	}
	public static boolean userPhotoUpload(CommonsMultipartFile file, String targetFolder, String targetFileName, String rootFilePath, int userId, String docType) {
		boolean isUploadSuccess = false;

		if (!file.isEmpty()) {
			try {
				String path = "";
				if (!"".equalsIgnoreCase(docType))
					path = getFileUploadPath(rootFilePath, targetFolder, userId, docType);
				else
					path = getFileUploadPath(rootFilePath, targetFolder, userId, "");
				
				String oname=file.getOriginalFilename().toLowerCase();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
				String fileName = targetFileName + "."+extension;
				
				byte barr[]=file.getBytes();  
				
				BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(path+File.separator+fileName));  
		        bout.write(barr);
		        bout.flush();
		        bout.close();
		        isUploadSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return isUploadSuccess;
	}
	
	
	// Documents Uploading...
	public static boolean userDocumentUpload(CommonsMultipartFile file, String targetFolder, String targetFileName, String rootFilePath, int userId, String docType) {
		boolean isUploadSuccess = false;

		if (!file.isEmpty()) {
			try {
				String path = getFileUploadPath(rootFilePath, targetFolder, userId, docType);
				
				String oname=file.getOriginalFilename();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
				String fileName = targetFileName + "."+extension;

				byte barr[]=file.getBytes();  
				
				BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(path+File.separator+fileName));  
		        bout.write(barr);
		        bout.flush();
		        bout.close();
		        isUploadSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return isUploadSuccess;
	}

	public static String getFileUploadPath(String rootFilePath, String targetFolder, int userId, String docType) {
		StringBuffer path = new StringBuffer(rootFilePath + File.separator + targetFolder);
		
		File f = new File(rootFilePath);
		if (!f.exists())
			f.mkdir();
		
		f = new File(path.toString());
		if (!f.exists())
			f.mkdir();
		
		path = path.append(File.separator + userId);
		f = new File(path.toString());
		if (!f.exists())
			f.mkdir();
		
		if (!"".equalsIgnoreCase(docType)) {
			path = path.append(File.separator + docType);
			f = new File(path.toString());
			if (!f.exists())
				f.mkdir();
		}
		
		return path.toString();
	}
	
	public static boolean downloadFile(String fileName, String downloadName, HttpServletResponse response) {
		try {
			if (fileName.equalsIgnoreCase("")) {
	            String errorMessage = "Sorry. The file you are looking for does not exist";
	            //System.out.println(errorMessage);
	           /* OutputStream outputStream = response.getOutputStream();
	            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
	            outputStream.close();*/
	            return false;
			}
			
			File file = new File(fileName);
			if(!file.exists()){
				String errorMessage = "Sorry. The file you are looking for does not exist";
	            //System.out.println(errorMessage);
	         /*   OutputStream outputStream = response.getOutputStream();
	            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
	            outputStream.close();*/
	            return false;
	        }
			String mimeType= Files.probeContentType(file.toPath());
		    if (mimeType == null) {
		        mimeType = "application/octet-stream";
		    }
		    response.setContentType(mimeType);
	         
	        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
	            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
	        //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
	         
	        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
	        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", downloadName));
	        response.setContentLength((int)file.length());
	        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 
	        //Copy bytes from source to destination(outputstream in this example), closes both streams.
	        FileCopyUtils.copy(inputStream, response.getOutputStream());
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	 public  static String getElapsedTimeHoursMinutesFromMilliseconds(long startDate, long endDate){
		 
			String formattedTime="";
			try {
				//milliseconds
				long different = endDate - startDate;

				/*//System.out.println("startDate : " + startDate);
				//System.out.println("endDate : "+ endDate);
				//System.out.println("different : " + different);*/

				long secondsInMilli = 1000;
				long minutesInMilli = secondsInMilli * 60;
				long hoursInMilli = minutesInMilli * 60;
				long daysInMilli = hoursInMilli * 24;

				long elapsedDays = different / daysInMilli;
				different = different % daysInMilli;

				long elapsedHours = different / hoursInMilli;
				different = different % hoursInMilli;

				long elapsedMinutes = different / minutesInMilli;
				different = different % minutesInMilli;

				long elapsedSeconds = different / secondsInMilli;

				/*System.out.printf(
				    "%d days, %d hours, %d minutes, %d seconds%n", 
				    elapsedDays,
				    elapsedHours, elapsedMinutes, elapsedSeconds);*/
				if(elapsedDays>0)
				{
					formattedTime=elapsedDays+" days "+elapsedHours+" hrs "+elapsedMinutes+" mins "+elapsedSeconds+" secs";
				}else if(elapsedHours>0)
				{
					formattedTime=elapsedHours+" hrs "+elapsedMinutes+" mins "+elapsedSeconds+" secs";
				}else if(elapsedMinutes>0)
				{
					formattedTime=elapsedMinutes+" mins "+elapsedSeconds+" secs";
				}else if(elapsedSeconds>0)
				{
					formattedTime=elapsedSeconds+" secs";
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return formattedTime;
		}
	public static Map<String,String> getCreditCardYearsList() {
		Map<String,String> yearList = new LinkedHashMap<String,String>();
		
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		for (int year = curYear; year < curYear+15; year++) {
			// yearList.put(year+"", year+"");
			/*lblValue = new LabelValueBean(year+"", year+"");
			yearList.add(lblValue);*/
			
			yearList.put(year+"", year+"");
		}
		return yearList;
	}
	
	public static java.sql.Date getSqlCurrentDateTime() {
		java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		return timeNow;
		
	}
	
	public static java.sql.Timestamp getCurrentTimeStamp() {
		return new java.sql.Timestamp(new Date().getTime());	
	}

	public static Date getPreviousDateByDays(int days) {
		
		Date dateFrom=null;
		try {
			String previousDate="";
			
			Instant now = Instant.now();
			Instant nowMinusDays = now.minus(Duration.ofDays(days));
			dateFrom = Date.from(nowMinusDays);

			LocalDateTime localeDate = LocalDateTime.ofInstant(nowMinusDays, ZoneId.systemDefault());
			previousDate = localeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			//System.out.println("1111111111111 ==="+previousDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return dateFrom;
	}
	
	/*
	 * This method is used to generate random password and it will return password string.
	 */
	public static String randomPasswordGenerator() {
		String static_text = "Temp123456";
		/*Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(9999);		
		int len = String.valueOf(randomInt).trim().length();		
		if(len<4){			
			randomInt= randomInt+1000;
		}		
		return static_text + randomInt;*/
		return static_text;
	}
	
	
	public static int randomNumberGenerator() {
		//String static_text = "Temp123456";
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(9999);		
		int len = String.valueOf(randomInt).trim().length();		
		if(len<4){			
			randomInt= randomInt+1000;
		}		
		return  randomInt;
		
	}
	
	
	/*** Get Current URL for attaching mail links purpose *********/
	public static String getCurrentSystemUrl(HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		return fullUrl.replace(request.getServletPath().toString(), "");
	}
	 /**
     * Merge multiple pdf into one pdf
     * 
     * @param list
     *            of pdf input stream
     * @param outputStream
     *            output file output stream
     * @throws DocumentException
     * @throws IOException
     */
    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        
        outputStream.flush();
        document.close();
        outputStream.close();
    }
	
  /* public static void mergePdfFiles(List<InputStream> inputPdfList,
            OutputStream outputStream) throws Exception{
 
        //Create document and pdfReader objects.
	Document document = new Document(PageSize.A4);
        List<PdfReader> readers = 
        		new ArrayList<PdfReader>();
        int totalPages = 0;
 
        //Create pdf Iterator object using inputPdfList.
        Iterator<InputStream> pdfIterator = 
        		inputPdfList.iterator();
 
        // Create reader list for the input pdf files.
        while (pdfIterator.hasNext()) {
                InputStream pdf = pdfIterator.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages = totalPages + pdfReader.getNumberOfPages();
        }
 
        // Create writer for the outputStream
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
 
        //Open document.
        document.open();
 
        //Contain the pdf data.
        PdfContentByte pageContentByte = writer.getDirectContent();
 
        PdfImportedPage pdfImportedPage;
        int currentPdfReaderPage = 1;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();
 
        // Iterate and process the reader list.
        while (iteratorPDFReader.hasNext()) {
          PdfReader pdfReader = iteratorPDFReader.next();
          //Create page and add content.
          while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
                      document.newPage();
                      pdfImportedPage = writer.getImportedPage(
                    		  pdfReader,currentPdfReaderPage);
                      pageContentByte.addTemplate(pdfImportedPage, 0, 0);
                      currentPdfReaderPage++;
          }
          currentPdfReaderPage = 1;
        }
 
        //Close document and outputStream.
        outputStream.flush();
        document.close();
        outputStream.close();
 
        //System.out.println("Pdf files merged successfully.");
       }*/
    public static void mergePdfFiles(List<InputStream> inputPdfList,
            OutputStream outputStream) throws Exception{
    	Document document = new Document();
    try {
		
		List<PdfReader> readers = 
		  new ArrayList<PdfReader>();
		PdfCopy copy = new PdfCopy(document, outputStream);
		PageStamp stamp;
		document.open();
		int n;
		int pageNo = 0;
		PdfImportedPage page;
		//Chunk chunk;
		int totalPages = 0;
		//Create pdf Iterator object using inputPdfList.
		Iterator<InputStream> pdfIterator = 
		  inputPdfList.iterator();

		// Create reader list for the input pdf files.
		while (pdfIterator.hasNext()) {
		        InputStream pdf = pdfIterator.next();
		        PdfReader pdfReader = new PdfReader(pdf);
		        readers.add(pdfReader);
		        n =  pdfReader.getNumberOfPages();
		
   /* for (Map.Entry<String, PdfReader> entry : filesToMerge.entrySet()) {
		    n = entry.getValue().getNumberOfPages();*/
		    for (int i = 0; i < n; ) {
		        pageNo++;
		        page = copy.getImportedPage(pdfReader, ++i);
		        stamp = copy.createPageStamp(page);
		        /*chunk = new Chunk(String.format("Page %d", pageNo));
		        if (i == 1)
		            chunk.setLocalDestination("p" + pageNo);
		        ColumnText.showTextAligned(stamp.getUnderContent(),
		                Element.ALIGN_RIGHT, new Phrase(chunk),
		                559, 810, 0);*/
		        stamp.alterContents();
		        copy.addPage(page);
		    }
		    
		}
		document.close();
		//reader.close();
   /* for (PdfReader r : filesToMerge.values()) {
		    r.close();
		}*/
	} catch (Exception e) {
		  e.printStackTrace();
	}
    
    }
    public static void ImageToPDF(String input,String output) {
		    Document document = new Document();
		    //String input = "c:/temp/capture.png"; // .gif and .jpg are ok too!
		    //String output = "c:/temp/capture.pdf";
		    try {
		      FileOutputStream fos = new FileOutputStream(output);
		      PdfWriter writer = PdfWriter.getInstance(document, fos);
		      writer.open();
		      document.open();
		      Image image = Image.getInstance(input);
		      //image.setAbsolutePosition(0f, 0f);
		      image.scaleAbsolute(500f,500f);
		      /*image.setAbsolutePosition(
		              (PageSize.POSTCARD.getWidth() - image.getScaledWidth()) / 2,
		              (PageSize.POSTCARD.getHeight() - image.getScaledHeight()) / 2);*/
		      /*image.scaleAbsolute(550, 20);
		      image.setAbsolutePosition(450, 450);
		      image.setAnnotation(new Annotation(0, 0, 0, 0, 3));*/
		      
		      document.add(Image.getInstance(image));
		      document.close();
		      writer.close();
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
    	}
    public static String getCurrentDate() {
    	
    	SimpleDateFormat inDate = new SimpleDateFormat("MM-dd-yyyy");
		String formattedStr="";
		Date dt = null;
		
		try {
			
			dt = new Date();
			formattedStr=inDate.format(dt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return formattedStr;
	}
    
    
    
    
    public static Timestamp getNotificationExpirtyDate(int addDays) {
    	Timestamp ts = null; 
		try {
			Instant now = Instant.now();
			Instant nowPlusDays = now.plus(Duration.ofDays(addDays));
			ts = new Timestamp(nowPlusDays.toEpochMilli());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
    
    public static int getIntAmount(String strAmount) {
		int amount = 0;
		try {
			if (strAmount != null && !strAmount.equals("")) {
			 	 Double dObj = new Double(strAmount);
				 amount =dObj.intValue();
			}
		} catch (Exception err) {
			//logger.info("Exception in getAmount " + err);
			amount = 0;
		}
		return amount;
	}    
}
