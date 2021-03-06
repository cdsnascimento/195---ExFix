package application;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import service.ContractService;
import service.OnlinePaymentService;
import service.PaypalService;

public class Program {

    public static void main(String[] args) throws ParseException {
        
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Enter contract data");
        
        System.out.print("Number: ");
        int number = sc.nextInt();

        System.out.print("Date (dd/MM/yyyy): ");
        Date date = sdf.parse(sc.next());
        
        System.out.print("Contact value: ");
        double value = sc.nextDouble();

        System.out.print("Enter number of installments: ");
        int N = sc.nextInt();
        
        System.out.println("Installments:");
        
        Contract contract = new Contract(number, date, value);

        OnlinePaymentService ps = new PaypalService();

        ContractService cs = new ContractService(ps);
        
        cs.ProcessContract(contract, N);
        
        for (Installment it : contract.getInstallment()) {
        	System.out.println(it);
        }
       
        sc.close();
    }
    
}
