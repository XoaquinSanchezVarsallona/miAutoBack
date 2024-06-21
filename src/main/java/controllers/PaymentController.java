package controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import spark.Route;
import com.mercadopago.client.preference.PreferenceItemRequest;
import java.math.BigDecimal;
import java.util.List;
import com.mercadopago.MercadoPagoConfig;


public class PaymentController {
    private static final Gson gson = new Gson();

    public static Route createPreference  = (req, res) -> {
        try {
            // Client token
            String token = req.headers("Authorization").replace("Bearer ", "");
            MercadoPagoConfig.setAccessToken(token);

            String requestBody = req.body();
            JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);
            JsonObject items = jsonObj.getAsJsonArray("items").get(0).getAsJsonObject();
            String title = items.get("title").getAsString();
            Float price = items.get("unit_price").getAsFloat();
            Integer quantity = items.get("quantity").getAsInt();

            // Preference
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(price))
                    .currencyId("ARS")
                    .build();

            List<PreferenceItemRequest> list = List.of(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8081/")
                    .failure("http://localhost:8081/")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(list)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            JsonObject json = new JsonObject();
            json.addProperty("preferenceId", preference.getId());
            res.status(200);
            res.type("application/json");
            return json.toString();
        } catch (MPException | MPApiException e) {
            res.status(500);
            return "Something went wrong when creating preference";
        }
    };
}
