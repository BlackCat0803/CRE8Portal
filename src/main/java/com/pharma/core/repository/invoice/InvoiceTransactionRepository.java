package com.pharma.core.repository.invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.invoice.InvoiceTransaction;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("invoiceTransactionRepository")
public interface InvoiceTransactionRepository extends JpaRepository<InvoiceTransaction, Integer> {

	List<InvoiceTransaction> findByInvoiceId(int id);
	
	List<InvoiceTransaction> findByPrescriptionTranId(int id);
	
	
	@Query("select c from InvoiceTransaction c where prescriptionTranId=:prescriptionTranId and scriptType=:scriptType and refillNumber=:refillNumber")
	List<InvoiceTransaction> fetchInvoiceDetailsByRxRefill(@Param("prescriptionTranId") int prescriptionTranId,@Param("scriptType") String scriptType,@Param("refillNumber") int refillNumber);
	
}
