package BankServices;

public class BankAccount {
    private int balance;
    private String mPIN;
    private String firstName,lastName;
    private String phoneNumber;
    private String address;
    private String occupation;
    private int annualIncome;
    private String accountType;

    public BankAccount(){
        //Empty Constructor for data transmittion
    }
    public BankAccount(int balance, String mPIN, String firstName, String lastName, String phoneNumber, String address, String occupation, int annualIncome, String accountType) {
        this.balance = balance;
        this.mPIN = mPIN;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.occupation = occupation;
        this.annualIncome = annualIncome;
        this.accountType = accountType;
    }

    public int getBalance() {
        return balance;
    }

    public String getmPIN() {
        return mPIN;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getOccupation() {
        return occupation;
    }

    public int getAnnualIncome() {
        return annualIncome;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setmPIN(String mPIN) {
        this.mPIN = mPIN;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setAnnualIncome(int annualIncome) {
        this.annualIncome = annualIncome;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
