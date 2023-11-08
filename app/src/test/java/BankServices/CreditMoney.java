package BankServices;

public class CreditMoney implements RBICreditGuide{
    private int credit_amount;
    private boolean validity=true;
    @Override
    public void setCreditAmount(int credit_amount) {
        this.credit_amount=credit_amount;
    }

    @Override
    public void creditMoney(BankAccount account) {
        if(credit_amount<0)
            validity=false;

        if(checkValidity())
        { int x= account.getBalance()+credit_amount;
        account.setBalance(x);
        generateReceipt(account);
        }
    }

    @Override
    public String generateReceipt(BankAccount account) {
        String result="Bank Account with MPIN creentials : "+account.getmPIN()+" credited by "+credit_amount+" successfully";
        return result;
    }

    @Override
    public boolean checkValidity() {

        return validity;
    }
}
