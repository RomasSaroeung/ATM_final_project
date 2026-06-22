import java.util.Scanner;
public class Atm {

    private Bank bank;
    private Account currentAccount;
    private Scanner scanner;

    public Atm(Bank bank) {
        this.bank    = bank;
        this.scanner = new Scanner(System.in);
    }
    private void showBanner() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║       🏧  ស្វាគមន៍មក ATM System         ║");
        System.out.println("║         Cambodia Bank ATM             ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
    private boolean login() {
        System.out.println("\n--- 🔐 ចូលប្រព័ន្ធ ---");
        System.out.print("បញ្ចូលលេខគណនី: ");
        String accNum = scanner.nextLine().trim();

        System.out.print("បញ្ចូល PIN: ");
        String pin = scanner.nextLine().trim();

        currentAccount = bank.login(accNum, pin);

        if (currentAccount == null) {
            System.out.println(" លេខគណនី ឬ PIN មិនត្រឹមត្រូវ!");
            return false;
        }

        System.out.printf("%n✅ ចូលប្រព័ន្ធបានជោគជ័យ! សូមស្វាគមន៍ %s%n",
                currentAccount.getOwnerName());
        return true;
    }
    private void showMenu() {
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║         📋 ម៉ឺនុយ ATM           ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║  1. 💰 គិតលុយ (មើលសមតុល្យ)     ║");
        System.out.println("║  2. 📥 ដាក់លុយ                 ║");
        System.out.println("║  3. 📤 ដកលុយ                  ║");
        System.out.println("║  4. 🔄 ផ្ទេរលុយ                  ║");
        System.out.println("║  5. 🚪 ចាកចេញ                 ║");
        System.out.println("╚═══════════════════════════════╝");
        System.out.print("ជ្រើសរើស (1-5): ");
    }
    private Currency chooseCurrency() {
        while (true) {
            System.out.println("\nជ្រើសរើសរូបិយប័ណ្ណ:");
            System.out.println("  1. 💵 ដុល្លារ ($)");
            System.out.println("  2. 💴 រៀល (៛)");
            System.out.print("ជ្រើសរើស (1-2): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": return Currency.USD;
                case "2": return Currency.KHR;
                default:
                    System.out.println(" ជម្រើសមិនត្រឹមត្រូវ! សូមបញ្ចូល 1 ឬ 2");
            }
        }
    }
    private void handleCheckBalance() {
        System.out.println("\n--- 💰 គិតលុយ ---");
        Currency currency = chooseCurrency();
        currentAccount.checkBalance(currency);
    }
    private void handleDeposit() {
        System.out.println("\n--- 📥 ដាក់លុយ ---");
        Currency currency = chooseCurrency();
        System.out.printf("ចំនួនទឹកប្រាក់ដើម្បីដាក់ (%s): ", currency.getSymbol());
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            currentAccount.deposit(amount, currency);
            currentAccount.checkBalance(currency);
        } catch (NumberFormatException e) {
            System.out.println(" ចំនួនទឹកប្រាក់មិនត្រឹមត្រូវ!");
        }
    }
    private void handleWithdraw() {
        System.out.println("\n--- 📤 ដកលុយ ---");
        Currency currency = chooseCurrency();
        System.out.printf("ចំនួនទឹកប្រាក់ដើម្បីដក (%s): ", currency.getSymbol());
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            currentAccount.withdraw(amount, currency);
            currentAccount.checkBalance(currency);
        } catch (NumberFormatException e) {
            System.out.println(" ចំនួនទឹកប្រាក់មិនត្រឹមត្រូវ!");
        }
    }
    private void handleTransfer() {
        System.out.println("\n--- 🔄 ផ្ទេរលុយ ---");
        System.out.print("បញ្ចូលលេខគណនីទទួល: ");
        String targetNum = scanner.nextLine().trim();

        if (targetNum.equals(currentAccount.getAccountNumber())) {
            System.out.println(" មិនអាចផ្ទេរទៅគណនីខ្លួនឯងបានទេ!");
            return;
        }
        Account target = bank.findAccount(targetNum);
        if (target == null) {
            System.out.println(" មិនរកឃើញគណនីទទួលទេ!");
            return;
        }
        System.out.printf("➡️  ផ្ទេរទៅ: %s (%s)%n",
                target.getOwnerName(), target.getAccountNumber());

        Currency currency = chooseCurrency();
        System.out.printf("ចំនួនទឹកប្រាក់ (%s): ", currency.getSymbol());

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (currentAccount.withdraw(amount, currency)) {
                target.deposit(amount, currency);
                System.out.printf("✅ ផ្ទេរ %s ទៅ %s បានជោគជ័យ!%n",
                        currency.format(currency.toUsd(amount)),
                        target.getOwnerName());
            }
            currentAccount.checkBalance(currency);
        } catch (NumberFormatException e) {
            System.out.println(" ចំនួនទឹកប្រាក់មិនត្រឹមត្រូវ!");
        }
    }
    public void run() {
        showBanner();
        int attempts = 0;
        while (attempts < 3) {
            if (login()) break;
            attempts++;
            if (attempts < 3)
                System.out.printf("⚠️  សល់ %d ការព្យាយាមទៀត%n", 3 - attempts);
        }

        if (currentAccount == null) {
            System.out.println("\n🔒 គណនីត្រូវបានចាក់សោ! សូមទំនាក់ទំនងធនាគារ។");
            scanner.close();
            return;
        }

        // Main Menu Loop
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleCheckBalance();
                    break;
                case "2":
                    handleDeposit();
                    break;
                case "3":
                    handleWithdraw();
                    break;
                case "4":
                    handleTransfer();
                    break;
                case "5":
                    System.out.println("\n👋 អរគុណ! សូមបញ្ជូនកាតរបស់អ្នក។ លា​សិន!");
                    running = false;
                    break;
                default:
                    System.out.println("ជម្រើសមិនត្រឹមត្រូវ! សូមបញ្ចូល 1-5");
            }
        }
        scanner.close();
    }
}