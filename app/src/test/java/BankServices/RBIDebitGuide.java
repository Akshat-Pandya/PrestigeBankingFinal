package BankServices;

public interface RBIDebitGuide {
    // This interface shall contain the basic features which the debit system must have according to RBI standards
    void setDebitAmount(int debit_amount,BankAccount account);
    void debitMoney(BankAccount account);
    String generateReceipt(BankAccount account);
    boolean checkValidity();
}
