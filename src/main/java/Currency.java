import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Currency {

    private Map<String, Double> currencies = new HashMap<>();

    private Pattern pattern = null;

    private void compilePattern() {
        if (this.pattern == null) {
            this.fillCurrencies();
            String currenciesJoin = String.join("|", getCurrenciesSymbol());
            this.pattern = Pattern.compile(".*?(\\d+).*?(" + currenciesJoin + ").*?(" + currenciesJoin + ").*?");
        }

    }


    private void fillCurrencies() {
        //BASE: USD!!!!
        if (currencies.isEmpty()) {
            this.currencies.put("EUR", 0.808274794D);
            this.currencies.put("JPY", 107.04346D);
            this.currencies.put("GBP", 0.698050067D);
            this.currencies.put("USD", 1.0D);
            this.currencies.put("CHF", 0.962000037D);
            this.currencies.put("CAD", 1.25745039D);
            this.currencies.put("CNY", 6.28239359D);
            this.currencies.put("AUD", 1.28617363D);
        }
    }

    public Map<String, Double> getCurrencies() {
        this.fillCurrencies();
        return this.currencies;
    }

    public List<String> getCurrenciesSymbol() {
        this.fillCurrencies();
        return new ArrayList<>(this.currencies.keySet());
    }

    public Double convertManual(String firstSymbol, double amount, String secSymbol) {
        this.fillCurrencies();

        double firstCurrencyInUsd = 0D;
        firstCurrencyInUsd = this.currencies.get(firstSymbol);
        if (firstCurrencyInUsd == 0D) return 0D;

        double secCurrencyInUsd = 0D;
        secCurrencyInUsd = this.currencies.get(secSymbol);
        if (secCurrencyInUsd == 0D) return 0D;

        double finalSum = 0D;
        finalSum = amount * firstCurrencyInUsd * secCurrencyInUsd;

        return finalSum;
    }

    public String convertAuto(String message) {

        if (message.isEmpty()) return "";

        this.fillCurrencies();
        this.compilePattern();


        Matcher matcher = this.pattern.matcher(message);

        if(matcher.matches()) {
            return matcher.group(1)+matcher.group(2)+matcher.group(3);
        }

        return "";
    }


}
