package BankServices;

public class TransferMoney  implements RBIMoneyTransferGuide {

    private int amount;
    private boolean validity=true;
    @Override
    public void setTransferAmount(int amount) {
        this.amount=amount;
    }

    @Override
    public boolean checkValidity(BankAccount account) {
        if(amount>account.getBalance())
        validity=false;
        return validity;
    }

    @Override
    public void transferAmount(BankAccount account, BankAccount account2) {
        if(checkValidity(account))
        {
            DebitMoney debitMoney=new DebitMoney();
            debitMoney.setDebitAmount(this.amount,account);
            if(debitMoney.checkValidity())
                debitMoney.debitMoney(account);
            CreditMoney creditMoney=new CreditMoney();
            creditMoney.setCreditAmount(this.amount);
            if(creditMoney.checkValidity())
            creditMoney.creditMoney(account2);

        }
    }

    @Override
    public String generateReceipt() {
        String result="Money transferred succesfully ";
        return result;
    }
}
