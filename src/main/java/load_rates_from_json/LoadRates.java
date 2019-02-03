package load_rates_from_json;

import com.google.gson.*;
import data_structures_in_json.CountryRate;

import java.io.*;
import java.net.URL;
import java.util.function.Consumer;

// Below is a typical "rates" : [ ] key/value pair with a JsonElement of the JsonArray relevant to this case
// Class LoadRates() with the two associated "Readers" manages the Reading and Parsing of it.
//
/* "rates": [

       {"name" : "Luxembourg", "code" : "LU", "country_code" : "LU",
        "periods" : [
        {"effective_from":"2016-01-01",
        "rates": {"super_reduced":3.0,"reduced1":8.0,"standard":17.0,"parking":13.0}
        },

        {"effective_from":"2015-01-01",
        "rates": {"super_reduced":3.0,"reduced1":8.0,"reduced2":14.0,"standard":17.0,"parking":12.0}
        },

        {"effective_from" : "0000-01-01",
        "rates" : {"super_reduced":3.0,"reduced1":6.0,"reduced2":12.0,"standard":15.0,"parking":12.0} }
                ]
        },
        ...............
        */

abstract public class LoadRates {

    private String source;
    private Gson gson = new Gson();
    private TypeAdapter<CountryRate> countryRateAdapter = gson.getAdapter(CountryRate.class).nullSafe();

    public LoadRates(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    abstract protected Reader getReader() throws IOException;

    public void load(Consumer<CountryRate> consumer) throws IOException, IllegalArgumentException {
        getRatesArray(getReader()).forEach (element -> {
                    try {
                        consumer.accept(countryRateAdapter.fromJsonTree(element));
                    }   catch (JsonIOException | JsonSyntaxException ex) {
                        throw new IllegalArgumentException("Could not parse country rate element");
                    }
                });
    }

    private JsonArray getRatesArray(Reader reader) throws IllegalArgumentException {

        JsonParser parser = new JsonParser();

        JsonElement jsonTree = parser.parse(reader);

        if (!jsonTree.isJsonObject()) {
            throw new IllegalArgumentException("Not a JSON object");
        }

        JsonObject jsonObject = jsonTree.getAsJsonObject();

        JsonElement ratesElement = jsonObject.get("rates");

        if (ratesElement == null || !ratesElement.isJsonArray()) {
            throw new IllegalArgumentException("Cannot find rates array");
        }

        return ratesElement.getAsJsonArray();
    }


    public static class LoadRatesFromJsonWithStringReader extends LoadRates {

        public LoadRatesFromJsonWithStringReader(String source) {
            super(source);
        }

        @Override
        protected Reader getReader() throws IOException {
            return new BufferedReader(new StringReader(getSource()));
        }
    }

    public static class LoadRatesFromJsonWithURL extends LoadRates {

        public LoadRatesFromJsonWithURL(String source) {
            super(source);
        }

        @Override
        protected Reader getReader() throws IOException {
            URL url = new URL(getSource());
            return new BufferedReader(new InputStreamReader(url.openStream()));
        }

    }

}

