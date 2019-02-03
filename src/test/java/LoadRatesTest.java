import data_structures_in_json.CountryRate;
import load_rates_from_json.LoadRates;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadRatesTest {

    @Test
    public void shouldProcess() throws Exception {
        List<CountryRate> countryRates = new LinkedList<>();
        new LoadRates.LoadRatesFromJsonWithURL(getClass().getClassLoader().getResource("details.json").toString()).load(countryRates::add);
        assertThat(countryRates).hasSize(28);
    }

    @Test(expected = IOException.class)
    public void shouldRaiseErrorForUnknownUrl() throws Exception {
        new LoadRates.LoadRatesFromJsonWithURL("abcd").load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorForDummyContent() throws Exception {
        new LoadRates.LoadRatesFromJsonWithStringReader("abracadabra").load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorWhenRatesArrayNotFound() throws Exception {
        new LoadRates.LoadRatesFromJsonWithStringReader("{\"version\":321}").load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorWhenRatesIsNotArray() throws Exception {
        new LoadRates.LoadRatesFromJsonWithStringReader("{\"rates\":123}").load(null);
    }

}