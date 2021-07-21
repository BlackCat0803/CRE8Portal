package com.pharma.core.pharmaservices.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.invoice.InvoiceForm;
import com.pharma.core.formBean.invoice.InvoiceJSonObj;
import com.pharma.core.formBean.order.OrderForm;
import com.pharma.core.formBean.order.OrderJSonObj;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.formBean.patient.PatientAccountJSonObj;
import com.pharma.core.formBean.physician.PhysicianJSonObj;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.reports.PatientsList;
import com.pharma.core.formBean.reports.PrescriptionJSonObj;
import com.pharma.core.formBean.reports.PrescriptionReportBean;
import com.pharma.core.model.invoice.InvoiceTransaction;
import com.pharma.core.model.order.OrderTransaction;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianClinic;
import com.pharma.core.model.prescription.PrescriptionTransaction;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianGroupService;
import com.pharma.core.pharmaservices.prescription.PrescriptionTranService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.invoice.InvoiceMasterRepository;
import com.pharma.core.repository.invoice.InvoiceTransactionRepository;
import com.pharma.core.repository.order.OrderMasterRepository;
import com.pharma.core.repository.order.OrderTransactionRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianClinicRepository;
import com.pharma.core.repository.prescription.PrescriptionRepository;
import com.pharma.core.repository.prescription.PrescriptionTranRepository;
import com.pharma.core.util.PharmacyUtil;


@Service("reportService")
@PropertySource("classpath:prescription.properties") 
public class ReportServiceImpl implements ReportService {

	@Autowired
	PatientAccountRespository patientAccountResp;
	
	@Autowired
	PhysicianAccountRespository physicianRep;

	@Autowired
	PhysicianGroupService phyGroupService;

	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	PrescriptionRepository prescriptionRep;

	@Autowired
	OrderMasterRepository orderRepo;
	
	@Autowired
	InvoiceMasterRepository invoiceRepo;
	
	@Autowired
	InvoiceTransactionRepository invoiceTranRepo;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirectorRepo;
	
	@Autowired
	OrderTransactionRepository orderTranRepo;
	
	@Autowired
	PrescriptionTranRepository presTranRepo;
	
	@Autowired
	PhysicianAccountService phyAcctService;
	
	@Autowired
	PhysicianClinicRepository phyClinicRepo;

	@Autowired
	PrescriptionTranService prescriptionTranService; 

	@Autowired
	GroupMasterRespository groupMasterAccount;
	
	@Autowired
	private Environment env;
	
	public String getPatientListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir,
			String physician, String group, String status, String fromDate, String toDate, HttpSession session) {

		try {
			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<Object[]> patientList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;
			
			
			int physicianId = 0;
			if (physician != null && !"".equalsIgnoreCase(physician)) {
				physicianId = physicianRep.findByPhysicianName(physician).getId();
			}

			/** -------------- Query ----------- 
			  	select pa.patient_name, gm.group_name, c.clinic_name, phy.physician_name, pa.mobile, pa.email, pa.city, pa.state, 
			  	pa.Date_registered, pa.status
				from patient_profile pa, phy_info phy, clinic c, phy_group phgp, group_master gm
				where pa.physician_id = phy.physician_id and phy.clinic_id = c.clinic_id and phy.physician_id = phgp.physician_id and 
				phgp.group_id = gm.group_id limit 10;
			 * */

			try {
				if (!"".equalsIgnoreCase(status)) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilter(physician, group, status).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilterWithFromDate(physician, group, status, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilterWithToDate(physician, group, status, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilterWithDate(physician, group, status, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilter(physician, group).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilterWithFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilterWithToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = patientAccountResp.reportCountByFilterWithDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			pageNumber = start / length;
			page = new PageRequest(pageNumber, length, dir, "id");
			
			try {
				if (!"".equalsIgnoreCase(status)) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilter(physician, group, status, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithFromDate(physician, group, status, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithToDate(physician, group, status, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithDate(physician, group, status, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilter(physician, group, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = patientList.getContent();
			
			List<PatientAccountForm> phyAssObjList = new ArrayList<PatientAccountForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			if (patientList != null && patientList.getSize() > 0 ) {
				// pa.patient_name, gm.group_name, phy.physician_name, pa.mobile, pa.email, pa.city, pa.state, pa.Date_registered, pa.status, pa.SSN, 
				// pa.date_of_birth, pa.gender, pa.patient_id, gm.group_id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,
				// pa.alternateIdTypeText
				for (int i=0; i<reportList.size(); i++) {
					PatientAccountForm obj = new PatientAccountForm();
					if (reportList.get(i)[0] != null)
						obj.setPatientName((String) reportList.get(i)[0]);
					else
						obj.setPatientName("");
					/*if (reportList.get(i)[1] != null)
						obj.setGroupName((String) reportList.get(i)[1]);
					else
						obj.setGroupName("");*/
					
					if (reportList.get(i)[3] != null)
						obj.setMobile((String) reportList.get(i)[3]);
					else
						obj.setMobile("");
					if (reportList.get(i)[4] != null)
						obj.setEmail((String) reportList.get(i)[4]);
					else
						obj.setEmail("");
					if (reportList.get(i)[5] != null)
						obj.setCity((String) reportList.get(i)[5]);
					else
						obj.setCity("");
					if (reportList.get(i)[6] != null)
						obj.setState((String) reportList.get(i)[6]);
					else
						obj.setState("");
					if (reportList.get(i)[7] != null)
						obj.setDateRegistered(dt.format((Date) reportList.get(i)[7]) );
					else
						obj.setDateRegistered("");
					if (reportList.get(i)[8] != null)
						obj.setStatus((String) reportList.get(i)[8]);
					else
						obj.setStatus("");
					if (reportList.get(i)[9] != null)
						obj.setSsn((String) reportList.get(i)[9]);
					else
						obj.setSsn("");
					if (reportList.get(i)[10] != null)
						obj.setDateOfBirth(dt.format((Date) reportList.get(i)[10]) );
					else
						obj.setDateOfBirth("");
					if (reportList.get(i)[11] != null)
						obj.setGender((String) reportList.get(i)[11]);
					else
						obj.setGender("");
					
//					StringBuilder physicianName = new StringBuilder();
					int patient_id = (Integer) reportList.get(i)[12];
					
					obj.setPatientId(patient_id);
					
					if (reportList.get(i)[14] != null)
						obj.setAddress((String) reportList.get(i)[14]);
					else
						obj.setAddress("");
					
					if (reportList.get(i)[15] != null)
						obj.setZipCode((String) reportList.get(i)[14]);
					else
						obj.setZipCode("");

					if (reportList.get(i)[16] != null)
						obj.setPhone((String) reportList.get(i)[16]);
					else
						obj.setPhone("");

					if (reportList.get(i)[17] != null)
						obj.setDriversLicense((String) reportList.get(i)[17]);
					else
						obj.setDriversLicense("");

					if (reportList.get(i)[18] != null)
						obj.setAlternateId((String) reportList.get(i)[18]);
					else
						obj.setAlternateId("");

					if (reportList.get(i)[19] != null)
						obj.setAlternateIdTypeText((String) reportList.get(i)[19]);
					else
						obj.setAlternateIdTypeText("");
					
					StringBuffer sb = new StringBuffer();
					if (!"".equalsIgnoreCase(obj.getAddress()))
						sb.append(obj.getAddress()).append(",");
					if (!"".equalsIgnoreCase(obj.getCity()))
						sb.append(obj.getCity()).append(",");
					if (!"".equalsIgnoreCase(obj.getState()))
						sb.append(obj.getState()).append(",");
					if (!"".equalsIgnoreCase(obj.getZipCode()))
						sb.append(obj.getZipCode()).append(",");
					if (sb != null && sb.toString().trim().length() > 0)
						obj.setAddressInfo( sb.toString().substring(0, sb.toString().length()-1) );
					else
						obj.setAddressInfo("");
					
					
					/*int group_id = 0;
					if (reportList.get(i)[13] != null) {
						group_id = (Integer) reportList.get(i)[13];
					}
					if (group_id > 0) {
						List<Integer> groupList = new ArrayList<Integer>();
						groupList.add(group_id);
						
						List<PhysicianAccount> groupTableList = physicianRep.getAllPatientGroupWisePhysicianListSelected(groupList, patient_id);
						if (groupTableList.size() > 0) {
							for (PhysicianAccount g: groupTableList) {
								if (group_id > 0)
									physicianName.append( phyAcctService.getPhysicianAccountDetails(g.getId()).getPhysicianName()).append(",");
								else
									physicianName.append(",");
							}
						}
					}
					if (physicianName.length() > 0)
						obj.setPhysicianName( physicianName.toString().substring(0, physicianName.length()-1) );
					else
						obj.setPhysicianName("");*/
					
					StringBuilder groupName = new StringBuilder();
					StringBuilder selectedGroupId = new StringBuilder();
					List<Integer> groupList = new ArrayList<Integer>();
					List<GroupMaster> groupMstTableList = null;
					
					if (physicianId > 0) {
						groupMstTableList = groupMasterAccount.getAllPatientGroupWiseListSelected(patient_id, physicianId);
					} else {
						groupMstTableList = groupMasterAccount.getAllPatientGroupWiseListSelected(patient_id);	
					}
					if (groupMstTableList.size() > 0) {
						for (GroupMaster g: groupMstTableList) {
							groupList.add(g.getId());
							if (g.getId() > 0) {
								selectedGroupId.append(g.getId()+"").append(",");
								groupName.append( g.getGroupName()).append(",");
							}
							else {
								selectedGroupId.append(",");
								groupName.append(",");
							}
						}
					}
					if (groupName.length() > 0)
						obj.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
					else
						obj.setGroupName("");
			
					StringBuilder physicianNameStr = new StringBuilder();
					if(groupList!=null && groupList.size()>0){
						List<PhysicianAccount> groupTableList = physicianRep.getPatientGroupWisePhysicianListSelected(groupList, patient_id);
						if (groupTableList.size() > 0) {
							for (PhysicianAccount g: groupTableList) {
								//physicianList.add(a.getGroupId());
								if (g.getId() > 0)
									physicianNameStr.append( g.getPhysicianName()).append(",");
								else
									physicianNameStr.append(",");
							}
						}
					}
					if (physicianNameStr.length() > 0)
						obj.setPhysicianName( physicianNameStr.toString().substring(0, physicianNameStr.length()-1) );
					else
						obj.setPhysicianName("");
					
					

					phyAssObjList.add(obj);
				}
			}

			PatientAccountJSonObj data = new PatientAccountJSonObj();
			data.setData(phyAssObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public boolean getPatientListExcelFile(PatientsList form, String fileName, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response, boolean isPdfFile) {
		boolean isGenerated = false;
		try {
			Page<Object[]> patientList = null;
			
			String physician = form.getPhysician();
			String group = form.getGroup();
			String status = form.getStatus();
			String fromDate = form.getFromRegDate();
			String toDate = form.getToRegDate();
			
			int physicianId = 0;
			if (physician != null && !"".equalsIgnoreCase(physician)) {
				physicianId = physicianRep.findByPhysicianName(physician).getId();
			}
			
			Pageable page = new PageRequest(0, Integer.MAX_VALUE);
			try {
				if (!"".equalsIgnoreCase(status)) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilter(physician, group, status, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithFromDate(physician, group, status, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithToDate(physician, group, status, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithDate(physician, group, status, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilter(physician, group, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						patientList = patientAccountResp.reportByFilterWithDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = patientList.getContent();
			List<PatientAccountForm> phyAssObjList = new ArrayList<PatientAccountForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			if (patientList != null && patientList.getSize() > 0 ) {
				// pa.patient_name, gm.group_name, phy.physician_name, pa.mobile, pa.email, pa.city, pa.state, pa.Date_registered, pa.status , pa.SSN, 
				// pa.date_of_birth, pa.gender, pa.patient_id, gm.group_id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,
				// pa.alternateIdTypeText
				for (int i=0; i<reportList.size(); i++) {
					PatientAccountForm obj = new PatientAccountForm();
					if (reportList.get(i)[0] != null)
						obj.setPatientName((String) reportList.get(i)[0]);
					else
						obj.setPatientName("");
					/*if (reportList.get(i)[1] != null)
						obj.setGroupName((String) reportList.get(i)[1]);
					else
						obj.setGroupName("");*/
					
					if (reportList.get(i)[3] != null)
						obj.setMobile((String) reportList.get(i)[3]);
					else
						obj.setMobile("");
					if (reportList.get(i)[4] != null)
						obj.setEmail((String) reportList.get(i)[4]);
					else
						obj.setEmail("");
					if (reportList.get(i)[5] != null)
						obj.setCity((String) reportList.get(i)[5]);
					else
						obj.setCity("");
					if (reportList.get(i)[6] != null)
						obj.setState((String) reportList.get(i)[6]);
					else
						obj.setState("");
					if (reportList.get(i)[7] != null)
						obj.setDateRegistered(dt.format((Date) reportList.get(i)[7]) );
					else
						obj.setDateRegistered("");
					if (reportList.get(i)[8] != null)
						obj.setStatus((String) reportList.get(i)[8]);
					else
						obj.setStatus("");
					if (reportList.get(i)[9] != null)
						obj.setSsn((String) reportList.get(i)[9]);
					else
						obj.setSsn("");
					if (reportList.get(i)[10] != null)
						obj.setDateOfBirth(dt.format((Date) reportList.get(i)[10]) );
					else
						obj.setDateOfBirth("");
					if (reportList.get(i)[11] != null)
						obj.setGender((String) reportList.get(i)[11]);
					else
						obj.setGender("");
					
					int patient_id = (Integer) reportList.get(i)[12];
					
					
					obj.setPatientId(patient_id);
					
					if (reportList.get(i)[14] != null)
						obj.setAddress((String) reportList.get(i)[14]);
					else
						obj.setAddress("");
					
					if (reportList.get(i)[15] != null)
						obj.setZipCode((String) reportList.get(i)[14]);
					else
						obj.setZipCode("");

					if (reportList.get(i)[16] != null)
						obj.setPhone((String) reportList.get(i)[16]);
					else
						obj.setPhone("");

					if (reportList.get(i)[17] != null)
						obj.setDriversLicense((String) reportList.get(i)[17]);
					else
						obj.setDriversLicense("");

					if (reportList.get(i)[18] != null)
						obj.setAlternateId((String) reportList.get(i)[18]);
					else
						obj.setAlternateId("");

					if (reportList.get(i)[19] != null)
						obj.setAlternateIdTypeText((String) reportList.get(i)[19]);
					else
						obj.setAlternateIdTypeText("");
					
					StringBuffer sb = new StringBuffer();
					if (!"".equalsIgnoreCase(obj.getAddress()))
						sb.append(obj.getAddress()).append(",");
					if (!"".equalsIgnoreCase(obj.getCity()))
						sb.append(obj.getCity()).append(",");
					if (!"".equalsIgnoreCase(obj.getState()))
						sb.append(obj.getState()).append(",");
					if (!"".equalsIgnoreCase(obj.getZipCode()))
						sb.append(obj.getZipCode()).append(",");
					if (sb != null && sb.toString().trim().length() > 0)
						obj.setAddressInfo( sb.toString().substring(0, sb.toString().length()-1) );
					else
						obj.setAddressInfo("");
					
					/*StringBuilder physicianName = new StringBuilder();
					int group_id = 0;
					if (reportList.get(i)[13] != null) {
						group_id = (Integer) reportList.get(i)[13];
					}
					if (group_id > 0) {
						List<Integer> groupList = new ArrayList<Integer>();
						groupList.add(group_id);
											
						List<PhysicianAccount> groupTableList = physicianRep.getAllPatientGroupWisePhysicianListSelected(groupList, patient_id);
						if (groupTableList.size() > 0) {
							for (PhysicianAccount g: groupTableList) {
								if (group_id > 0)
									physicianName.append( phyAcctService.getPhysicianAccountDetails(g.getId()).getPhysicianName()).append(",");
								else
									physicianName.append(",");
							}
						}
					}
					if (physicianName.length() > 0)
						obj.setPhysicianName( physicianName.toString().substring(0, physicianName.length()-1) );
					else
						obj.setPhysicianName("");*/
					
					
					
					StringBuilder groupName = new StringBuilder();
					StringBuilder selectedGroupId = new StringBuilder();
					List<Integer> groupList = new ArrayList<Integer>();
					
					List<GroupMaster> groupMstTableList = null;
					if (physicianId > 0) {
						groupMstTableList = groupMasterAccount.getAllPatientGroupWiseListSelected(patient_id, physicianId);
					} else {
						groupMstTableList = groupMasterAccount.getAllPatientGroupWiseListSelected(patient_id);	
					}
					if (groupMstTableList.size() > 0) {
						for (GroupMaster g: groupMstTableList) {
							groupList.add(g.getId());
							if (g.getId() > 0) {
								selectedGroupId.append(g.getId()+"").append(",");
								groupName.append( g.getGroupName()).append(",");
							}
							else {
								selectedGroupId.append(",");
								groupName.append(",");
							}
						}
					}
					if (groupName.length() > 0)
						obj.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
					else
						obj.setGroupName("");
			
					StringBuilder physicianNameStr = new StringBuilder();
					if(groupList!=null && groupList.size()>0){
						List<PhysicianAccount> groupTableList = physicianRep.getPatientGroupWisePhysicianListSelected(groupList, patient_id);
						if (groupTableList.size() > 0) {
							for (PhysicianAccount g: groupTableList) {
								if (g.getId() > 0)
									physicianNameStr.append( g.getPhysicianName()).append(",");
								else
									physicianNameStr.append(",");
							}
						}
					}
					if (physicianNameStr.length() > 0)
						obj.setPhysicianName( physicianNameStr.toString().substring(0, physicianNameStr.length()-1) );
					else
						obj.setPhysicianName("");

					phyAssObjList.add(obj);
				}
			}
			JRDataSource itemsJRBean = new JRBeanCollectionDataSource(phyAssObjList);
			
			String rootFilePath = session.getServletContext().getRealPath("/") + File.separator;
            String targetFolder = rootFilePath + File.separator + "Reports" + File.separator + "PatientList";
    		File f = new File(targetFolder);
    		if (!f.exists())
    			f.mkdir();
            
			String companyLogo = rootFilePath + "\\resources\\images\\CRE8-Pharma-logo.png";
			String outputFileName = "Patient_List";
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("companyLogo", companyLogo);
            if (isPdfFile)
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
            else
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);

            JasperPrint jasperPrint = JasperFillManager.fillReport(rootFilePath + "\\JASP-RPT\\Reports\\patient_list.jasper", parameters, itemsJRBean);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (isPdfFile) {
            	response.setContentType("application/pdf");
            	response.setHeader("Content-Disposition", "attachment;filename="+outputFileName+".pdf");
            	
            	byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            	
            	response.getOutputStream().write(pdfBytes);
            	response.getOutputStream().flush();
            	response.getOutputStream().close();
            	response.flushBuffer();
            } else {
        		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        		response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

            	JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        		/*xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName+".xlsx");
        		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);*/
        		
        		xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFileName+".xlsx"));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        		xlsxExporter.exportReport();
        		
        		response.getOutputStream().write(os.toByteArray());
        		response.getOutputStream().flush();
        		response.getOutputStream().close();
        		response.flushBuffer();
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isGenerated;
	}

	public String getPhysicianListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician,  
			String clinic, String group, String status, String dea, String fromDate, String toDate, HttpSession session) {
		if (physician == null) physician = "";
		if (clinic == null) clinic = "";
		if (group == null) group = "";
		if (status == null) status = "";
		if (dea == null) dea = "";
		if (fromDate == null) fromDate = "";
		if (toDate == null) toDate = "";
		
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<Object[]> physicianList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;
		
			try {
				if (!"".equalsIgnoreCase(status)) {
					if ("".equalsIgnoreCase(clinic)) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatus(physician, group, dea, status).size();
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatusAndFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, status).size();
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatusAndToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), dea, status).size();
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatusAndDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, status).size();
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatus(physician, clinic, group, dea, status).size();
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatusAndFromDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, status).size();
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithStatusAndToDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(toDate), dea, status).size();
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, status).size();
					}
				} else {
					if ("".equalsIgnoreCase(clinic)) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilter(physician, group, dea).size();
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate), dea).size();
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), dea).size();
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea).size();
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilter(physician, clinic, group, dea).size();
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithFromDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate), dea).size();
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithToDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(toDate), dea).size();
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							total = physicianRep.reportCountByFilterWithDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea).size();
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			pageNumber = start / length;
			
			switch (orderColumn) {
				default: {
					page = new PageRequest(pageNumber, length, dir, "id");
					break;
				}
			}
			
			try {
				if (!"".equalsIgnoreCase(status)) {
					if ("".equalsIgnoreCase(clinic)) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatus(physician, group, dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, status, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);					
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatus(physician, clinic, group, dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndFromDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, status, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndToDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);
					}
				} else {
					if ("".equalsIgnoreCase(clinic)) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilter(physician, group, dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithFromDate(physician,  group, PharmacyUtil.getSqlDateFromString(fromDate), dea, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, page);					
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilter(physician, clinic, group, dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithFromDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithToDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(toDate), dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, page);
					
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = physicianList.getContent();
			
			List<PhysicianProfile> phyAssObjList = new ArrayList<PhysicianProfile>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			if (physicianList != null && physicianList.getSize() > 0 ) {
				// p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id
				
				for (int i=0; i<reportList.size(); i++) {
					PhysicianProfile obj = new PhysicianProfile();
					if (reportList.get(i)[0] != null)
						obj.setPhysicianName((String) reportList.get(i)[0]);
					else
						obj.setPhysicianName("");
					if (reportList.get(i)[1] != null)
						obj.setGroupName((String) reportList.get(i)[1]);
					else
						obj.setGroupName("");
					
					if (reportList.get(i)[3] != null)
						obj.setDea((String) reportList.get(i)[3]);
					else
						obj.setDea("");
					if (reportList.get(i)[4] != null)
						obj.setPhone((String) reportList.get(i)[4]);
					else
						obj.setPhone("");
					if (reportList.get(i)[5] != null)
						obj.setMobile((String) reportList.get(i)[5]);
					else
						obj.setMobile("");
					if (reportList.get(i)[6] != null)
						obj.setEmail((String) reportList.get(i)[6]);
					else
						obj.setEmail("");
					if (reportList.get(i)[7] != null)
						obj.setCity((String) reportList.get(i)[7]);
					else
						obj.setCity("");
					if (reportList.get(i)[8] != null)
						obj.setState((String) reportList.get(i)[8]);
					else
						obj.setState("");
					
					if (reportList.get(i)[9] != null)
						obj.setDateRegistrated(dt.format((Date) reportList.get(i)[9]) );
					else
						obj.setDateRegistrated("");
					
					obj.setStatus((String) reportList.get(i)[10]);
					
					int pid = (Integer) reportList.get(i)[11];
					
					obj.setPhysicianId(pid);
					
					obj.setClinicName("");
					
					if (reportList.get(i)[2] != null) {
						// Clinic List added to summary list 
						StringBuilder clinicNameList = new StringBuilder();
						List<PhysicianClinic> clinicTableList = phyClinicRepo.findByPhysicianIdAndDelFlagOrderById(pid, PharmacyUtil.deleteFlagNo);
						if (clinicTableList.size() > 0) {
							for (PhysicianClinic pc: clinicTableList) {
								if (pc.getClinicId() > 0) 
									clinicNameList.append( clinicRepo.findOne(pc.getClinicId()).getClinicName()).append(",");
								else
									clinicNameList.append(",");
							}
						}
						if (clinicNameList.toString().length() > 0)
							obj.setClinicName( clinicNameList.toString().substring(0, clinicNameList.length()-1) );
					}

					phyAssObjList.add(obj);
				}
			}
			
			PhysicianJSonObj data = new PhysicianJSonObj();
			data.setData(phyAssObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
	}

	public boolean getPhysicianListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, boolean isPdfFile) {

		boolean isGenerated = false;
		try {
			Page<Object[]> physicianList = null;
			
			String physician = form.getPhysician();
			String clinic = form.getClinic();
			String group = form.getGroup();
			String status = form.getStatus();
			String fromDate = form.getFromRegDate();
			String toDate = form.getToRegDate();
			String dea = form.getDeaSearch();
			
			if (physician == null) physician = "";
			if (clinic == null) clinic = "";
			if (group == null) group = "";
			if (status == null) status = "";
			if (dea == null) dea = "";
			if (fromDate == null) fromDate = "";
			if (toDate == null) toDate = "";
			
			Pageable page = new PageRequest(0, Integer.MAX_VALUE);
			try {
				if (!"".equalsIgnoreCase(status)) {
					if ("".equalsIgnoreCase(clinic)) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatus(physician, group, dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndFromDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, status, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);					
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatus(physician, clinic, group, dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndFromDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, status, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndToDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithStatusAndDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, status, page);
					}
				} else {
					if ("".equalsIgnoreCase(clinic)) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilter(physician, group, dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithFromDate(physician,  group, PharmacyUtil.getSqlDateFromString(fromDate), dea, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithToDate(physician, group, PharmacyUtil.getSqlDateFromString(toDate), dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithDate(physician, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, page);					
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilter(physician, clinic, group, dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithFromDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate), dea, page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithToDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(toDate), dea, page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							physicianList = physicianRep.reportByFilterWithDate(physician, clinic, group, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), dea, page);
					
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = physicianList.getContent();
			List<PhysicianProfile> phyAssObjList = new ArrayList<PhysicianProfile>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			if (physicianList != null && physicianList.getSize() > 0 ) {
				// p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id
				
				for (int i=0; i<reportList.size(); i++) {
					PhysicianProfile obj = new PhysicianProfile();
					if (reportList.get(i)[0] != null)
						obj.setPhysicianName((String) reportList.get(i)[0]);
					else
						obj.setPhysicianName("");
					if (reportList.get(i)[1] != null)
						obj.setGroupName((String) reportList.get(i)[1]);
					else
						obj.setGroupName("");
					
					if (reportList.get(i)[3] != null)
						obj.setDea((String) reportList.get(i)[3]);
					else
						obj.setDea("");
					if (reportList.get(i)[4] != null)
						obj.setPhone((String) reportList.get(i)[4]);
					else
						obj.setPhone("");
					if (reportList.get(i)[5] != null)
						obj.setMobile((String) reportList.get(i)[5]);
					else
						obj.setMobile("");
					if (reportList.get(i)[6] != null)
						obj.setEmail((String) reportList.get(i)[6]);
					else
						obj.setEmail("");
					if (reportList.get(i)[7] != null)
						obj.setCity((String) reportList.get(i)[7]);
					else
						obj.setCity("");
					if (reportList.get(i)[8] != null)
						obj.setState((String) reportList.get(i)[8]);
					else
						obj.setState("");
					
					if (reportList.get(i)[9] != null)
						obj.setDateRegistrated(dt.format((Date) reportList.get(i)[9]) );
					else
						obj.setDateRegistrated("");
					
					obj.setStatus((String) reportList.get(i)[10]);
					
					int pid = (Integer) reportList.get(i)[11];
					obj.setClinicName("");
					
					if (reportList.get(i)[2] != null) {
						// Clinic List added to summary list 
						StringBuilder clinicNameList = new StringBuilder();
						List<PhysicianClinic> clinicTableList = phyClinicRepo.findByPhysicianIdAndDelFlagOrderById(pid, PharmacyUtil.deleteFlagNo);
						if (clinicTableList.size() > 0) {
							for (PhysicianClinic pc: clinicTableList) {
								if (pc.getClinicId() > 0) 
									clinicNameList.append( clinicRepo.findOne(pc.getClinicId()).getClinicName()).append(",");
								else
									clinicNameList.append(",");
							}
						}
						if (clinicNameList.toString().length() > 0)
							obj.setClinicName( clinicNameList.toString().substring(0, clinicNameList.length()-1) );
					}

					phyAssObjList.add(obj);
				}
			}
			JRDataSource itemsJRBean = new JRBeanCollectionDataSource(phyAssObjList);
			
			String rootFilePath = session.getServletContext().getRealPath("/") + File.separator;
            String targetFolder = rootFilePath + File.separator + "Reports" + File.separator + "PhysicianList";
    		File f = new File(targetFolder);
    		if (!f.exists())
    			f.mkdir();
            
			String companyLogo = rootFilePath + "\\resources\\images\\CRE8-Pharma-logo.png";
			String outputFileName = "Physician_List";
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("companyLogo", companyLogo);
            if (isPdfFile)
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
            else
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(rootFilePath + "\\JASP-RPT\\Reports\\physician_list.jasper", parameters, itemsJRBean);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (isPdfFile) {
            	response.setContentType("application/pdf");
            	response.setHeader("Content-Disposition", "attachment;filename="+outputFileName+".pdf");
            	
            	byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            	
            	response.getOutputStream().write(pdfBytes);
            	response.getOutputStream().flush();
            	response.getOutputStream().close();
            	response.flushBuffer();
            } else {
        		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        		response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

            	JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            	xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFileName+".xlsx"));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        		xlsxExporter.exportReport();
        		
        		response.getOutputStream().write(os.toByteArray());
        		response.getOutputStream().flush();
        		response.getOutputStream().close();
        		response.flushBuffer();
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isGenerated;	
	}

	
	public String getPrescriptionListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			String physician, String patient, String fromDate, String toDate, String status, String group, String rxNo, String prescriptionNo, String rxPrescriptionNo,
			HttpSession session) {

		if (physician == null) physician = "";
		if (patient == null) patient = "";
		if (fromDate == null) fromDate = "";
		if (toDate == null) toDate = "";
		if (status == null) status = "";
		if (group == null) group = "";
		if (rxNo == null) rxNo = "";
		if (prescriptionNo == null) prescriptionNo = "";
		if (rxPrescriptionNo == null) rxPrescriptionNo="";
		
		LoginForm user = (LoginForm) session.getAttribute("loginDetail");
		int physicianId = 0;
		int groupId = 0;
		int patientId=0;
		if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType()))
		{
			//temp commented on jan 19,2018
			//physicianId = assistantRepo.findOne(user.getUserid()).getPhysicianId();
			physicianId = user.getPhysicianAssistantPhysicianId();
		}
		else if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType()))
			physicianId = user.getUserid();			
		else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType()))
			groupId = groupDirectorRepo.findOne(user.getUserid()).getGroupId();
		else if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType()))
			patientId = user.getUserid();	
		
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<Object[]> prescriptionList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;
		
			try {
				if (physicianId == 0 && groupId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.reportCountByFilter(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.reportCountByFilterWithFromDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.reportCountByFilterWithToDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.reportCountByFilterWithDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				} else if (physicianId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.physicianReportCountByFilter(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.physicianReportCountByFilterWithFromDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.physicianReportCountByFilterWithToDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.physicianReportCountByFilterWithDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				} else if (groupId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.groupReportCountByFilter(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.groupReportCountByFilterWithFromDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.groupReportCountByFilterWithToDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.groupReportCountByFilterWithDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				} else if (patientId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.patientReportCountByFilter(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = prescriptionRep.patientReportCountByFilterWithFromDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.patientReportCountByFilterWithToDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = prescriptionRep.patientReportCountByFilterWithDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			pageNumber = start / length;
			page = new PageRequest(pageNumber, length, dir, "id");
			
			try {
				if (physicianId == 0 && groupId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.reportByFilter(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.reportByFilterWithFromDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.reportByFilterWithToDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.reportByFilterWithDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else if (physicianId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.physicianReportByFilter(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.physicianReportByFilterWithFromDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.physicianReportByFilterWithToDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.physicianReportByFilterWithDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else if (groupId > 0) {

					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.groupReportByFilter(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.groupReportByFilterWithFromDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.groupReportByFilterWithToDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.groupReportByFilterWithDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
					
				} else if (patientId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.patientReportByFilter(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.patientReportByFilterWithFromDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.patientReportByFilterWithToDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						prescriptionList = prescriptionRep.patientReportByFilterWithDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = prescriptionList.getContent();
			
			List<PrescriptionReportBean> phyAssObjList = new ArrayList<PrescriptionReportBean>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			/*List<PrescriptionTransaction> accList =null;
			StringBuilder tempPrescriptionStr = new StringBuilder();
			StringBuilder tempRxStr = new StringBuilder();
			StringBuilder tempRxStatus = new StringBuilder();
			StringBuilder tempItems = new StringBuilder();
			StringBuilder tempDispensedItems = new StringBuilder();
			
			StringBuilder tempQty = new StringBuilder();
			StringBuilder tempTotRefills = new StringBuilder();
			StringBuilder tempRefillsFilled = new StringBuilder();
			StringBuilder tempDaysSupply = new StringBuilder();*/
			
			if (prescriptionList != null && prescriptionList.getSize() > 0 ) {
				//  m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemName, o.origintypetext,
				//	t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo
				for (int i=0; i<reportList.size(); i++) {
					PrescriptionReportBean obj = new PrescriptionReportBean();
					if (reportList.get(i)[0] != null)
						obj.setPrescriberNumber((String) reportList.get(i)[0]);
					else
						obj.setPrescriberNumber("");
					if (reportList.get(i)[1] != null)
						obj.setWrittenOn(dt.format((Date) reportList.get(i)[1]) );
					else
						obj.setWrittenOn("");
					if (reportList.get(i)[2] != null)
						obj.setExpireOn(dt.format((Date) reportList.get(i)[2]) );
					else
						obj.setExpireOn("");
					if (reportList.get(i)[3] != null)
						obj.setRxNumber((String) reportList.get(i)[3]);
					else
						obj.setRxNumber("");
					if (reportList.get(i)[4] != null)
						obj.setPhysicianName((String) reportList.get(i)[4]);
					else
						obj.setPhysicianName("");
					if (reportList.get(i)[5] != null)
						obj.setPatientName((String) reportList.get(i)[5]);
					else
						obj.setPatientName("");
					
					if (reportList.get(i)[6] != null)
						obj.setItem((String) reportList.get(i)[6]);
					else
						obj.setItem("");
					
					if (reportList.get(i)[7] != null)
						obj.setOrigin((String) reportList.get(i)[7]);
					else
						obj.setOrigin("");
					if (reportList.get(i)[8] != null)
						obj.setQuantity(Integer.toString((Integer) reportList.get(i)[8]) );
					else
						obj.setQuantity("");
					if (reportList.get(i)[9] != null)
						obj.setDaysSupply(Integer.toString((Integer) reportList.get(i)[9]) );
					else
						obj.setDaysSupply("");
					if (reportList.get(i)[10] != null)
						obj.setRefills(Integer.toString((Integer) reportList.get(i)[10]) );
					else
						obj.setRefills("");
					if (reportList.get(i)[11] != null)
						obj.setRefillsRemaining(Integer.toString((Integer) reportList.get(i)[11]) );
					else
						obj.setRefillsRemaining("");
					if (reportList.get(i)[12] != null)
						obj.setRefillsFilled(Integer.toString((Integer) reportList.get(i)[12]) );
					else
						obj.setRefillsFilled("");
					if (reportList.get(i)[13] != null)
						obj.setStatus((String) reportList.get(i)[13]);
					else
						obj.setStatus("");
					
					obj.setPrescriptionId((Integer) reportList.get(i)[14]);
					
					if (reportList.get(i)[15] != null)
						obj.setCre8PrescriptionNo((String) reportList.get(i)[15]);
					else
						obj.setCre8PrescriptionNo("");
					
					if (reportList.get(i)[16] != null)
						obj.setDispensedItems((String) reportList.get(i)[16]);
					else
						obj.setDispensedItems("");

					
					
					
					
					
					/*obj.setItem("");
					obj.setRxNumber("");
					obj.setQuantity("");
					obj.setRefills("");
					obj.setRefillsFilled("");
					obj.setDaysSupply("");
					accList = prescriptionTranService.getTransactionByPrescription(prescriptionId);
					
					tempPrescriptionStr=new StringBuilder();
					tempRxStr = new StringBuilder();
					tempRxStatus = new StringBuilder();
					tempItems = new StringBuilder();
					tempDispensedItems = new StringBuilder();
					tempQty = new StringBuilder();
					tempTotRefills = new StringBuilder();
					tempRefillsFilled = new StringBuilder();
					tempDaysSupply = new StringBuilder();
					
					if (accList.size() > 0) {
						for (PrescriptionTransaction b : accList) {
							tempPrescriptionStr.append(b.getCre8PrescriptionNo()).append(", ");
							tempItems.append(b.getItemName()).append(", ");
							tempDispensedItems.append(b.getDispensedItemName()).append(", ");
							if(!"".equalsIgnoreCase(b.getRxNumber())) {
								tempRxStr.append(b.getRxNumber()).append(", ");
								tempRxStatus.append(b.getRxStatus()).append(", ");
								tempTotRefills.append(b.getRefills()).append(", ");
								tempRefillsFilled.append(b.getRefillsFilled()).append(", ");
								tempDaysSupply.append(b.getDaysSupply()).append(", ");
							}
							tempQty.append(b.getQuantity()).append(", ");
						}
						if (tempItems.toString().length() > 0)
							obj.setItem(tempItems.toString().substring(0, tempItems.toString().length()-2));
						if (tempDispensedItems.toString().length() > 0)
							obj.setDispensedItems(tempDispensedItems.toString().substring(0, tempDispensedItems.toString().length()-2));
						if (tempRxStr.toString().length() > 0)
							obj.setRxNumber(tempRxStr.toString().substring(0, tempRxStr.toString().length()-2));
						if (tempQty.toString().length() > 0)
							obj.setQuantity(tempQty.toString().substring(0, tempQty.toString().length()-2));
						if (tempTotRefills.toString().length() > 0)
							obj.setRefills(tempTotRefills.toString().substring(0, tempTotRefills.toString().length()-2));
						if (tempRefillsFilled.toString().length() > 0)
							obj.setRefillsFilled(tempRefillsFilled.toString().substring(0, tempRefillsFilled.toString().length()-2));
						if (tempDaysSupply.toString().length() > 0)
							obj.setDaysSupply(tempDaysSupply.toString().substring(0, tempDaysSupply.toString().length()-2));
					}*/
					phyAssObjList.add(obj);
				}
			}
			
			PrescriptionJSonObj data = new PrescriptionJSonObj();
			data.setData(phyAssObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean getPrescriptionListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, boolean isPdfFile) {
		
//		String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
		
		boolean isGenerated = false;
		try {
			Page<Object[]> prescriptionList = null;
			
			String physician = form.getPhysician();
			String patient = form.getPatient();
			String status = form.getStatus();
			String fromDate = form.getFromRegDate();
			String toDate = form.getToRegDate();
			String group = form.getGroup();
			String rxNo = form.getRxNumber();
			String prescriptionNo = form.getPrescriptionNo();
			String rxPrescriptionNo = form.getRxPrescriptionNo();
			
			if (physician == null) physician = "";
			if (patient == null) patient = "";
			if (status == null) status = "";
			if (fromDate == null) fromDate = "";
			if (toDate == null) toDate = "";
			if (group == null) group = "";
			if (rxNo == null) rxNo = "";
			if (prescriptionNo == null) prescriptionNo = "";
			if (rxPrescriptionNo == null) rxPrescriptionNo = "";
			
			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			int physicianId = 0;
			int groupId = 0;
			int patientId=0;
			if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType()))
			{
				//temp commented on jan 19,2018
				//physicianId = assistantRepo.findOne(user.getUserid()).getPhysicianId();
				physicianId = user.getPhysicianAssistantPhysicianId();
			}
			else if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType()))
				physicianId = user.getUserid();			
			else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType()))
				groupId = groupDirectorRepo.findOne(user.getUserid()).getGroupId();
			else if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType()))
				patientId = user.getUserid();	
			

			Pageable page = new PageRequest(0, Integer.MAX_VALUE);
			try {
					if (physicianId == 0 && groupId == 0 && patientId == 0) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.reportByFilter(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.reportByFilterWithFromDate(physician, patient, status, group, rxNo,rxPrescriptionNo,prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.reportByFilterWithToDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.reportByFilterWithDate(physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), page);
					} else if (physicianId > 0) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.physicianReportByFilter(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.physicianReportByFilterWithFromDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.physicianReportByFilterWithToDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.physicianReportByFilterWithDate(physicianId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), page);
					} else if (groupId > 0) {

						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.groupReportByFilter(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.groupReportByFilterWithFromDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.groupReportByFilterWithToDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.groupReportByFilterWithDate(groupId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), page);

					} else if (patientId > 0) {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.patientReportByFilter(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, page);
						else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.patientReportByFilterWithFromDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate), page);
						else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.patientReportByFilterWithToDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(toDate), page);
						else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
							prescriptionList = prescriptionRep.patientReportByFilterWithDate(patientId, physician, patient, status, group, rxNo,rxPrescriptionNo, prescriptionNo, PharmacyUtil.getSqlDateFromString(fromDate),
									PharmacyUtil.getSqlDateFromString(toDate), page);
					}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = prescriptionList.getContent();
			
			List<PrescriptionReportBean> phyAssObjList = new ArrayList<PrescriptionReportBean>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			/*List<PrescriptionTransaction> accList =null;
			StringBuilder tempPrescriptionStr = new StringBuilder();
			StringBuilder tempRxStr = new StringBuilder();
			StringBuilder tempRxStatus = new StringBuilder();
			StringBuilder tempItems = new StringBuilder();
			StringBuilder tempDispensedItems = new StringBuilder();
			
			StringBuilder tempQty = new StringBuilder();
			StringBuilder tempTotRefills = new StringBuilder();
			StringBuilder tempRefillsFilled = new StringBuilder();
			StringBuilder tempDaysSupply = new StringBuilder();*/
			
			if (prescriptionList != null && prescriptionList.getSize() > 0 ) {
				//  m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemName, o.origintypetext,
				//	t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id
				for (int i=0; i<reportList.size(); i++) {
					PrescriptionReportBean obj = new PrescriptionReportBean();
					if (reportList.get(i)[0] != null)
						obj.setPrescriberNumber((String) reportList.get(i)[0]);
					else
						obj.setPrescriberNumber("");
					if (reportList.get(i)[1] != null)
						obj.setWrittenOn(dt.format((Date) reportList.get(i)[1]) );
					else
						obj.setWrittenOn("");
					if (reportList.get(i)[2] != null)
						obj.setExpireOn(dt.format((Date) reportList.get(i)[2]) );
					else
						obj.setExpireOn("");
					if (reportList.get(i)[3] != null)
						obj.setRxNumber((String) reportList.get(i)[3]);
					else
						obj.setRxNumber("");
					if (reportList.get(i)[4] != null)
						obj.setPhysicianName((String) reportList.get(i)[4]);
					else
						obj.setPhysicianName("");
					if (reportList.get(i)[5] != null)
						obj.setPatientName((String) reportList.get(i)[5]);
					else
						obj.setPatientName("");
					

					if (reportList.get(i)[6] != null)
						obj.setItem((String) reportList.get(i)[6]);
					else
						obj.setItem("");
					
					
					if (reportList.get(i)[7] != null)
						obj.setOrigin((String) reportList.get(i)[7]);
					else
						obj.setOrigin("");
					if (reportList.get(i)[8] != null)
						obj.setQuantity(Integer.toString((Integer) reportList.get(i)[8]) );
					else
						obj.setQuantity("");
					if (reportList.get(i)[9] != null)
						obj.setDaysSupply(Integer.toString((Integer) reportList.get(i)[9]) );
					else
						obj.setDaysSupply("");
					if (reportList.get(i)[10] != null)
						obj.setRefills(Integer.toString((Integer) reportList.get(i)[10]) );
					else
						obj.setRefills("");
					if (reportList.get(i)[11] != null)
						obj.setRefillsRemaining(Integer.toString((Integer) reportList.get(i)[11]) );
					else
						obj.setRefillsRemaining("");
					if (reportList.get(i)[12] != null)
						obj.setRefillsFilled(Integer.toString((Integer) reportList.get(i)[12]) );
					else
						obj.setRefillsFilled("");
					if (reportList.get(i)[13] != null)
						obj.setStatus((String) reportList.get(i)[13]);
					else
						obj.setStatus("");
					
					obj.setPrescriptionId((Integer) reportList.get(i)[14]);
					
					if (reportList.get(i)[15] != null)
						obj.setCre8PrescriptionNo((String) reportList.get(i)[15]);
					else
						obj.setCre8PrescriptionNo("");
					
					if (reportList.get(i)[16] != null)
						obj.setDispensedItems((String) reportList.get(i)[16]);
					else
						obj.setDispensedItems("");
					
					
					
					/*obj.setItem("");
					obj.setRxNumber("");
					obj.setQuantity("");
					obj.setRefills("");
					obj.setRefillsFilled("");
					obj.setDaysSupply("");
					accList = prescriptionTranService.getTransactionByPrescription(prescriptionId);
					
					tempPrescriptionStr=new StringBuilder();
					tempRxStr = new StringBuilder();
					tempRxStatus = new StringBuilder();
					tempItems = new StringBuilder();
					tempDispensedItems = new StringBuilder();
					tempQty = new StringBuilder();
					tempTotRefills = new StringBuilder();
					tempRefillsFilled = new StringBuilder();
					tempDaysSupply = new StringBuilder();
					
					if (accList.size() > 0) {
						for (PrescriptionTransaction b : accList) {
							tempPrescriptionStr.append(b.getCre8PrescriptionNo()).append(", ");
							tempItems.append(b.getItemName()).append(", ");
							if (b.getDispensedItemName() != null && !"".equalsIgnoreCase(b.getDispensedItemName().trim()))
								tempDispensedItems.append(b.getDispensedItemName().trim()).append(", ");
							
							if(!"".equalsIgnoreCase(b.getRxNumber())) {
								tempRxStr.append(b.getRxNumber()).append(", ");
								tempRxStatus.append(b.getRxStatus()).append(", ");
								tempTotRefills.append(b.getRefills()).append(", ");
								tempRefillsFilled.append(b.getRefillsFilled()).append(", ");
								tempDaysSupply.append(b.getDaysSupply()).append(", ");
							}
							tempQty.append(b.getQuantity()).append(", ");
						}
						if (tempItems.toString().length() > 0)
							obj.setItem(tempItems.toString().substring(0, tempItems.toString().length()-2));
						if (tempDispensedItems != null && tempDispensedItems.toString().length() > 0)
							obj.setDispensedItems(tempDispensedItems.toString().substring(0, tempDispensedItems.toString().length()-2));
						if (tempRxStr.toString().length() > 0)
							obj.setRxNumber(tempRxStr.toString().substring(0, tempRxStr.toString().length()-2));
						if (tempQty.toString().length() > 0)
							obj.setQuantity(tempQty.toString().substring(0, tempQty.toString().length()-2));
						if (tempTotRefills.toString().length() > 0)
							obj.setRefills(tempTotRefills.toString().substring(0, tempTotRefills.toString().length()-2));
						if (tempRefillsFilled.toString().length() > 0)
							obj.setRefillsFilled(tempRefillsFilled.toString().substring(0, tempRefillsFilled.toString().length()-2));
						if (tempDaysSupply.toString().length() > 0)
							obj.setDaysSupply(tempDaysSupply.toString().substring(0, tempDaysSupply.toString().length()-2));
					}*/
					phyAssObjList.add(obj);
				}
			}
			JRDataSource itemsJRBean = new JRBeanCollectionDataSource(phyAssObjList);
			
			String rootFilePath = session.getServletContext().getRealPath("/") + File.separator;
            String targetFolder = rootFilePath + File.separator + "Reports" + File.separator + "PrescriptionList";
    		File f = new File(targetFolder);
    		if (!f.exists())
    			f.mkdir();
            
			String companyLogo = rootFilePath + "\\resources\\images\\CRE8-Pharma-logo.png";
			String outputFileName = "Prescription_List";
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("companyLogo", companyLogo);
            if (isPdfFile)
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
            else
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(rootFilePath + "\\JASP-RPT\\Reports\\prescription_list.jasper", parameters, itemsJRBean);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (isPdfFile) {
            	response.setContentType("application/pdf");
            	response.setHeader("Content-Disposition", "attachment;filename="+outputFileName+".pdf");
            	
            	byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            	
            	response.getOutputStream().write(pdfBytes);
            	response.getOutputStream().flush();
            	response.getOutputStream().close();
            	response.flushBuffer();
            } else {
        		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        		response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

            	JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            	xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFileName+".xlsx"));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        		xlsxExporter.exportReport();
        		
        		response.getOutputStream().write(os.toByteArray());
        		response.getOutputStream().flush();
        		response.getOutputStream().close();
        		response.flushBuffer();
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isGenerated;
	}

	public String getOrderListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician, 
			String patient, String fromDate, String toDate, String status, String group, String orderNo, String rxNumber, HttpSession session) {
		
		if (physician == null) physician = "";
		if (patient == null) patient = "";
		if (fromDate == null) fromDate = "";
		if (toDate == null) toDate = "";
		if (status == null) status = "";
		if (group == null) group = "";
		if (orderNo == null) orderNo = "";
		if (rxNumber == null) rxNumber = "";
		
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<Object[]> orderList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;
			String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
			
			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			int physicianId = 0;
			int groupId = 0;
			int patientId=0;
			if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType()))
			{
				//temp commented on jan 19,2018
				//physicianId = assistantRepo.findOne(user.getUserid()).getPhysicianId();
				physicianId = user.getPhysicianAssistantPhysicianId();
			}
			else if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType()))
				physicianId = user.getUserid();			
			else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType()))
				groupId = groupDirectorRepo.findOne(user.getUserid()).getGroupId();
			else if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType()))
				patientId = user.getUserid();	
		
			try {
				if (physicianId == 0 && groupId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.reportCountByFilter(physician, patient, status, group, orderNo, rxNumber).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.reportCountByFilterWithFromDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.reportCountByFilterWithToDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.reportCountByFilterWithDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				} else if (physicianId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.physicianReportCountByFilter(physicianId, physician, patient, status, group, orderNo, rxNumber).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.physicianReportCountByFilterWithFromDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.physicianReportCountByFilterWithToDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.physicianReportCountByFilterWithDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				} else if (groupId > 0) {

					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.groupReportCountByFilter(groupId, physician, patient, status, group, orderNo, rxNumber).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.groupReportCountByFilterWithFromDate(groupId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.groupReportCountByFilterWithToDate(groupId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.groupReportCountByFilterWithDate(groupId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();

				} else if (patientId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.patientReportCountByFilter(patientId, physician, patient, status, group, orderNo, rxNumber).size();
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						total = orderRepo.patientReportCountByFilterWithFromDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate)).size();
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.patientReportCountByFilterWithToDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate)).size();
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						total = orderRepo.patientReportCountByFilterWithDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate)).size();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			pageNumber = start / length;
			page = new PageRequest(pageNumber, length, dir, "id");
			
			try {
				if (physicianId == 0 && groupId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilter(physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilterWithFromDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilterWithToDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilterWithDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else if (physicianId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilter(physicianId, physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilterWithFromDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilterWithToDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilterWithDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else if (groupId > 0) {
					// will work
					
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.groupReportByFilter(groupId, physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.groupReportByFilterWithFromDate(groupId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.groupReportByFilterWithToDate(groupId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.groupReportByFilterWithDate(groupId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);				
					
				} else if (patientId  > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilter(patientId, physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilterWithFromDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilterWithToDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilterWithDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = orderList.getContent();
			StringBuilder tempRxStr = new StringBuilder();
			StringBuilder tempOrderNo = new StringBuilder();
			StringBuilder tempOrderStatus = new StringBuilder();
			StringBuilder tempItemName = new StringBuilder();
			String medicationDispensed="";
			
			List<OrderForm> phyAssObjList = new ArrayList<OrderForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			if (orderList != null && orderList.getSize() > 0 ) {
				/*m.order_number, m.order_date, t.rx_number, p.physician_name, pat.patient_name,
				concat(t.dispensed_name, " ", t.dispensed_drug, " ", t.dispensed_quantity, " ", t.dispensed_unit) as medicationDispensed,
				t.refills_filled, t.days_supply, t.refills_remaining, t.rx_status */
				for (int i=0; i<reportList.size(); i++) {
					OrderForm obj = new OrderForm();
					
					// orderNumber
					if (reportList.get(i)[0] != null)
						obj.setOrderNumber((String) reportList.get(i)[0] );
					else
						obj.setOrderNumber("");

					// orderDate
					if (reportList.get(i)[1] != null)
						obj.setOrderDate(dt.format((Date) reportList.get(i)[1]) );
					else
						obj.setOrderDate("");

					// rxNumbers
					if (reportList.get(i)[2] != null)
						obj.setRxNumbers((String) reportList.get(i)[2]) ;
					else
						obj.setRxNumbers("");

					// physicianName
					if (reportList.get(i)[3] != null)
						obj.setPhysicianName((String) reportList.get(i)[3]);
					else
						obj.setPhysicianName("");

					// patientName
					if (reportList.get(i)[4] != null)
						obj.setPatientName((String) reportList.get(i)[4]);
					else
						obj.setPatientName("");
					
					// medicationDispensed
					if (reportList.get(i)[3] != null)
						obj.setMedicationDispensed((String) reportList.get(i)[5]);
					else
						obj.setMedicationDispensed("");
					
					// refillsFilled
					if (reportList.get(i)[6] != null)
						obj.setRefillsFilled(Integer.toString((Integer) reportList.get(i)[6]) );
					else
						obj.setRefillsFilled("");
					
					// daysSupply
					if (reportList.get(i)[7] != null)
						obj.setDaysSupply(Integer.toString((Integer) reportList.get(i)[7]) );
					else
						obj.setDaysSupply("");
					
					// refillRemaining
					if (reportList.get(i)[8] != null)
						obj.setRefillRemaining(Integer.toString((Integer) reportList.get(i)[8]) );
					else
						obj.setRefillRemaining("");
					
					// status
					if (reportList.get(i)[9] != null)
						obj.setStatus((String) reportList.get(i)[9]) ;
					else
						obj.setStatus("");
					
					tempRxStr = new StringBuilder();
					tempOrderNo = new StringBuilder();
					tempOrderStatus = new StringBuilder();
					tempItemName = new StringBuilder();
					int orderId = (Integer) reportList.get(i)[10];
										
					obj.setOrderId(orderId);
					
					
					List<OrderTransaction> tran = new ArrayList<OrderTransaction>();
					if ("".equalsIgnoreCase(status))
						tran = orderTranRepo.findByOrderId(orderId);
					else
						tran = orderTranRepo.findByOrderIdAndRxStatus(orderId, status);
					medicationDispensed="";
					if (tran.size() > 0) {
						for (OrderTransaction t: tran) {
							if (t.getRxNumber()!=null && !"".equalsIgnoreCase(t.getRxNumber()) && t.getRefillNumber()==0)
								tempRxStr.append(t.getRxNumber()).append(", ");
							else if (t.getRxNumber()!=null && !"".equalsIgnoreCase(t.getRxNumber()) && t.getRefillNumber()>0)
								tempRxStr.append(t.getRxNumber()+"-"+t.getRefillNumber()).append(", ");
							
							if (t.getPrescriptionTranId()>0 && presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber()!=null && !"".equalsIgnoreCase(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber()))
								tempOrderNo.append(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber()).append(", ");
							if (t.getRxStatus()!=null && !"".equalsIgnoreCase(t.getRxStatus()))
								tempOrderStatus.append(t.getRxStatus()).append(", ");
							
							if(t.getPrescriptionTranId()>0)
								obj.setOrderNumbers(defaultPrescriptionNoFormat+prescriptionRep.findOne(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId()).getPrescriptionNumber()+"");
							
							if(t.getDispensedName()!=null && t.getDispensedName().length()>0)
								medicationDispensed=t.getDispensedName();
							if(t.getDispensedDrug()!=null && t.getDispensedDrug().length()>0)
								medicationDispensed+="-"+t.getDispensedDrug();
							if(t.getDispensedQuantity()>0)
								medicationDispensed+="-"+t.getDispensedQuantity();
							if(t.getDispensedUnit()!=null && t.getDispensedUnit().length()>0)
								medicationDispensed+="-"+t.getDispensedUnit();
							
							obj.setMedicationDispensed(medicationDispensed);
							obj.setRefillsFilled(t.getRefillsFilled()+"");
							obj.setDaysSupply(t.getDaysSupply()+"");
							obj.setPriorityType(t.getPriorityType());
							
							if (!"".equalsIgnoreCase(medicationDispensed))
								tempItemName.append(medicationDispensed).append(", ");
						}
						if (tempItemName.toString().length() > 0)
							obj.setMedicationDispensed(tempItemName.toString().substring(0, tempItemName.toString().length()-2));
						if (tempRxStr.toString().length() > 0)
							obj.setRxNumbers(tempRxStr.toString().substring(0, tempRxStr.toString().length()-2));
						if (tempOrderStatus.toString().length() > 0)
							obj.setStatus(tempOrderStatus.toString().substring(0, tempOrderStatus.toString().length()-2));
					}
					phyAssObjList.add(obj);
				}
			}
			
			OrderJSonObj data = new OrderJSonObj();
			data.setData(phyAssObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public boolean getOrderListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, boolean isPdfFile) {
		
		boolean isGenerated = false;
		try {
			Page<Object[]> orderList = null;

			String physician = form.getPhysician();
			String patient = form.getPatient();
			String status = form.getStatus();
			String fromDate = form.getFromRegDate();
			String toDate = form.getToRegDate();
			String group = form.getGroup();
			String orderNo = form.getOrderNo();
			String rxNumber = form.getRxNumber();
			
			if (physician == null) physician = "";
			if (patient == null) patient = "";
			if (status == null) status = "";
			if (fromDate == null) fromDate = "";
			if (toDate == null) toDate = "";
			if (group == null) group = "";
			if (orderNo == null) orderNo = "";
			if (rxNumber == null) rxNumber = "";
			String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
			
			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			int physicianId = 0;
			int groupId = 0;
			int patientId=0;
			if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType()))
			{
				//temp commented on jan 19,2018
				//physicianId = assistantRepo.findOne(user.getUserid()).getPhysicianId();
				physicianId = user.getPhysicianAssistantPhysicianId();
			}
			else if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType()))
				physicianId = user.getUserid();			
			else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType()))
				groupId = groupDirectorRepo.findOne(user.getUserid()).getGroupId();
			else if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType()))
				patientId = user.getUserid();	
			
			Pageable page = new PageRequest(0, Integer.MAX_VALUE);
			try {
				if (physicianId == 0 && groupId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilter(physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilterWithFromDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilterWithToDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.reportByFilterWithDate(physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else if (physicianId > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilter(physicianId, physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilterWithFromDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilterWithToDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.physicianReportCountByFilterWithDate(physicianId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				} else if (groupId > 0) {
					// will work
				} else if (patientId  > 0) {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilter(patientId, physician, patient, status, group, orderNo, rxNumber, page);
					else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilterWithFromDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
					else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilterWithToDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
					else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
						orderList = orderRepo.patientReportByFilterWithDate(patientId, physician, patient, status, group, orderNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
								PharmacyUtil.getSqlDateFromString(toDate), page);
				}			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = orderList.getContent();
			StringBuilder tempRxStr = new StringBuilder();
			StringBuilder tempOrderNo = new StringBuilder();
			StringBuilder tempOrderStatus = new StringBuilder();
			StringBuilder tempItemName = new StringBuilder();
			String medicationDispensed="";
			
			List<OrderForm> phyAssObjList = new ArrayList<OrderForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			
			if (orderList != null && orderList.getSize() > 0 ) {
				/*m.order_number, m.order_date, t.rx_number, p.physician_name, pat.patient_name,
				concat(t.dispensed_name, " ", t.dispensed_drug, " ", t.dispensed_quantity, " ", t.dispensed_unit) as medicationDispensed,
				t.refills_filled, t.days_supply, t.refills_remaining, t.rx_status */
				for (int i=0; i<reportList.size(); i++) {
					OrderForm obj = new OrderForm();
					
					// orderNumber
					if (reportList.get(i)[0] != null)
						obj.setOrderNumber((String) reportList.get(i)[0] );
					else
						obj.setOrderNumber("");

					// orderDate
					if (reportList.get(i)[1] != null)
						obj.setOrderDate(dt.format((Date) reportList.get(i)[1]) );
					else
						obj.setOrderDate("");

					// rxNumbers
					if (reportList.get(i)[2] != null)
						obj.setRxNumbers((String) reportList.get(i)[2]) ;
					else
						obj.setRxNumbers("");

					// physicianName
					if (reportList.get(i)[3] != null)
						obj.setPhysicianName((String) reportList.get(i)[3]);
					else
						obj.setPhysicianName("");

					// patientName
					if (reportList.get(i)[4] != null)
						obj.setPatientName((String) reportList.get(i)[4]);
					else
						obj.setPatientName("");
					
					// medicationDispensed
					if (reportList.get(i)[3] != null)
						obj.setMedicationDispensed((String) reportList.get(i)[5]);
					else
						obj.setMedicationDispensed("");
					
					// refillsFilled
					if (reportList.get(i)[6] != null)
						obj.setRefillsFilled(Integer.toString((Integer) reportList.get(i)[6]) );
					else
						obj.setRefillsFilled("");
					
					// daysSupply
					if (reportList.get(i)[7] != null)
						obj.setDaysSupply(Integer.toString((Integer) reportList.get(i)[7]) );
					else
						obj.setDaysSupply("");
					
					// refillRemaining
					if (reportList.get(i)[8] != null)
						obj.setRefillRemaining(Integer.toString((Integer) reportList.get(i)[8]) );
					else
						obj.setRefillRemaining("");
					
					// status
					if (reportList.get(i)[9] != null)
						obj.setStatus((String) reportList.get(i)[9]) ;
					else
						obj.setStatus("");
					
					
					tempRxStr = new StringBuilder();
					tempOrderNo = new StringBuilder();
					tempOrderStatus = new StringBuilder();
					tempItemName = new StringBuilder();
					int orderId = (Integer) reportList.get(i)[10];
					
					List<OrderTransaction> tran = new ArrayList<OrderTransaction>();
					if ("".equalsIgnoreCase(status))
						tran = orderTranRepo.findByOrderId(orderId);
					else
						tran = orderTranRepo.findByOrderIdAndRxStatus(orderId, status);
					medicationDispensed="";
					if (tran.size() > 0) {
						for (OrderTransaction t: tran) {
							if (t.getRxNumber()!=null && !"".equalsIgnoreCase(t.getRxNumber()) && t.getRefillNumber()==0)
								tempRxStr.append(t.getRxNumber()).append(", ");
							else if (t.getRxNumber()!=null && !"".equalsIgnoreCase(t.getRxNumber()) && t.getRefillNumber()>0)
								tempRxStr.append(t.getRxNumber()+"-"+t.getRefillNumber()).append(", ");
							
							if (t.getPrescriptionTranId()>0 && presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber()!=null && !"".equalsIgnoreCase(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber()))
								tempOrderNo.append(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber()).append(", ");
							if (t.getRxStatus()!=null && !"".equalsIgnoreCase(t.getRxStatus()))
								tempOrderStatus.append(t.getRxStatus()).append(", ");
							
							if(t.getPrescriptionTranId()>0)
								obj.setOrderNumbers(defaultPrescriptionNoFormat+prescriptionRep.findOne(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId()).getPrescriptionNumber()+"");
							
							if(t.getDispensedName()!=null && t.getDispensedName().length()>0)
								medicationDispensed=t.getDispensedName();
							if(t.getDispensedDrug()!=null && t.getDispensedDrug().length()>0)
								medicationDispensed+="-"+t.getDispensedDrug();
							if(t.getDispensedQuantity()>0)
								medicationDispensed+="-"+t.getDispensedQuantity();
							if(t.getDispensedUnit()!=null && t.getDispensedUnit().length()>0)
								medicationDispensed+="-"+t.getDispensedUnit();
							
							obj.setMedicationDispensed(medicationDispensed);
							obj.setRefillsFilled(t.getRefillsFilled()+"");
							obj.setDaysSupply(t.getDaysSupply()+"");
							obj.setPriorityType(t.getPriorityType());
							
							if (!"".equalsIgnoreCase(medicationDispensed))
								tempItemName.append(medicationDispensed).append(", ");
						}
						if (tempItemName.toString().length() > 0)
							obj.setMedicationDispensed(tempItemName.toString().substring(0, tempItemName.toString().length()-2));
						if (tempRxStr.toString().length() > 0)
							obj.setRxNumbers(tempRxStr.toString().substring(0, tempRxStr.toString().length()-2));
						if (tempOrderStatus.toString().length() > 0)
							obj.setStatus(tempOrderStatus.toString().substring(0, tempOrderStatus.toString().length()-2));
					}					
					
					phyAssObjList.add(obj);
				}
			}
			JRDataSource itemsJRBean = new JRBeanCollectionDataSource(phyAssObjList);
			
			String rootFilePath = session.getServletContext().getRealPath("/") + File.separator;
            String targetFolder = rootFilePath + File.separator + "Reports" + File.separator + "OrderList";
    		File f = new File(targetFolder);
    		if (!f.exists())
    			f.mkdir();
            
			String companyLogo = rootFilePath + "\\resources\\images\\CRE8-Pharma-logo.png";
			String outputFileName = "Order_List";
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("companyLogo", companyLogo);
            if (isPdfFile)
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
            else
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(rootFilePath + "\\JASP-RPT\\Reports\\order_list.jasper", parameters, itemsJRBean);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (isPdfFile) {
            	response.setContentType("application/pdf");
            	response.setHeader("Content-Disposition", "attachment;filename="+outputFileName+".pdf");
            	
            	byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            	
            	response.getOutputStream().write(pdfBytes);
            	response.getOutputStream().flush();
            	response.getOutputStream().close();
            	response.flushBuffer();
            } else {
        		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        		response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

            	JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            	xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFileName+".xlsx"));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        		xlsxExporter.exportReport();
        		
        		response.getOutputStream().write(os.toByteArray());
        		response.getOutputStream().flush();
        		response.getOutputStream().close();
        		response.flushBuffer();
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isGenerated;
	}

	public String getInvoiceListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir,
			String physician, String patient, String fromDate, String toDate, String group, String invoiceNo, String rxNumber, HttpSession session) {

		if (physician == null) physician = "";
		if (patient == null) patient = "";
		if (fromDate == null) fromDate = "";
		if (toDate == null) toDate = "";
		if (group == null) group = "";
		if (invoiceNo == null) invoiceNo = "";
		if (rxNumber == null) rxNumber = "";
		
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<Object[]> invoiceList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;
		
			try {
				if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
					total = invoiceRepo.reportCountByFilter(physician, patient, group, invoiceNo, rxNumber);
				else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
					total = invoiceRepo.reportCountByFilterWithFromDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate));
				else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
					total = invoiceRepo.reportCountByFilterWithToDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate));
				else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
					total = invoiceRepo.reportCountByFilterWithDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
							PharmacyUtil.getSqlDateFromString(toDate));
			} catch(Exception e) {
				e.printStackTrace();
			}
			pageNumber = start / length;
			page = new PageRequest(pageNumber, length, dir, "id");
			
			try {
				if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilter(physician, patient, group, invoiceNo, rxNumber, page);
				else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilterWithFromDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
				else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilterWithToDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
				else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilterWithDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
							PharmacyUtil.getSqlDateFromString(toDate), page);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = invoiceList.getContent();
			
			List<InvoiceForm> phyAssObjList = new ArrayList<InvoiceForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			StringBuffer sb = new StringBuffer();
			
			if (invoiceList != null && invoiceList.getSize() > 0 ) {
				// m.invoiceNumber, m.invoiceDate, t.rxNumber, p.physicianName, pat.patientName, t.item, t.quantity, m.subTotal, m.tax, m.total 
				for (int i=0; i<reportList.size(); i++) {
					InvoiceForm obj = new InvoiceForm();

					obj.setInvoiceId((Integer) reportList.get(i)[10]);
					
					sb = new StringBuffer();
					List<InvoiceTransaction> tranList = invoiceTranRepo.findByInvoiceId((Integer) reportList.get(i)[10]);
					if (tranList != null && tranList.size() > 0) {
						for (InvoiceTransaction t: tranList) {
							sb.append(t.getItem()).append(", ");
						}
					}
					
					// invoiceNo
					if (reportList.get(i)[0] != null)
						obj.setInvoiceNo((String) reportList.get(i)[0] );
					else
						obj.setInvoiceNo("");

					// invoiceDateStr
					if (reportList.get(i)[1] != null)
						obj.setInvoiceDateStr(dt.format((Date) reportList.get(i)[1]) );
					else
						obj.setInvoiceDateStr("");

					// rxNumber
					if (reportList.get(i)[2] != null)
						obj.setRxNumber((String) reportList.get(i)[2]) ;
					else
						obj.setRxNumber("");

					// physicianName
					if (reportList.get(i)[3] != null)
						obj.setPhysicianName((String) reportList.get(i)[3]);
					else
						obj.setPhysicianName("");

					// patientName
					if (reportList.get(i)[4] != null)
						obj.setPatientName((String) reportList.get(i)[4]);
					else
						obj.setPatientName("");

					// itemInvoiced
					if (sb.toString().length() > 0)
						obj.setItemInvoiced(sb.toString().substring(0, sb.toString().length()-2));
					else
						obj.setItemInvoiced("");
					
					// quantity
					if (reportList.get(i)[6] != null)
						obj.setQuantity(Integer.toString((Integer) reportList.get(i)[6]) );
					else
						obj.setQuantity("");
					
					// subtotal
					if (reportList.get(i)[7] != null)
						obj.setSubtotal((Double) reportList.get(i)[7]);
					else
						obj.setSubtotal(0);
					
					// tax
					if (reportList.get(i)[8] != null)
						obj.setTax((Double) reportList.get(i)[8]);
					else
						obj.setTax(0);
					
					// total
					if (reportList.get(i)[9] != null)
						obj.setTotal((Double) reportList.get(i)[9]) ;
					else
						obj.setTotal(0);
					
					// paymentstatus
					if (reportList.get(i)[11] != null)
						obj.setPaymentstatus((String) reportList.get(i)[11]);
					else
						obj.setPaymentstatus("");
					
					// paymenttype
					if (reportList.get(i)[12] != null)
						obj.setPaymenttype((String) reportList.get(i)[12]);
					else
						obj.setPaymenttype("");
					
					phyAssObjList.add(obj);
				}
			}
			
			InvoiceJSonObj data = new InvoiceJSonObj();
			data.setData(phyAssObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

	public boolean getInvoiceListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, boolean isPdfFile) {
		
		boolean isGenerated = false;
		try {
			Page<Object[]> invoiceList = null;

			String physician = form.getPhysician();
			String patient = form.getPatient();
			String status = form.getStatus();
			String fromDate = form.getFromRegDate();
			String toDate = form.getToRegDate();
			String group = form.getGroup();
			String invoiceNo = form.getInvoiceNo();
			String rxNumber = form.getRxNumber();
			
			
			if (physician == null) physician = "";
			if (patient == null) patient = "";
			if (status == null) status = "";
			if (fromDate == null) fromDate = "";
			if (toDate == null) toDate = "";
			if (group == null) group = "";
			if (invoiceNo == null) invoiceNo = "";
			if (rxNumber == null) rxNumber = "";
			
			Pageable page = new PageRequest(0, Integer.MAX_VALUE);
			try {
				if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilter(physician, patient, group, invoiceNo, rxNumber, page);
				else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilterWithFromDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate), page);
				else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilterWithToDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(toDate), page);
				else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate))
					invoiceList = invoiceRepo.reportByFilterWithDate(physician, patient, group, invoiceNo, rxNumber, PharmacyUtil.getSqlDateFromString(fromDate),
							PharmacyUtil.getSqlDateFromString(toDate), page);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<Object[]> reportList = invoiceList.getContent();
			List<InvoiceForm> phyAssObjList = new ArrayList<InvoiceForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			StringBuffer sb = new StringBuffer();
			
			if (reportList != null && reportList.size() > 0 ) {
				// m.invoiceNumber, m.invoiceDate, t.rxNumber, p.physicianName, pat.patientName, t.item, t.quantity, m.subTotal, m.tax, m.total 
				for (int i=0; i<reportList.size(); i++) {
					InvoiceForm obj = new InvoiceForm();

					sb = new StringBuffer();
					List<InvoiceTransaction> tranList = invoiceTranRepo.findByInvoiceId((Integer) reportList.get(i)[10]);
					if (tranList != null && tranList.size() > 0) {
						for (InvoiceTransaction t: tranList) {
							sb.append(t.getItem()).append(", ");
						}
					}
					
					// invoiceNo
					if (reportList.get(i)[0] != null)
						obj.setInvoiceNo((String) reportList.get(i)[0] );
					else
						obj.setInvoiceNo("");

					// invoiceDateStr
					if (reportList.get(i)[1] != null)
						obj.setInvoiceDateStr(dt.format((Date) reportList.get(i)[1]) );
					else
						obj.setInvoiceDateStr("");

					// rxNumber
					if (reportList.get(i)[2] != null)
						obj.setRxNumber((String) reportList.get(i)[2]) ;
					else
						obj.setRxNumber("");

					// physicianName
					if (reportList.get(i)[3] != null)
						obj.setPhysicianName((String) reportList.get(i)[3]);
					else
						obj.setPhysicianName("");

					// patientName
					if (reportList.get(i)[4] != null)
						obj.setPatientName((String) reportList.get(i)[4]);
					else
						obj.setPatientName("");

					// itemInvoiced
					if (sb.toString().length() > 0)
						obj.setItemInvoiced(sb.toString().substring(0, sb.toString().length()-2));
					else
						obj.setItemInvoiced("");
					
					// quantity
					if (reportList.get(i)[6] != null)
						obj.setQuantity(Integer.toString((Integer) reportList.get(i)[6]) );
					else
						obj.setQuantity("");
					
					// subtotal
					if (reportList.get(i)[7] != null)
						obj.setSubtotal((Double) reportList.get(i)[7]);
					else
						obj.setSubtotal(0);
					
					// tax
					if (reportList.get(i)[8] != null)
						obj.setTax((Double) reportList.get(i)[8]);
					else
						obj.setTax(0);
					
					// total
					if (reportList.get(i)[9] != null)
						obj.setTotal((Double) reportList.get(i)[9]) ;
					else
						obj.setTotal(0);
					
					// paymentstatus
					if (reportList.get(i)[11] != null)
						obj.setPaymentstatus((String) reportList.get(i)[11]);
					else
						obj.setPaymentstatus("");
					
					// paymenttype
					if (reportList.get(i)[12] != null)
						obj.setPaymenttype((String) reportList.get(i)[12]);
					else
						obj.setPaymenttype("");
					
					phyAssObjList.add(obj);
				}
			}
			JRDataSource itemsJRBean = new JRBeanCollectionDataSource(phyAssObjList);
			
			String rootFilePath = session.getServletContext().getRealPath("/") + File.separator;
            String targetFolder = rootFilePath + File.separator + "Reports" + File.separator + "InvoiceList";
    		File f = new File(targetFolder);
    		if (!f.exists())
    			f.mkdir();
            
			String companyLogo = rootFilePath + "\\resources\\images\\CRE8-Pharma-logo.png";
			String outputFileName = "Invoice_List";
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("companyLogo", companyLogo);
            if (isPdfFile)
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
            else
            	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(rootFilePath + "\\JASP-RPT\\Reports\\invoice_list.jasper", parameters, itemsJRBean);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (isPdfFile) {
            	response.setContentType("application/pdf");
            	response.setHeader("Content-Disposition", "attachment;filename="+outputFileName+".pdf");
            	
            	byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            	
            	response.getOutputStream().write(pdfBytes);
            	response.getOutputStream().flush();
            	response.getOutputStream().close();
            	response.flushBuffer();
            } else {
        		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        		response.setHeader("Content-Disposition", "attachment; filename="+outputFileName+".xlsx");

            	JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            	xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFileName+".xlsx"));
        		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        		xlsxExporter.exportReport();
        		
        		response.getOutputStream().write(os.toByteArray());
        		response.getOutputStream().flush();
        		response.getOutputStream().close();
        		response.flushBuffer();
            }
            isGenerated = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isGenerated;
	}
	
}
