package BankServices;

public interface RBIMoneyTransferGuide {
    void setTransferAmount(int amount);
    boolean checkValidity(BankAccount account);
    void transferAmount(BankAccount account,BankAccount account2);
    String generateReceipt();
}
