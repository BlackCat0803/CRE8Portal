package com.pharma.core.pharmaservices.order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import com.pharma.core.formBean.order.OrderForm;
import com.pharma.core.formBean.order.OrderJSonObj;
import com.pharma.core.formBean.order.OrderTransactionForm;
import com.pharma.core.model.invoice.InvoiceTransaction;
import com.pharma.core.model.order.OrderMaster;
import com.pharma.core.model.order.OrderTransaction;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.pioneer.PointofsaleShipperType;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianGroupService;
import com.pharma.core.repository.invoice.InvoiceMasterRepository;
import com.pharma.core.repository.invoice.InvoiceTransactionRepository;
import com.pharma.core.repository.order.OrderMasterRepository;
import com.pharma.core.repository.order.OrderTransactionRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.pioneer.PointofsaleShipperTypeRepository;
import com.pharma.core.repository.prescription.PrescriptionRepository;
import com.pharma.core.repository.prescription.PrescriptionTranRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the order master data
 */
@Service("orderMasterService")
@PropertySource("classpath:prescription.properties") 
public class OrderMasterServiceImpl implements OrderMasterService {
	
	@Autowired
	OrderMasterRepository orderMasterRepo;
	
	@Autowired
	OrderTransactionRepository orderTranRepo;
	
	@Autowired
	PhysicianAccountRespository physicianRep;
	
	@Autowired
	PhysicianGroupService  phyGroupService;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	@Autowired
	PrescriptionTranRepository presTranRepo;
	
	@Autowired
	PrescriptionRepository presMasterRepo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	PointofsaleShipperTypeRepository posShipperType;
	
	@Autowired
	InvoiceMasterRepository invoiceMasterRepo;
	
	@Autowired
	InvoiceTransactionRepository invoiceTranRepo;
	
	public String getOrderDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String orderNo, String phyname,
			 String patientName, String fromDate, String toDate, String status, int physicianId, int patientId, int groupId, String rxNo) {
		try {
			String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
			// https://stackoverflow.com/questions/1069415/limit-on-the-where-col-in-condition
			// https://stackoverflow.com/questions/39652760/need-to-calculate-count-of-child-table-rows-based-on-same-key-between-master-and
			
			// Query : select * from order_master m join order_transaction t where m.order_id=t.order_id and rx_status like 'Complete' group by t.order_id;
			// rx_status like 'Waiting for %'... rest is same

			// select * from order_master m, order_transaction t, phy_info p, patient_profile pat, prescription_transaction pre
			// where m.order_id=t.order_id and m.patient_id=pat.patient_id and m.physician_id=p.physician_id
			// and t.prescription_tran_id=pre.prescription_tran_id group by m.order_id;

			if (orderNo == null) orderNo = "";				// order_master.order_number like 
			if (phyname == null)  phyname = "";				// order_master.physician_id (phy_info.physician_name)  like
			if (patientName == null)  patientName = "";		// order_master.patient_id (patient_profile.patient_name) like
			if (fromDate == null)  fromDate = "";			// order_master.order_date>= <=
			if (toDate == null)  toDate = "";				// order_master.order_date>= <=
			if (status == null)  status = "";				// order_transaction.rx_status==
			if (rxNo == null) rxNo = "";
			
			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<OrderMaster> orderList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			try {
				if (groupId > 0) {  
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						total = orderMasterRepo.countByGroupId(groupId);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countGroupWithoutDate(groupId, orderNo, patientName, phyname, status, rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countGroupWithoutFromDate(groupId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countGroupWithoutToDate(groupId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countGroupWithDate(groupId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), 
									PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						}
					}
				} else if (physicianId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						total = orderMasterRepo.findAll().size();
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countAllWithoutDate(orderNo, patientName, phyname, status, rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countAllWithoutFromDate(orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countAllWithoutToDate(orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countAllWithDate(orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), 
									PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						}
					}
				} else if (physicianId == 0 && patientId > 0) {
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						total = orderMasterRepo.findAllByPatientId(patientId);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPatientWithoutDate(patientId, orderNo, patientName, phyname, status, rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPatientWithoutFromDate(patientId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPatientWithoutToDate(patientId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPatientWithDate(patientId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), 
									PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						}
					}
				} else {
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						total = orderMasterRepo.getFilterRecordCountByPhysicianId(physicianId);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPhysicianWithoutDate(physicianId, orderNo, patientName, phyname, status, rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPhysicianWithoutFromDate(physicianId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPhysicianWithoutToDate(physicianId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							total = orderMasterRepo.countPhysicianWithDate(physicianId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), 
									PharmacyUtil.getSqlDateFromString(toDate), rxNo);
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
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						orderList = orderMasterRepo.findByGroupId(groupId, page);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findGroupWithoutDate(groupId, orderNo, patientName, phyname, status, rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findGroupWithFromDate(groupId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo, page);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findGroupWithToDate(groupId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findGroupWithDate(groupId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						}
					}
				} else if (physicianId == 0 && patientId == 0) {
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						orderList = orderMasterRepo.getAllRecords(page);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findAllWithoutDate(orderNo, patientName, phyname, status, rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findAllWithoutFromDate(orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo, page);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findAllWithoutToDate(orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findAllWithDate(orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						}
					}
				} else if (physicianId == 0 && patientId > 0) {
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						orderList = orderMasterRepo.getFilterRecordsByPatientId(patientId, page);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPatientWithoutDate(patientId, orderNo, patientName, phyname, status, rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPatientWithoutFromDate(patientId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo, page);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPatientWithoutToDate(patientId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPatientWithDate(patientId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						}
					}
				} else {
					if ("".equalsIgnoreCase(orderNo) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) 
							&& "".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate) && "".equalsIgnoreCase(status) && "".equalsIgnoreCase(rxNo)) {
						orderList = orderMasterRepo.getFilterRecordsByPhysicianId(physicianId,page);
					} else {
						if ("".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPhysicianWithoutDate(physicianId, orderNo, patientName, phyname, status, rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && "".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPhysicianWithoutFromDate(physicianId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), rxNo, page);
						} else if ("".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPhysicianWithoutToDate(physicianId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						} else if (!"".equalsIgnoreCase(fromDate) && !"".equalsIgnoreCase(toDate)) {
							orderList = orderMasterRepo.findPhysicianWithDate(physicianId, orderNo, patientName, phyname, status, PharmacyUtil.getSqlDateFromString(fromDate), PharmacyUtil.getSqlDateFromString(toDate), rxNo, page);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			StringBuilder tempRxStr = new StringBuilder();
			StringBuilder tempOrderNo = new StringBuilder();
			StringBuilder tempOrderStatus = new StringBuilder();
			
			
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			List<OrderForm> orderObjList = new ArrayList<OrderForm>();
			String medicationDispensed="";
			if (orderList != null && orderList.getSize() > 0 ) {
				for(OrderMaster a: orderList) {
					OrderForm obj = new OrderForm();
					obj.setOrderId(a.getId());
					obj.setOrderNumber(a.getOrderNumber());
					obj.setGroupId(a.getGroupId());
					obj.setPatientId(a.getPatientId());
					obj.setPhysicianId(a.getPhysicianId());
					obj.setOrderDate( dt.format(a.getOrderDate()));
					obj.setPhysicianName( physicianRep.findOne(a.getPhysicianId()).getPhysicianName() );
					obj.setPatientName( patientRepo.findOne(a.getPatientId()).getPatientName() );
					
					obj.setDT_RowId("row_" + a.getId());
					
					String groupName = getGroupNamesByPhysicianId(a.getPhysicianId());
					if (groupName.length() > 0)
						obj.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
					else
						obj.setGroupName("");
					
					obj.setRxNumbers("");
					obj.setOrderNumbers("");
					obj.setRxStatuses("");
					
					tempRxStr = new StringBuilder();
					tempOrderNo = new StringBuilder();
					tempOrderStatus = new StringBuilder();
					List<OrderTransaction> tran = new ArrayList<OrderTransaction>();
					if ("".equalsIgnoreCase(status))
						tran = orderTranRepo.findByOrderId(a.getId());
					else
						tran = orderTranRepo.findByOrderIdAndRxStatus(a.getId(), status);
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
								obj.setOrderNumbers(defaultPrescriptionNoFormat+presMasterRepo.findOne(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId()).getPrescriptionNumber()+"");
							
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
							
						}
						if (tempRxStr.toString().length() > 0)
							obj.setRxNumbers(tempRxStr.toString().substring(0, tempRxStr.toString().length()-2));
						/*if (tempOrderNo.toString().length() > 0)
							obj.setOrderNumbers(tempOrderNo.toString().substring(0, tempOrderNo.toString().length()-2));*/
						
						if (tempOrderStatus.toString().length() > 0)
							obj.setRxStatuses(tempOrderStatus.toString().substring(0, tempOrderStatus.toString().length()-2));
					}

					orderObjList.add(obj);
				}
			}

			OrderJSonObj data = new OrderJSonObj();
			data.setData(orderObjList);
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

	private String getGroupNamesByPhysicianId(int id) {
		StringBuilder groupName = new StringBuilder();
		ArrayList<Integer> groupList = new ArrayList<Integer>();
		List<PhysicianGroup> groupTableList = phyGroupService.getActiveRecordsByPhysician(id);
		if (groupTableList.size() > 0) {
			for (PhysicianGroup g: groupTableList) {
				groupList.add(g.getGroupId());
				if (g.getGroupId() >0)
					groupName.append( groupService.getGroupMasterDetails(g.getGroupId()).getGroupName()).append(",");
				else
					groupName.append(",");
			}
		}
		return groupName.toString();
	}

	public OrderForm getOrderDetails(int id) {
		OrderForm form = new OrderForm();
		form.setOrderId(id);
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String medicationDispensed="";
		String medicationPrescribed="";
		
		try {
			if (id > 0) {
				String orderNo = "";
				OrderMaster master = orderMasterRepo.findOne(id);
				if (master != null) {
					form.setOrderNumber(master.getOrderNumber());
					form.setGroupId(master.getGroupId());
					form.setPhysicianId(master.getPhysicianId());
					form.setPatientId(master.getPatientId());
					orderNo = master.getOrderNumber();
					
					form.setPhysicianName( physicianRep.findOne(master.getPhysicianId()).getPhysicianName() );
					form.setPatientName( patientRepo.findOne(master.getPatientId()).getPatientName() );
					String groupName = getGroupNamesByPhysicianId(master.getPhysicianId());
					if (groupName.length() > 0)
						form.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
					else
						form.setGroupName("");
					form.setOrderDate(dt.format(master.getOrderDate()));

					form.setShippingName( patientRepo.findOne(master.getPatientId()).getPatientName() );
					form.setShippingAddress(master.getShippingAddress());
					form.setShippingCity(master.getShippingCity());
					form.setShippingState(master.getShippingState());
					form.setShippingZipCode(master.getShippingZipCode());

					
					form.setCreatedBy(master.getCreatedBy());
					form.setCreatedUser(master.getCreatedUser());
					form.setCreatedDate(master.getCreatedDate());
					
					form.setLastUpdatedBy(master.getLastUpdatedBy());
					form.setLastUpdatedUser(master.getLastUpdatedUser());
					form.setLastUpdatedDate(master.getLastUpdatedDate());
				}
				
				String trackingurl="";
				List<OrderTransactionForm> tranForm = new ArrayList<OrderTransactionForm>();
				List<OrderTransaction> transList = orderTranRepo.findByOrderId(id);
				if (transList.size() >0) {
					for (OrderTransaction t: transList) {
						
						OrderTransactionForm formTran = new OrderTransactionForm();
						
						formTran.setDaysSupply(t.getDaysSupply());
						formTran.setDispensedDrug(t.getDispensedDrug());
						formTran.setDispensedName(t.getDispensedName());
						formTran.setDispensedQuantity(t.getDispensedQuantity());
						formTran.setDispensedUnit(t.getDispensedUnit());
						if (t.getLastFilledDate() != null)
							formTran.setLastFilledDate(dt.format(t.getLastFilledDate()));
						else
							formTran.setLastFilledDate("");
						formTran.setOrderId(orderNo);
						formTran.setOrderTranId(t.getId());
						formTran.setPrescribedDrug(t.getPrescribedDrug());
						formTran.setPrescribedName(t.getPrescribedName());
						formTran.setPrescribedQuantity(t.getPrescribedQuantity());
						formTran.setPrescribedUnit(t.getPrescribedUnit());
						if(t.getDispensedName()!=null && t.getDispensedName().length()>0)
							medicationDispensed=t.getDispensedName();
						if(t.getDispensedDrug()!=null && t.getDispensedDrug().length()>0)
							medicationDispensed+="-"+t.getDispensedDrug();
						if(t.getDispensedQuantity()>0)
							medicationDispensed+="-"+t.getDispensedQuantity();
						if(t.getDispensedUnit()!=null && t.getDispensedUnit().length()>0)
							medicationDispensed+="-"+t.getDispensedUnit();
						
						formTran.setMedicationDispensed(medicationDispensed);
						
						if(t.getPrescribedName()!=null && t.getPrescribedName().length()>0)
							medicationPrescribed=t.getPrescribedName();
						if(t.getPrescribedDrug()!=null && t.getPrescribedDrug().length()>0)
							medicationPrescribed+="-"+t.getPrescribedDrug();
						if(t.getPrescribedQuantity()>0)
							medicationPrescribed+="-"+t.getPrescribedQuantity();
						if(t.getPrescribedUnit()!=null && t.getPrescribedUnit().length()>0)
							medicationPrescribed+="-"+t.getPrescribedUnit();
						formTran.setMedicationPrescribed(medicationPrescribed);
						formTran.setQuantityRemaining(t.getQuantityRemaining());
						formTran.setRefillsAllowed(t.getRefillsAllowed());
						formTran.setRefillsFilled(t.getRefillsFilled());
						formTran.setRefillsRemaining(t.getRefillsRemaining());
						formTran.setRxNumber(t.getRxNumber());
						formTran.setRxStatus(t.getRxStatus());
						formTran.setTrackingNumber(t.getTrackingNumber());
						
						formTran.setPrescriberOrderNumber(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber());
						
						formTran.setInvoiceId(0);
						if (t.getPrescriptionTranId() > 0) {
							List<InvoiceTransaction> invoiceTran = invoiceTranRepo.fetchInvoiceDetailsByRxRefill(t.getPrescriptionTranId(),t.getScriptType(),t.getRefillNumber());
							if (invoiceTran.size() > 0)
								formTran.setInvoiceId(invoiceTran.get(0).getInvoiceId());
						}
						
						if (formTran.getInvoiceId() > 0)
							formTran.setInvoiceNumber(invoiceMasterRepo.findOne(formTran.getInvoiceId()).getInvoiceNumber()+"");
						else
							formTran.setInvoiceNumber("");
						
						formTran.setPrescriptionTranId(t.getPrescriptionTranId());
						formTran.setPriorityType(t.getPriorityType());
						formTran.setLotNumber(t.getLotNumber());
						if (t.getLotExpirationDate() != null)
							formTran.setLotExpirationDate(dt.format(t.getLotExpirationDate()));
						else
							formTran.setLotExpirationDate("");
						formTran.setRxComments(t.getRxComments());
						if (t.getCompletedDate() != null)
							formTran.setCompletedDate(dt.format(t.getCompletedDate()));
						else
							formTran.setCompletedDate("");
						
						formTran.setShipmentstatus(t.getShipmentstatus());
						formTran.setShippingcompany(t.getShippingcompany());
						
						List<PointofsaleShipperType> shipperTypeList = posShipperType.findByShippertypetext(t.getShippingcompany());
						
						if(shipperTypeList!=null && shipperTypeList.size()>0)
						{
							trackingurl=shipperTypeList.get(0).getUrltrackingprod();
							trackingurl=trackingurl.substring(0, trackingurl.length()-3);
							//System.out.println("trackingurl 11111111=============="+trackingurl);
							formTran.setTrackingurl(trackingurl+t.getTrackingNumber());
						}
						formTran.setRefillNumber(t.getRefillNumber());
						formTran.setScriptType(t.getScriptType());
						formTran.setPrescriptionId(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId());
						formTran.setPrescriptionNo(presMasterRepo.findOne(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId()).getPrescriptionNumber()+"-"+ presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionNo());
						
						tranForm.add(formTran);
						
					}
				}
				
				form.setTransactions(tranForm);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	public boolean generateOrderPdf(OrderForm form, String rootFilePath, HttpSession session, Environment env) {
		boolean isReady = false;
		
		try {
			if (form.getOrderId() > 0 ) {
				OrderForm acc = getOrderDetails(form.getOrderId());
				if (acc != null) {
					List<OrderForm> formData = new ArrayList<OrderForm>();
					formData.add(acc);
					
					JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(formData);
					
			        String targetFolder = rootFilePath + File.separator + form.getOrderId();
					File f = new File(rootFilePath);
					if (!f.exists())
						f.mkdir();
					
					f = new File(targetFolder);
					if (!f.exists())
						f.mkdir();
			        
			        String outputFile = targetFolder + File.separatorChar + "Order_"+form.getOrderId()+".pdf";
			        
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

			        JasperPrint jasperPrint = JasperFillManager.fillReport(realpath + "\\JASP-RPT\\OrderPdf.jasper", parameters, itemsJRBean);

			        OutputStream outputStream = new FileOutputStream(new File(outputFile));
			        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			        isReady = true;
			        //System.out.println("Order PDF File Generated");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		return isReady;
	}

}
