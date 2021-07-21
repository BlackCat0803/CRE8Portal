package com.pharma.core.repository.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.order.OrderTransaction;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("orderTransactionRepository")
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Integer> {

	List<OrderTransaction> findByOrderId(int id);
	
	@Query("Select count(o) from OrderTransaction o where o.orderId=:orderId")
	int getFilterRecordCount(@Param("orderId") int orderId);
	
	@Query("Select o from OrderTransaction o where o.orderId=:orderId")
	Page<OrderTransaction> getFilterRecords(@Param("orderId") int orderId, Pageable page);
	
	List<OrderTransaction> findByOrderIdAndRxStatus(int id,String status);
	
	List<OrderTransaction> findByPrescriptionTranId(int id);
	
	@Query("select c from OrderTransaction c where prescriptionTranId=:prescriptionTranId and scriptType=:scriptType and refillNumber=:refillNumber")
	List<OrderTransaction> fetchOrderDetailsByRxRefill(@Param("prescriptionTranId") int prescriptionTranId,@Param("scriptType") String scriptType,@Param("refillNumber") int refillNumber);


}
