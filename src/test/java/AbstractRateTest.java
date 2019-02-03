import data_structures_in_json.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AbstractRateTest {

   // static final String RATE_NAME = "rate_name";
   static final String RATE_NAME = "rate_name";

    CountryRate.Period createPeriod(Date date, String name, Double value) {
        Map<String, Double> rates = new HashMap<>(1);
        rates.put(name, value);
        return new CountryRate.Period(date, rates);
    }

    CountryRate createSimpleCountryRate(String code, String name, Double value) {
        return new CountryRate(code, code, code, Collections.singleton(createPeriod(new Date(), name, value)));
    }



}
