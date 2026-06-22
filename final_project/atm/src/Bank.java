
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put("1001", new Account("1001", "1234", "សុខ ដារ៉ា",  5000.00));
        accounts.put("1002", new Account("1002", "5678", "ចាន់ សុភា",  12500.50));
        accounts.put("1003", new Account("1003", "9999", "វង្ស នីតា",   800.00));
    }
    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    public Account login(String accountNumber, String pin) {
        Account acc = findAccount(accountNumber);
        if (acc == null) return null;
        if (!acc.verifyPin(pin)) return null;
        return acc;
    }
}