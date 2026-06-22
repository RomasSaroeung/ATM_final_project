// តួនាទី: គ្រប់គ្រងរូបិយប័ណ្ណ (Dollar / Riel) និងអត្រាប្តូរប្រាក់

public enum Currency {
    USD("$",  1.0),
    KHR("៛",  4100.0);
    private final String symbol;
    private final double rateFromUsd;

    Currency(String symbol, double rateFromUsd) {
        this.symbol      = symbol;
        this.rateFromUsd = rateFromUsd;
    }
    public String getSymbol() {
        return symbol;
    }
    public double fromUsd(double usdAmount) {
        return usdAmount * rateFromUsd;
    }
    public double toUsd(double amountInThisCurrency) {
        return amountInThisCurrency / rateFromUsd;
    }
    public String format(double usdAmount) {
        double value = fromUsd(usdAmount);
        if (this == KHR) {
            return String.format("%,.0f%s", value, symbol);
        } else {
            return String.format("%s%,.2f", symbol, value);
        }
    }
}