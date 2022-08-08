package eu.raveq.lobbysystem;

import org.bukkit.plugin.java.JavaPlugin;

public class CoinSystem extends JavaPlugin {

    private static CoinSystem instance;
    private MySQL mysql;
    private CoinAPIImpl coinAPI;

    @Override
    public void onLoad() {
        instance = this;
        this.mysql = MySQL.newBuilder()
                .withUrl("135.125.194.35")
                .withPort(3306)
                .withDatabase("RaveQMinecraft")
                .withUser("AltV")
                .withPassword("RaveQ$MySQL$123")
                .create();
    }

    @Override
    public void onEnable() {
        System.out.println("[CoinAPI] Die CoinAPI wurde aktiviert");
        coinAPI = new CoinAPIImpl();
        coinAPI.createTables();
        CoinAPI.setApi((ICoinAPI) coinAPI);
    }

    @Override
    public void onDisable() {
        System.out.println("[CoinAPI] Die CoinAPI wurde deaktiviert");
    }

    public static CoinSystem getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public CoinAPIImpl getCoinAPI() {
        return coinAPI;
    }

}
