package eu.raveq.lobbysystem.API;

public class CoinAPI {

    private static ICoinAPI api;

    public static ICoinAPI getApi() {
        return api;
    }

    public static void setApi(ICoinAPI api) {
        CoinAPI.api = api;
    }

}