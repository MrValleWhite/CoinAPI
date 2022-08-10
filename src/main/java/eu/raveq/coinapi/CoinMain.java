package eu.raveq.coinapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinMain extends JavaPlugin implements Listener {

    public static final String pr = "§8| §bCoin's §8» ";
    public static final String noperm = pr + "§cDazu hast Du keine Rechte!";

    public static CoinMySQL mysql;

    private static CoinMain ins;

    @Override
    public void onEnable() {
        saveConfig();
        ins = this;
        mysql = new CoinMySQL();
        if(getConfig().getString("Host") == null)
            getConfig().set("Host", "127.0.0.1");
        if (getConfig().getString("Database") == null)
            getConfig().set("Database", "CoinAPI");
        if (getConfig().getString("Nutzer") == null)
            getConfig().set("Nutzer", "Benutzername");
        if (getConfig().getString("Passwort") == null)
            getConfig().set("Passwort", "Passwort");

        saveConfig();
        connectMySQL();
        Bukkit.getConsoleSender().sendMessage("§8| §bCoin's §8» §7Plugin wurde §egeladen");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        closeMySQL();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        CoinAPI.createPlayer(uuid);
    }

    public static CoinMain getInstance() {
        return ins;
    }

    public static void closeMySQL() {
        mysql.close();
    }

    public void connectMySQL() {
        String host = getConfig().getString("Host");
        String database = getConfig().getString("Database");
        String user = getConfig().getString("Nutzer");
        String password = getConfig().getString("Passwort");
        if (host.equalsIgnoreCase("127.0.0.1") && database.equalsIgnoreCase("CoinAPI") && user.equalsIgnoreCase("Benutzername") && password.equalsIgnoreCase("Passwort")) {
            Bukkit.getConsoleSender().sendMessage("§8| §bCoin's §8» §cDie MySQL Daten sind ungültig");
            Bukkit.getPluginManager().disablePlugin(getInstance());
            return;
        }
        mysql = new CoinMySQL(host, database, user, password);
        mysql.connect();
        mysql.update("CREATE TABLE IF NOT EXISTS CoinSystem(UUID varchar(64), COINS int)");
    }

}
