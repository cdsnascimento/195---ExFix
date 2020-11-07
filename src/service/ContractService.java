package service;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void ProcessContract(Contract contract, Integer months) {
		
		Date dueDate = contract.getDate();
		double basicQuota = contract.getTotalValue() / months;
		double updateQuota;
		double fullQuota;
		
		for (int i = 1; i <= months; i++) {
			updateQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
			fullQuota = updateQuota + onlinePaymentService.paymentFee(updateQuota);
			dueDate = addMonths(contract.getDate(), i);
			contract.getInstallment().add(new Installment(dueDate, fullQuota));
		}
	}
	
	private Date addMonths(Date date, int N) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MONTH, N);
		
		return calendar.getTime();
	}
}
