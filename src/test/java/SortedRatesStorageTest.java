import data_structures_in_json.CountryRate;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import sort_store_print_rates.SortedRatesStorage;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SortedRatesStorageTest extends AbstractRateTest {

    private static final String ANOTHER_RATE_NAME = "another";

    private SortedRatesStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new SortedRatesStorage();
        storage.add(createSimpleCountryRate("CZ", RATE_NAME, 4.));
        storage.add(createSimpleCountryRate("SK", RATE_NAME, 7.));

        // record with 2 rate types
        Map<String, Double> rates = new HashMap<>(1);
        rates.put(RATE_NAME, 5.);
        rates.put(ANOTHER_RATE_NAME, 13.);
        List<CountryRate.Period> periods = new LinkedList<>();
        periods.add(new CountryRate.Period(new Date(), rates));
        CountryRate austriaRates = new CountryRate("AU", "AU", "AU", periods);
        storage.add(austriaRates);
    }

    @Test
    public void getRatesShouldReturnAll() {
        assertThat(storage.getRates()).hasSize(3)
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(RATE_NAME))
                .contains(Tuple.tuple("CZ", 4.), Tuple.tuple("SK", 7.), Tuple.tuple("AU", 5.));
    }

    @Test
    public void addRateShouldIgnoreDoubleCountries() {
        assertFalse("Storage must return false in case of double",
                storage.add(createSimpleCountryRate("CZ", RATE_NAME, 6.)));
        assertTrue(storage.add(createSimpleCountryRate("D", RATE_NAME, 4.)));

        assertThat(storage.getRates()).hasSize(4)
                .extracting(CountryRate::getCode)
                .contains("CZ", "SK", "AU", "D");
    }

    @Test
    public void getRatesSortedByShouldSortProperly() {
        assertThat(storage.getRatesSortedBy(RATE_NAME, true))
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(RATE_NAME))
                .containsExactly(Tuple.tuple("CZ", 4.), Tuple.tuple("AU", 5.), Tuple.tuple("SK", 7.));

        assertThat(storage.getRatesSortedBy(RATE_NAME, false))
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(RATE_NAME))
                .containsExactly(Tuple.tuple("SK", 7.), Tuple.tuple("AU", 5.), Tuple.tuple("CZ", 4.));
    }

    @Test
    public void getRatesSortedByShouldSkipCountriesWithoutRate() {
        assertThat(storage.getRatesSortedBy(ANOTHER_RATE_NAME, true))
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(ANOTHER_RATE_NAME))
                .containsOnly(Tuple.tuple("AU", 13.));
    }
}