 public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Atm  atm  = new Atm(bank);
        atm.run();
    }
}