package com.pharma.core.pharmaservices.manual;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.manual.InstructionManualForm;
import com.pharma.core.formBean.manual.ManualJSonObj;
import com.pharma.core.model.manual.InstructionManual;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.manual.InstructionManualRepository;
/**
 * This is an implementation class that loads and stores the instruction manual data
 */
@Service
public class InstructionManualServiceImpl implements InstructionManualService {

	@Autowired
	InstructionManualRepository manualRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	public int getLatestDisplayOrder() {
		int max = 0;
		try {
			max = manualRepo.getMaxDisplayOrder();	
		} catch(Exception e){}
		return max;
	}

	public boolean saveDocumentFiles(CommonsMultipartFile docfile, CommonsMultipartFile Imagefile, InstructionManualForm form, 
				String rootFilePath, LoginForm loggedInUser) {
		boolean isSaved = false;
		InstructionManual fileRec = null;
		
		if (form.getFileId() >0)
			fileRec = manualRepo.findOne(form.getFileId());
		else
			fileRec = new InstructionManual();

		fileRec.setFileTitle(form.getDocumentTitle());
		String extension = "";
		String thumbExt = "";
		// thumb image data
		if (Imagefile.getSize() > 0) {
			String othumbName=Imagefile.getOriginalFilename();
			thumbExt = othumbName.substring(othumbName.lastIndexOf('.') + 1, othumbName.length());

			fileRec.setOriginalThumbFileName(othumbName);
			fileRec.setThumbFileExtension(thumbExt);
			fileRec.setStoredThumbFielName("to be update");
		}

		if (docfile.getSize() > 0) {
			// document file data
			String oname=docfile.getOriginalFilename();
			extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
			
			fileRec.setOriginalFileName(oname);
			fileRec.setFileExtension(extension);
			fileRec.setStoredFielName("to be update");  //Temporary data.. will update later (because this field is mandatory in database table)
		}

		fileRec.setFileDescription("");
		fileRec.setDisplayOrder(form.getDisplayOrder());
		fileRec.setAdminId(loggedInUser.getUserid());
		fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			
		InstructionManual acc = manualRepo.save(fileRec);
		isSaved=true;
			
		// upload
		if (isSaved) {
			if (Imagefile.getSize() > 0)
				fileUpload(rootFilePath, acc.getId(), thumbExt, "image", Imagefile);
			if (docfile.getSize() > 0)
				fileUpload(rootFilePath, acc.getId(), extension, "document", docfile );
			
			// reorder existing display orders
			reorderExisitngRecords(fileRec.getDisplayOrder());
		}
			
		return isSaved;
	}
	
	
	private void fileUpload(String rootFilePath, int recId, String extension, String docType, CommonsMultipartFile docfile) {
		try {
			String fileName = "manual_"+recId +"."+extension;
			
			File f = new File(rootFilePath);
			if (!f.exists())
				f.mkdir();
			
			f = new File(rootFilePath + File.separator + recId);
			if (!f.exists())
				f.mkdir();
			
			StringBuffer path = new StringBuffer(rootFilePath + File.separator + recId + File.separator);
			
			f = new File(path.toString());
			if (!f.exists())
				f.mkdir();
			
			path.append(docType);
			f = new File(path.toString());
			if (!f.exists())
				f.mkdir();			
			
			byte barr[]=docfile.getBytes();  
			
			BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(path+File.separator+fileName));  
	        bout.write(barr);
	        bout.flush();
	        bout.close();
	        
			// 	update file path to record
	        InstructionManual fileRec = manualRepo.findOne(recId);
	        if ("document".equalsIgnoreCase(docType))
	        	fileRec.setStoredFielName(path+File.separator+fileName);
	        else
	        	fileRec.setStoredThumbFielName(path+File.separator+fileName);
			manualRepo.save(fileRec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void reorderExisitngRecords(int displayOrder) {
		List<InstructionManual> list = manualRepo.getOrderRecords(displayOrder);
		if (list.size() > 0){
			for (InstructionManual l: list) {
				l.setDisplayOrder(displayOrder++);
				manualRepo.save(l);
			}
		}
	}

	public String getManualDocumentsData(int draw, int start, int length, String searchTerm, 
			int orderColumn, String orderDir, String docName, String doctype) {

		if (docName == null) docName="";
		if (doctype == null) doctype="";
		
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<InstructionManual> docList = null;
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;

		try {
			if ("".equalsIgnoreCase(docName) && "".equalsIgnoreCase(doctype)) {
				total = manualRepo.findAll().size();
			} else {
				total = manualRepo.countWidthSearch(docName);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "permission");
				break;
			}
			case 2: {
				page = new PageRequest(pageNumber, length, dir, "isActive");
				break;
			}
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			if ("".equalsIgnoreCase(docName) && "".equalsIgnoreCase(doctype)) {
				docList = manualRepo.getAllManual(page);
			} else {
				docList = manualRepo.findWidthSearch(docName, page);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<InstructionManualForm> docObjList = new ArrayList<InstructionManualForm>();
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		
		if (docList != null && docList.getSize() > 0 ) {
			for(InstructionManual a: docList) {
				InstructionManualForm obj = new InstructionManualForm();
				obj.setFileId(a.getId());
				
				obj.setDocumentTitle(a.getFileTitle());
				
				
				// thumb image data
				obj.setOriginalThumbFileName(a.getOriginalThumbFileName());
				obj.setThumbFileExtension(a.getThumbFileExtension());
				obj.setStoredThumbFielName(a.getStoredThumbFielName());
				
				// document file data
				obj.setOriginalFileName(a.getOriginalFileName());
				obj.setStoredFielName(a.getStoredFielName());
				obj.setFileExtension(a.getFileExtension());
				
/*				obj.setFileType(a.getFileType());
				if (a.getFileType().equalsIgnoreCase("image"))
					obj.setDocumentType("image");
				else
					obj.setDocumentType("document");
*/				
				
				
				obj.setDisplayOrder(a.getDisplayOrder());
				obj.setCreatorName(  adminRepo.findOne(a.getAdminId()).getName() );
				obj.setUploadedDate(dt.format(a.getUploadedDate()));
				obj.setDT_RowId("row_" + a.getId());
				
				docObjList.add(obj);
			}
		}
		
		ManualJSonObj data = new ManualJSonObj();
		data.setData(docObjList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);
		
		return json2;	
	}
	

	public String getDocFileName(int fileId, boolean isImageFile) {
		String file = "";
		if (fileId > 0) {
			try {
				InstructionManual a = manualRepo.findOne(fileId);
				if (a != null){
					if (isImageFile)
						file = a.getStoredThumbFielName();
					else
						file = a.getStoredFielName();
				}
			} catch(Exception e){}
		}
		return file;
	}
	
	public String getDownloadDocFileName(int fileId, boolean isImageFile) {
		String file = "";
		if (fileId > 0) {
			try {
				InstructionManual a = manualRepo.findOne(fileId);
				if (a != null){
					if (isImageFile)
						file = a.getOriginalThumbFileName();
					else
						file = a.getOriginalFileName();
				}
			} catch(Exception e){}
		}
		return file;
	}
	
	public boolean deleteInstructionManualDocFiles(int id) {
		boolean isDeleted = false;
		if (id > 0) {
			InstructionManual a = manualRepo.findOne(id);
			if (a != null) {
				manualRepo.delete(a);
				isDeleted = true;
			}
		}
		return isDeleted;
	}

	public InstructionManualForm editInstructionManualDocFiles(int id) {
		InstructionManualForm form = new InstructionManualForm();
		if (id > 0) {
			InstructionManual acc = manualRepo.findOne(id);
			
			form.setAdminId(acc.getAdminId());
			form.setDisplayOrder(acc.getDisplayOrder());
			form.setDocumentTitle(acc.getFileTitle());
			form.setFileDescription(acc.getFileDescription()); 
			form.setFileExtension(acc.getFileExtension());
			form.setFileId(acc.getId());
			form.setOriginalFileName(acc.getOriginalFileName());
			form.setOriginalThumbFileName(acc.getOriginalThumbFileName());
			form.setStoredFielName(acc.getStoredFielName());
			form.setStoredThumbFielName(acc.getStoredThumbFielName());
			form.setThumbFileExtension(acc.getThumbFileExtension());
		}
		
		return form;
	}

	public List<InstructionManualForm> getAllDocumentsList(String filePath) {
		List<InstructionManualForm> docLists = new ArrayList<InstructionManualForm>();
		List<InstructionManual> accList = manualRepo.getAllManual();
		
		for (InstructionManual acc: accList) {
			InstructionManualForm form = new InstructionManualForm();
			
			form.setAdminId(acc.getAdminId());
			form.setDisplayOrder(acc.getDisplayOrder());
			form.setDocumentTitle(acc.getFileTitle());
			form.setFileDescription(acc.getFileDescription()); 
			form.setFileExtension(acc.getFileExtension());
			form.setFileId(acc.getId());
			form.setOriginalFileName(acc.getOriginalFileName());
			form.setOriginalThumbFileName(acc.getOriginalThumbFileName());
			form.setStoredFielName(acc.getStoredFielName());
			form.setStoredThumbFielName(acc.getStoredThumbFielName());
			form.setThumbFileExtension(acc.getThumbFileExtension());
			
			form.setDisplayDocumentName( getImageName(acc.getStoredFielName(), filePath)  );
			form.setDisplayImageName( getImageName(acc.getStoredThumbFielName(), filePath)  );
			
			docLists.add(form);
		}
		
		return docLists;
	}
	
	
	
	public String getImageName(String inputFileName, String filepath) {
		String filename="";
		try {
			// F:\PharmacyPortalWks\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\CRE8Portal\resources\instructionmanual\3\image\manual_3.png
			

			if (inputFileName != null && !"".equalsIgnoreCase(inputFileName) && inputFileName.indexOf(filepath) > -1) {
				filename = inputFileName.substring(inputFileName.indexOf(filepath));
				filename = filename.replace("\\", "/");
			}
			
			/*AdminFileUpload rec = adminFileRepo.findByAdminIdAndFileType(fileId, "Photo");
			if (rec != null && rec.getStoredFielName() != null && !"".equalsIgnoreCase(rec.getStoredFielName())) {
				filename = rec.getStoredFielName().substring(rec.getStoredFielName().indexOf(filepath));
				filename = filename.replace("\\", "/");
			}*/
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return filename; 
	}
	
	
	public String getDocumentFileNameById(String id, String filepath) {
		String filename="";
		if (Integer.parseInt(id) > 0) {
			InstructionManual acc = manualRepo.findOne( Integer.parseInt(id));
			if (acc != null) {
				filename = acc.getStoredFielName();
				
				if (filename != null && !"".equalsIgnoreCase(filename) && filename.indexOf(filepath) > -1) {
					filename = filename.substring(filename.indexOf(filepath));
					filename = filename.replace("\\", "/");
				}
			}
		}
		return filename;
	}

	public List<InstructionManualForm> getLatestManual(Pageable latest, String filePath) {
		
		List<InstructionManualForm> docLists = new ArrayList<InstructionManualForm>();
		List<InstructionManual> accList = manualRepo.getLatestManual(latest);
		
		for (InstructionManual acc: accList) {
			InstructionManualForm form = new InstructionManualForm();
			
			form.setAdminId(acc.getAdminId());
			form.setDisplayOrder(acc.getDisplayOrder());
			form.setDocumentTitle(acc.getFileTitle());
			form.setFileDescription(acc.getFileDescription()); 
			form.setFileExtension(acc.getFileExtension());
			form.setFileId(acc.getId());
			form.setOriginalFileName(acc.getOriginalFileName());
			form.setOriginalThumbFileName(acc.getOriginalThumbFileName());
			form.setStoredFielName(acc.getStoredFielName());
			form.setStoredThumbFielName(acc.getStoredThumbFielName());
			form.setThumbFileExtension(acc.getThumbFileExtension());
			
			form.setDisplayDocumentName( getImageName(acc.getStoredFielName(), filePath)  );
			form.setDisplayImageName( getImageName(acc.getStoredThumbFielName(), filePath)  );
			
			docLists.add(form);
		}
		
		
		return docLists;
	}
	
}
