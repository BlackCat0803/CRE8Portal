package com.pharma.core.pharmaservices.invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.invoice.InvoiceForm;
import com.pharma.core.formBean.invoice.InvoiceJSonObj;
import com.pharma.core.formBean.invoice.InvoiceTranForm;
import com.pharma.core.model.invoice.InvoiceMaster;
import com.pharma.core.model.invoice.InvoiceTransaction;
import com.pharma.core.model.order.OrderTransaction;
import com.pharma.core.repository.invoice.InvoiceMasterRepository;
import com.pharma.core.repository.invoice.InvoiceTransactionRepository;
import com.pharma.core.repository.order.OrderMasterRepository;
import com.pharma.core.repository.order.OrderTransactionRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.prescription.PrescriptionRepository;
import com.pharma.core.repository.prescription.PrescriptionTranRepository;
import com.pharma.core.util.PharmacyUtil;

/**
 * This is an implementation class that loads and stores the invoice data
 *
 */
@Service("invoiceMasterService")
public class InvoiceMasterServiceImpl implements InvoiceMasterService {

	@Autowired
	InvoiceMasterRepository invoiceMasterRepo;
	
	@Autowired
	InvoiceTransactionRepository invoiceTranRepo;
	
	@Autowired
	PhysicianAccountRespository physicianRep;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	@Autowired
	PrescriptionRepository presMasterRepo;
	
	@Autowired
	PrescriptionTranRepository presTranRepo;
	
	@Autowired
	OrderMasterRepository orderMasterRepo;
	
	@Autowired
	OrderTransactionRepository orderTranRepo;
	
	@Autowired
	private Environment env;
	
	public String getInvoiceSummaryData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String patientName,
			 String phyName, String invoiceNo, String fromDate, String toDate, int physicianId, int patientId, int groupId,  String rxNoSearch) {

		if (invoiceNo == null) invoiceNo = "";
		if (fromDate == null)  fromDate = "";
		if (toDate == null)  toDate = "";
		if (patientName == null) patientName = "";
		if (phyName == null) phyName = "";
		if (rxNoSearch == null) rxNoSearch = "";
		
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<InvoiceMaster> invoiceList = null;
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;

		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch) ) {
					total = invoiceMasterRepo.countAllByGroupId(groupId);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllGroupWithoutDate(invoiceNo, patientName, phyName, groupId, rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllGroupWithoutFromDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								groupId, rxNoSearch);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllGroupWithoutToDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(toDate), 
								groupId, rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllGroupWithDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								PharmacyUtil.getSqlDateFromString(toDate), groupId, rxNoSearch);
					}
				}
			} else  if (physicianId == 0 && patientId == 0) {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch) ) {
					total = invoiceMasterRepo.findAll().size();
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllWithoutDate(invoiceNo, patientName, phyName, rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllWithoutFromDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), rxNoSearch);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllWithoutToDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countAllWithDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch);
					}
				}
			} else if (physicianId == 0 && patientId > 0) {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)&& "".equalsIgnoreCase(rxNoSearch) ) {
					total = invoiceMasterRepo.findAllByPatientId(patientId);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPatientWithoutDate(patientId, invoiceNo, patientName, phyName, rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPatientWithoutFromDate(patientId, invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(fromDate), rxNoSearch);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPatientWithoutToDate(patientId, invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPatientWithDate(patientId, invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(fromDate), PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch);
					}
				}
			} else {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch) ) {
					total = invoiceMasterRepo.findAllByPhysicianId(physicianId);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPhysicianWithoutDate(physicianId, invoiceNo, patientName, phyName, rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPhysicianWithoutFromDate(physicianId, invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(fromDate), rxNoSearch);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPhysicianWithoutToDate(physicianId, invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						total = invoiceMasterRepo.countPhysicianWithDate(physicianId, invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(fromDate), PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch);
					}
				}
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
			if (groupId > 0) {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch) ) {
					invoiceList = invoiceMasterRepo.getAllGroupRecords(groupId, page);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllGroupWithoutDate(invoiceNo, patientName, phyName, groupId, rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllGroupWithFromDate(invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(fromDate), groupId, rxNoSearch, page);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllGroupWithToDate(invoiceNo, patientName, phyName, 
								PharmacyUtil.getSqlDateFromString(toDate), groupId, rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllGroupWithDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								PharmacyUtil.getSqlDateFromString(toDate), groupId, rxNoSearch, page);
					}
				}
			}  else  if (physicianId == 0 && patientId == 0) {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch)) {
					invoiceList = invoiceMasterRepo.getAllRecords(page);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllWithoutDate(invoiceNo, patientName, phyName, rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllWithoutFromDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), rxNoSearch, page);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllWithoutToDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findAllWithDate(invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch, page);
					}
				}
			}else if (physicianId == 0 && patientId > 0) {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch)) {
					invoiceList = invoiceMasterRepo.getPatientRecords(patientId, page);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPatientWithoutDate(patientId, invoiceNo, patientName, phyName, rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPatientWithoutFromDate(patientId, invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), rxNoSearch, page);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPatientWithoutToDate(patientId, invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPatientWithDate(patientId, invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch, page);
					}
				}
			} else {
				if ("".equalsIgnoreCase(invoiceNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyName) 
						&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(rxNoSearch) ) {
					invoiceList = invoiceMasterRepo.getPhysicianRecords(physicianId, page);
				} else {
					if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPhysicianWithoutDate(physicianId, invoiceNo, patientName, phyName, rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPhysicianWithoutFromDate(physicianId, invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), rxNoSearch, page);
					} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPhysicianWithoutToDate(physicianId, invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch, page);
					} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
						invoiceList = invoiceMasterRepo.findPhysicianWithDate(physicianId, invoiceNo, patientName, phyName, PharmacyUtil.getSqlDateFromString(fromDate), 
								PharmacyUtil.getSqlDateFromString(toDate), rxNoSearch, page);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		String rxNo = "";
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		
		List<InvoiceForm> orderFormObjList = new ArrayList<InvoiceForm>();
		if (invoiceList != null && invoiceList.getSize()>0 ) {
			for(InvoiceMaster a: invoiceList) {
				InvoiceForm obj = new InvoiceForm();
				
				obj.setInvoiceId(a.getId());
				obj.setInvoiceNo(a.getInvoiceNumber());
				obj.setInvoiceDateStr( dt.format(a.getInvoiceDate()));
				obj.setPhysicianId(a.getPhysicianId());
				obj.setPhysicianName( physicianRep.findOne(a.getPhysicianId()).getPhysicianName() );
				obj.setPatientName( patientRepo.findOne(a.getPatientId()).getPatientName() );
				obj.setTotal(a.getTotal());
				obj.setInvoiceStatus("Paid");
				
				List<InvoiceTransaction> tranList = invoiceTranRepo.findByInvoiceId(a.getId());
				if (tranList != null && tranList.size() > 0) {
					for (InvoiceTransaction t: tranList) {
						obj.setItemInvoiced(t.getItem());
						obj.setRefillsFilled(t.getNumberOfRefillsFilled()+"");
						obj.setQuantity(t.getQuantity()+"");
						obj.setLotNo(t.getLotNumber());
						obj.setLotExpDate(PharmacyUtil.getStringDateFromSqlDate(t.getExpirationDate()));
						
						rxNo = "";
						if (t.getRxNumber() != null && !"".equalsIgnoreCase(t.getRxNumber()))
								rxNo = t.getRxNumber();

						if(t.getRefillNumber()==0)
							obj.setRxNumber(rxNo);
						else
							obj.setRxNumber(rxNo+"-"+t.getRefillNumber());
					}
				}
				obj.setDT_RowId("row_" + a.getId());
				orderFormObjList.add(obj);
			}
		}
		
		InvoiceJSonObj data = new InvoiceJSonObj();
		data.setData(orderFormObjList);
		data.setDraw(draw);	
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);	
		
		return json2;
	}
	
	
	public InvoiceForm getInvoiceData(int id) {
		InvoiceForm form = new InvoiceForm();
		String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
		try {
			if (id > 0) {
				InvoiceMaster master = invoiceMasterRepo.findOne(id);
				if (master != null) {
					form.setBillingAddress(master.getBillingAddress());
					form.setBillingCity(master.getBillingCity());
					form.setBillingName(master.getBillingName());
					form.setBillingState(master.getBillingState());
					form.setBillingZipcode(master.getBillingZipcode());
					
					form.setInvoiceDate(master.getInvoiceDate());
					form.setInvoiceId(master.getId());
					form.setInvoiceNo(master.getInvoiceNumber());
					
					form.setPatientId(master.getPatientId());
					form.setPhysicianId(master.getPhysicianId());
					form.setPhysicianName( physicianRep.findOne(master.getPhysicianId()).getPhysicianName() );
					form.setPatientName( patientRepo.findOne(master.getPatientId()).getPatientName() );
					
					form.setShippingAddress(master.getShippingAddress());
					form.setShippingCity(master.getShippingCity());
					form.setShippingName(master.getShippingName());
					form.setShippingState(master.getShippingState());
					form.setShippingZipcode(master.getShippingZipcode());
					
					form.setSubtotal(master.getSubTotal());
					form.setTax(master.getTax());
					form.setTotal(master.getTotal());
					form.setWrittenDate(master.getWrittenDate());
					form.setRxNumber(master.getRxNumber());
					
					form.setInvoiceDateStr(PharmacyUtil.getStringDateFromSqlDate(master.getInvoiceDate()));
					form.setWrittenDateStr(PharmacyUtil.getStringDateFromSqlDate(master.getWrittenDate()));
					
					form.setCreatedBy(master.getCreatedBy());
					form.setCreatedUser(master.getCreatedUser());
					form.setCreatedDate(master.getCreatedDate());
					
					form.setLastUpdatedBy(master.getLastUpdatedBy());
					form.setLastUpdatedUser(master.getLastUpdatedUser());
					form.setLastUpdatedDate(master.getLastUpdatedDate());
					
					form.setRemarks(master.getRemarks());
					
					List<InvoiceTranForm> itemList = new ArrayList<InvoiceTranForm>();
					List<InvoiceTransaction> tranList = invoiceTranRepo.findByInvoiceId(id);
					if (tranList != null && tranList.size() > 0) {
						for (InvoiceTransaction t: tranList) {
							InvoiceTranForm tForm = new InvoiceTranForm();
							
							tForm.setId(t.getId());
							tForm.setInvoiceId(t.getInvoiceId());
							tForm.setRxExpirationdate(PharmacyUtil.getStringDateFromSqlDate(t.getExpirationDate()));
							tForm.setRxItem(t.getItem());
							tForm.setRxLotNo(t.getLotNumber());
							tForm.setRxQuantity(t.getQuantity());
							tForm.setRxTotal(t.getTotal());
							tForm.setNoOfRefillsFilled(t.getNumberOfRefillsFilled());
							tForm.setDaysSupply(t.getDaysSupply());
							if(t.getPaymentstatus()==null || t.getPaymentstatus().length()==0)
								tForm.setPaymentstatus("Not Paid");
							else
								tForm.setPaymentstatus(t.getPaymentstatus());
							tForm.setPaymenttype(t.getPaymenttype());
							
							
							if (t.getPrescriptionTranId() > 0)
							{
								List<OrderTransaction> orderTran=orderTranRepo.fetchOrderDetailsByRxRefill(t.getPrescriptionTranId(),t.getScriptType(),t.getRefillNumber());
								form.setOrderId(orderTran.get(0).getOrderId());
								form.setOrderNumber(orderMasterRepo.findOne(form.getOrderId()).getOrderNumber());
								
								/*if (orderTran.get(0).getRxNumber()!=null && !"".equalsIgnoreCase(orderTran.get(0).getRxNumber()) && orderTran.get(0).getRefillNumber()==0)
									form.setOrderNumber(orderTran.get(0).getRxNumber());
								else if (orderTran.get(0).getRxNumber()!=null && !"".equalsIgnoreCase(orderTran.get(0).getRxNumber()) && orderTran.get(0).getRefillNumber()>0)
									form.setOrderNumber(orderTran.get(0).getRxNumber()+"-"+orderTran.get(0).getRefillNumber());*/
								
								
								form.setPrescriptionId(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId());
								
								/*if (orderTranRepo.findOne(t.getPrescriptionTranId()).getRxNumber()!=null && !"".equalsIgnoreCase(orderTranRepo.findOne(t.getPrescriptionTranId()).getRxNumber()) && orderTranRepo.findOne(t.getPrescriptionTranId()).getRefillNumber()==0)
									form.setPrescriptionNumber(orderTranRepo.findOne(t.getPrescriptionTranId()).getRxNumber());
								else if (orderTranRepo.findOne(t.getPrescriptionTranId()).getRxNumber()!=null && !"".equalsIgnoreCase(orderTranRepo.findOne(t.getPrescriptionTranId()).getRxNumber()) && orderTranRepo.findOne(t.getPrescriptionTranId()).getRefillNumber()>0)
									form.setPrescriptionNumber(orderTranRepo.findOne(t.getPrescriptionTranId()).getRxNumber()+"-"+orderTranRepo.findOne(t.getPrescriptionTranId()).getRefillNumber());*/
								
								form.setPrescriptionNumber(defaultPrescriptionNoFormat+ presMasterRepo.findOne(form.getPrescriptionId()).getPrescriptionNumber());
								tForm.setPrescriptionNo(presMasterRepo.findOne(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId()).getPrescriptionNumber()+"-"+ presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionNo());	
							}
							else
							{
								form.setOrderId(0);
								form.setOrderNumber("");
								form.setPrescriptionId(0);
								form.setPrescriptionNumber("");
								tForm.setPrescriptionNo("");
							}

							tForm.setRxNumber(t.getRxNumber());
								
							tForm.setRefillNumber(t.getRefillNumber());
							tForm.setScriptType(t.getScriptType());
							form.setRefillNumber(t.getRefillNumber());
							if(t.getRefillNumber()>0)
								form.setRxNumber(master.getRxNumber()+"-"+t.getRefillNumber());
							itemList.add(tForm);
						}
						form.setItems(itemList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return form;
	}
	

	public boolean generateInvoicePdf(InvoiceForm form, String rootFilePath, HttpSession session, Environment env) {
		
		boolean isReady = false;
		
		try {
			if (form.getInvoiceId() > 0 ) {
				InvoiceForm acc = getInvoiceData(form.getInvoiceId());
				if (acc != null) {
					List<InvoiceForm> formData = new ArrayList<InvoiceForm>();
					formData.add(acc);
					
					JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(formData);
					
			        String targetFolder = rootFilePath + File.separator + form.getInvoiceId();
					File f = new File(rootFilePath);
					if (!f.exists())
						f.mkdir();
					
					f = new File(targetFolder);
					if (!f.exists())
						f.mkdir();
			        
			        String outputFile = targetFolder + File.separatorChar + "Invoice_"+form.getInvoiceId()+".pdf";
			        
			        String realpath = session.getServletContext().getRealPath("/") + File.separator;
			        
					String checkedImagePath = realpath + "\\resources\\images\\checkbox_checked.jpg";
					String unCheckedImagePath = realpath + "\\resources\\images\\checkbox_unchecked.jpg";
					String SUB_REPORT_PATH = realpath + "\\JASP-RPT\\";
					String companyLogo = realpath + "\\resources\\images\\CRE8-Pharma-logo.png";
					
			        Map<String, Object> parameters = new HashMap<String, Object>();
			        parameters.put("checkedImagePath", checkedImagePath);
			        parameters.put("unCheckedImagePath", unCheckedImagePath);
			        parameters.put("SUBREPORT_DIR",SUB_REPORT_PATH); 
			        parameters.put("companyLogo", companyLogo);
			        
			        parameters.put("Company_Name",env.getProperty("invoice.company_name"));
			        parameters.put("Company_add1",env.getProperty("invoice.company_add1"));
			        parameters.put("Company_add2",env.getProperty("invoice.company_add2"));

			        JasperPrint jasperPrint = JasperFillManager.fillReport(realpath + "\\JASP-RPT\\Invoice.jasper", parameters, itemsJRBean);

			        OutputStream outputStream = new FileOutputStream(new File(outputFile));
			        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			        isReady = true;
			        //System.out.println("File Generated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isReady;
		
		
	}
	
}
