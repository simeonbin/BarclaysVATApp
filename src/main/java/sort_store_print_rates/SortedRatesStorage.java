package sort_store_print_rates;

import data_structures_in_json.CountryRate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortedRatesStorage {  //implements RatesStorage

    private static final Function<String, Comparator<CountryRate>> compareBy = rateType -> Comparator.comparing(rate -> rate.getLastRates().get(rateType));

    private Set<CountryRate> rates = new HashSet<>();

    public boolean add(CountryRate rate) {
        return rates.add(rate);
    }

    public Collection<CountryRate> getRates() {
        return Collections.unmodifiableSet(rates);
    }

    public List<CountryRate> getRatesSortedBy(String rateType, boolean ascending) {

        Comparator<CountryRate> comparator = compareBy.apply(rateType);
        return rates.stream()
                .filter(rate -> rate.getLastRates().get(rateType) != null)
                .sorted(ascending ? comparator : comparator.reversed())
                .collect(Collectors.toList());

    }
}
