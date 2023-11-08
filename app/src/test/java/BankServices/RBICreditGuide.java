package BankServices;

public interface RBICreditGuide {
    // This interface shall contain the basic features which the credit system must have according to RBI standards
    void setCreditAmount(int credit_amount);
    void creditMoney(BankAccount account);
    String generateReceipt(BankAccount account);
    boolean checkValidity();
}
