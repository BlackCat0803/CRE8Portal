package com.pharma.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.manual.InstructionManualForm;
import com.pharma.core.pharmaservices.manual.InstructionManualService;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to upload the instruction manual 
 */
@Controller
@PropertySource("classpath:instructionmanual.properties")
@PropertySource("classpath:physicianAccount.properties")
public class InstructionManualController extends InstructionBaseController {

	@Autowired
	InstructionManualService manualService;
	
	@Autowired
	private Environment env;
	/**
	 * This method loads the instruction manual upload page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/documentUpload")  
	public ModelAndView loadDocumentUpload(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		int maxDisplayOrder =  manualService.getLatestDisplayOrder();
	

		ModelAndView mv = new ModelAndView();
		
		try {
			InstructionManualForm form = new InstructionManualForm();
			form.setDisplayOrder(++maxDisplayOrder);
			mv.addObject("manual", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			mv.setViewName("manualDocuments");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * This method uploads the instruction manual data
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @param uploadImageFile
	 * @param uploadDocFile
	 * @return
	 */
	@RequestMapping(value = "/saveDocuments", method = RequestMethod.POST)
	public ModelAndView saveDocumentUpload(@ModelAttribute("manual") InstructionManualForm form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam CommonsMultipartFile uploadImageFile, @RequestParam CommonsMultipartFile uploadDocFile) {
		
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (uploadDocFile != null) {
				// String rootFilePath=env.getProperty("file.root_folder_path") + File.separator + env.getProperty("file.instruction_manual_folder");
				String rootFilePath= PharmacyUtil.getRootFolderForInstructionManual(session, env);
				boolean isSaved = manualService.saveDocumentFiles(uploadDocFile, uploadImageFile, form, rootFilePath, loggedInUser);
				if (isSaved) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					redirectAttributes.addFlashAttribute("message", "Instruction manual uploaded successfully");
					
					//System.out.println("Document uploaded successfully");
				} else {
					redirectAttributes.addFlashAttribute("saveStatus", "false");
					redirectAttributes.addFlashAttribute("message", "failed to upload Instruction manual");
					
					//System.out.println("Fail to upload Document");
				}
			}
			mv.setViewName("redirect:documentUpload");				
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Admin Account failed to create.");
			mv.setViewName("error500");
		}
		return mv;
	}
	/**
	 * This method loads the uploaded instruction manual data
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param col0Name
	 * @param col0Search
	 * @param col0Order
	 * @param col0SearchValue
	 * @param col0SearchRegex
	 * @param col1Data
	 * @param col1Name
	 * @param col1Search
	 * @param col1Order
	 * @param col1SearchValue
	 * @param col1SearchRegex
	 * @param col2Data
	 * @param col2Name
	 * @param col2Search
	 * @param col2Order
	 * @param col2SearchValue
	 * @param col2SearchRegex
	 * @param col3Data
	 * @param col3Name
	 * @param col3Search
	 * @param col3Order
	 * @param col3SearchValue
	 * @param col3SearchRegex
	 * @param col4Data
	 * @param col4Name
	 * @param col4Search
	 * @param col4Order
	 * @param col4SearchValue
	 * @param col4SearchRegex
	 * @param col5Data
	 * @param col5Name
	 * @param col5Search
	 * @param col5Order
	 * @param col5SearchValue
	 * @param col5SearchRegex
	 * @param col6Data
	 * @param col6Name
	 * @param col6Search
	 * @param col6Order
	 * @param col6SearchValue
	 * @param col6SearchRegex
	 * @param displayName
	 * @param doctype
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAdminManualDocData", method = RequestMethod.POST)
	public @ResponseBody String getAdminManualDocumentsData(@RequestParam("draw") int draw,
			@RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm,
			@RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, @RequestParam("columns[0][data]") String col0Data,
			@RequestParam("columns[0][name]") String col0Name, @RequestParam("columns[0][searchable]") String col0Search, @RequestParam("columns[0][orderable]") String col0Order,
			@RequestParam("columns[0][search][value]") String col0SearchValue, @RequestParam("columns[0][search][regex]") String col0SearchRegex,
			@RequestParam("columns[1][data]") String col1Data, @RequestParam("columns[1][name]") String col1Name, @RequestParam("columns[1][searchable]") String col1Search,
			@RequestParam("columns[1][orderable]") String col1Order, @RequestParam("columns[1][search][value]") String col1SearchValue,
			@RequestParam("columns[1][search][regex]") String col1SearchRegex,
			@RequestParam("columns[2][data]") String col2Data, @RequestParam("columns[2][name]") String col2Name, @RequestParam("columns[2][searchable]") String col2Search,
			@RequestParam("columns[2][orderable]") String col2Order, @RequestParam("columns[2][search][value]") String col2SearchValue, @RequestParam("columns[2][search][regex]") String col2SearchRegex,
			@RequestParam("columns[3][data]") String col3Data, @RequestParam("columns[3][name]") String col3Name, @RequestParam("columns[3][searchable]") String col3Search,
			@RequestParam("columns[3][orderable]") String col3Order, @RequestParam("columns[3][search][value]") String col3SearchValue,
			@RequestParam("columns[3][search][regex]") String col3SearchRegex,
			@RequestParam("columns[4][data]") String col4Data, @RequestParam("columns[4][name]") String col4Name, @RequestParam("columns[4][searchable]") String col4Search,
			@RequestParam("columns[4][orderable]") String col4Order, @RequestParam("columns[4][search][value]") String col4SearchValue,
			@RequestParam("columns[4][search][regex]") String col4SearchRegex,
			@RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name, @RequestParam("columns[5][searchable]") String col5Search,
			@RequestParam("columns[5][orderable]") String col5Order, @RequestParam("columns[5][search][value]") String col5SearchValue,
			@RequestParam("columns[5][search][regex]") String col5SearchRegex,
			@RequestParam("columns[6][data]") String col6Data, @RequestParam("columns[6][name]") String col6Name, @RequestParam("columns[6][searchable]") String col6Search,
			@RequestParam("columns[6][orderable]") String col6Order, @RequestParam("columns[6][search][value]") String col6SearchValue,
			@RequestParam("columns[6][search][regex]") String col6SearchRegex,
			@RequestParam("displayName") String displayName, @RequestParam("doctype") String doctype, HttpServletRequest request) {
		
		return manualService.getManualDocumentsData(draw, start, len, searchTerm, orderColumn, orderDir, displayName, doctype);
	}	
	/**
	 * This method loads the instruction manual data
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/editManualDocument")
	public ModelAndView editInstructionDocFiles(Model model, HttpServletRequest request, 
			RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("manual") InstructionManualForm form) {
		
		ModelAndView mv = new ModelAndView();
		try {
			int id = 0;
			if (form != null)
				id = form.getTempId();
			//System.out.println("editDocFiles ---> " + id);
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			form = manualService.editInstructionManualDocFiles( id );
			mv.addObject("manual", form);
			mv.setViewName("manualDocuments");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 *  This method deletes the instruction manual data
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @param response
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteManualDocument", method = RequestMethod.POST)
	public ModelAndView deleteInstructionDocFiles(Model model, HttpServletRequest request, 
			RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("id") String id) {
		
		ModelAndView mv = new ModelAndView();
		try {
			//System.out.println("deleteDocFiles ---> " + id);

			boolean isDeleted = manualService.deleteInstructionManualDocFiles( Integer.parseInt(id) );
			if (isDeleted) {
				redirectAttributes.addFlashAttribute("saveStatus", "true");
				redirectAttributes.addFlashAttribute("message", "Instruction manual deleted successfully");
				
				//System.out.println("Document deleted successfully");
			} else {
				redirectAttributes.addFlashAttribute("saveStatus", "false");
				redirectAttributes.addFlashAttribute("message", "failed to delete Instruction manual");
				
				//System.out.println("Fail to delete Document");
			}
			mv.setViewName("redirect:documentUpload");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * This method downloads the instruction manual data
	 * @param request
	 * @param response
	 * @param session
	 * @param fileId
	 * @param isImageStr
	 */
	@RequestMapping(value = "/manualDocFileDownload", method = RequestMethod.GET)
	public void downloadInstructionDocFiles(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, @RequestParam("f") String fileId, @RequestParam("i") String isImageStr){
		try {
			//System.out.println("downloadDocFiles ---> " + fileId);
			String fileName = "";
			String downloadName="";
			if (isImageStr.equalsIgnoreCase("n")) {
				fileName = manualService.getDocFileName( Integer.parseInt(fileId), false );
				downloadName = manualService.getDownloadDocFileName( Integer.parseInt(fileId), false );
			} else {
				fileName = manualService.getDocFileName( Integer.parseInt(fileId), true );
				downloadName = manualService.getDownloadDocFileName( Integer.parseInt(fileId), true );
			}
			PharmacyUtil.downloadFile(fileName, downloadName, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method loads the instruction manual list data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/manualDocListView", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView instructionDocumentViewList(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		
		try {
			//String rootFilePath= PharmacyUtil.getRootFolderForInstructionManual(session, env);
			String rootFilePath=env.getProperty("file.instruction_manual_folder");
			List<InstructionManualForm> formList = manualService.getAllDocumentsList(rootFilePath);
			mv.addObject("docList", formList);
			mv.setViewName("viewDocuments");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * This method returns the instruction manual file
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instructionManualDocView", method = RequestMethod.POST)
	public @ResponseBody String instructionManualDocView(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, @RequestParam("id") String id){
		
		String fileName = "";
		String filepath=env.getProperty("file.instruction_manual_folder");
		fileName = manualService.getDocumentFileNameById(id, filepath);
		
		
		return fileName;
	}
	
	
}
