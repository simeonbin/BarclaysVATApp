package data_structures_in_json;

import java.util.*;

public class CountryRate {

    private String name;
    private String code;
    private String country_code;
    private SortedSet<Period> periods = new TreeSet<>();

    public CountryRate(String name, String code, String country_code, Collection<Period> periods) {
        this.name = name;
        this.code = code;
        this.country_code = country_code;
        this.periods.addAll(periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryRate rate = (CountryRate) o;
        return code.equalsIgnoreCase(rate.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCountryCode() {
        return country_code;
    }

    public Map<String, Double> getLastRates() {
        return periods.last().getRates();
    }

    public static class Period implements Comparable<Period> {
        Date effective_from;
        Map<String, Double> rates;

        public Period(Date effective_from, Map<String, Double> rates) {
            this.effective_from = effective_from;
            this.rates = rates;
        }

        Date getEffectiveFrom() {
            return effective_from;
        }

        Map<String, Double> getRates() {
            return Collections.unmodifiableMap(rates);
        }

        @Override
        public int compareTo(Period o) {
            return effective_from.compareTo(o.effective_from);
        }
    }
}
