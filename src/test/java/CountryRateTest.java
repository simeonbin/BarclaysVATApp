import data_structures_in_json.CountryRate;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CountryRateTest extends AbstractRateTest {

    private CountryRate rate;

    @Before
    public void init() {
        long millis = new Date().getTime();

        List<CountryRate.Period> periods = new LinkedList<>();
        periods.add(createPeriod(new Date(millis - 100), RATE_NAME, 10.));
        periods.add(createPeriod(new Date(millis), RATE_NAME, 20.));
        periods.add(createPeriod(new Date(millis - 200), RATE_NAME, 30.));

        rate = new CountryRate("Czech", "CZ", "CZK", periods);
    }

    @Test
    public void fieldsShouldReturnProperValues() {
        assertEquals("Czech", rate.getName());
        assertEquals("CZ", rate.getCode());
        assertEquals("CZK", rate.getCountryCode());
    }

    @Test
    public void getLastRatesShouldReturnTheMostRecent() {
        assertEquals(new Double(20.), rate.getLastRates().get(RATE_NAME));
    }

}