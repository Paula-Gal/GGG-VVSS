package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, double amount){
        Payment payment= new Payment(table, type, amount);
        validatePayment(payment);
        payRepo.add(payment);
    }

    private void validatePayment(Payment payment){
        if(payment.getTableNumber()<1 || payment.getTableNumber()>8){
            throw new RuntimeException("Invalid table");
        }
        if(payment.getAmount()<=0){
            throw new RuntimeException("Invalid amount");
        }
    }

    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        List<Payment> paymentList=getPayments();
        if (paymentList==null) return total;
        if (paymentList.size()==0) return total;
        for (Payment p:paymentList){
            if (p.getType().equals(type))
                total+=p.getAmount();
        }
        return total;
    }

}
