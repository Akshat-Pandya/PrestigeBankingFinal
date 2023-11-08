package BankServices;

public class LoanProvider  {
    private final float SAVINGS_ITR=9.54F;
    private final float BUSINESS_ITR=6.54F;
    private final float FIXED_DEPOSIT_ITR=11.32F;
    private float interestRate;
    public void viewInterestRates(BankAccount account){
        if(account.getAccountType()=="SAVING")
            interestRate=SAVINGS_ITR;
        if(account.getAccountType()=="BUSINESS")
            interestRate=BUSINESS_ITR;
        if(account.getAccountType()=="FIXEDDEPOSIT")
            interestRate=FIXED_DEPOSIT_ITR;

    }
}
