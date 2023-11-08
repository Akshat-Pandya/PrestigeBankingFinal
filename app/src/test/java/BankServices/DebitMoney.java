package BankServices;

public class DebitMoney implements RBIDebitGuide{
    private final int  DEBIT_LIMIT=10000;
    private int debit_amount;
    private boolean validity=true;
    @Override
    public void setDebitAmount(int debit_amount, BankAccount account) {
        if(debit_amount>account.getBalance())
            validity=false;
        else if (debit_amount>DEBIT_LIMIT)
            validity=false;
    }


    @Override
    public void debitMoney(BankAccount account) {
        if(validity)
        {
            int x=account.getBalance()-debit_amount;
            account.setBalance(x);
            generateReceipt(account);
        }
    }

    @Override
    public String generateReceipt(BankAccount account) {
        String result="Bank Account with MPIN creentials : "+account.getmPIN()+" debited by "+debit_amount+" successfully";
        return result;
    }

    @Override
    public boolean checkValidity() {
        return validity;
    }
}
