public class Account {

    private String accountNumber;
    private String pin;
    private String ownerName;
    private double balanceUsd;

    public Account(String accountNumber, String pin, String ownerName, double balanceUsd) {
        this.accountNumber = accountNumber;
        this.pin           = pin;
        this.ownerName     = ownerName;
        this.balanceUsd    = balanceUsd;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public double getBalanceUsd()    { return balanceUsd; }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }
    public boolean deposit(double amount, Currency currency) {
        if (amount <= 0) {
            System.out.println(" ចំនួនទឹកប្រាក់ត្រូវតែធំជាង 0!");
            return false;
        }
        double amountUsd = currency.toUsd(amount);
        balanceUsd += amountUsd;
        System.out.printf("✅ ដាក់លុយបានជោគជ័យ! បានដាក់: %s%n", currency.format(amountUsd));
        return true;
    }
    public boolean withdraw(double amount, Currency currency) {
        if (amount <= 0) {
            System.out.println(" ចំនួនទឹកប្រាក់ត្រូវតែធំជាង 0!");
            return false;
        }
        double amountUsd = currency.toUsd(amount);
        if (amountUsd > balanceUsd) {
            System.out.printf(" សមតុល្យមិនគ្រប់គ្រាន់! សមតុល្យបច្ចុប្បន្ន: %s%n",
                    currency.format(balanceUsd));
            return false;
        }
        balanceUsd -= amountUsd;
        System.out.printf("✅ ដកលុយបានជោគជ័យ! បានដក: %s%n", currency.format(amountUsd));
        return true;
    }
    public void checkBalance(Currency currency) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║        សមតុល្យគណនី              ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf( "║  ឈ្មោះ   : %-22s║%n", ownerName);
        System.out.printf( "║  លេខគណនី: %-22s║%n", accountNumber);
        System.out.printf( "║  សមតុល្យ : %-21s║%n", currency.format(balanceUsd));
        System.out.println("╚══════════════════════════════════╝");
    }
}