import data_structures_in_json.CountryRate;
import load_rates_from_json.LoadRates.*; //LoadRatesFromJsonWithURL;
import sort_store_print_rates.SortedRatesStorage;

import java.util.List;

public class BarclaysVAT {

    private static final int COUNTRIES_LIMIT = 3;
    private static final String STD_RATE_NAME = "standard";

    private static void write(String str) {
        System.out.println(str);
    }

    private static void writeCountryRate(CountryRate cr) {
        write(String.format("\t%-15s%-6s%.1f", cr.getName(), cr.getCountryCode(), cr.getLastRates().get(STD_RATE_NAME)));
    }

    private static void writeTopCountryRates(List<CountryRate> countryRates) {
        countryRates.subList(0, COUNTRIES_LIMIT).forEach(BarclaysVAT::writeCountryRate);
    }

    public static void main(String[] args) {
        SortedRatesStorage storage = new SortedRatesStorage();

        LoadRatesFromJsonWithURL loader = new LoadRatesFromJsonWithURL("http://jsonvat.com");
        try {
            write("Loading data...");
            loader.load(storage::add);
        } catch (Exception ex) {
            write("Could not load_rates_from_json data");
            ex.printStackTrace();
            return;
        }

        write("Countries with the smallest standard VAT:");
        writeTopCountryRates(storage.getRatesSortedBy(STD_RATE_NAME, true));

        write("Countries with the largest standard VAT:");
        writeTopCountryRates(storage.getRatesSortedBy(STD_RATE_NAME, false));

    }
}

