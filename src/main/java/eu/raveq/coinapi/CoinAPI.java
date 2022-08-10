package eu.raveq.coinapi;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinAPI {

    static boolean playerExists(String uuid) {
        try {
            ResultSet rs = CoinMain.mysql.query("SELECT * FROM CoinSystem WHERE UUID= '" + uuid + "'");
            if (rs.next())
                return (rs.getString("UUID") != null);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void createPlayer(String uuid) {
        if (!playerExists(uuid))
            CoinMain.mysql.update("INSERT INTO CoinSystem(UUID, COINS) VALUES ('" + uuid + "', '0');");
    }

    public static Integer getCoins(String uuid) {
        Integer i = Integer.valueOf(0);
        if (playerExists(uuid)) {
            try {
                ResultSet rs = CoinMain.mysql.query("SELECT * FROM CoinSystem WHERE UUID= '" + uuid + "'");
                if (!rs.next() || Integer.valueOf(rs.getInt("COINS")) == null);
                i = Integer.valueOf(rs.getInt("COINS"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            return getCoins(uuid);
        }
        return i;
    }

    public static void setCOINS(String uuid, Integer COINS) {
        if (playerExists(uuid)) {
            CoinMain.mysql.update("UPDATE CoinSystem SET COINS= '" + COINS + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setCOINS(uuid, COINS);
        }
    }

    public static void addCOINS(String uuid, Integer COINS) {
        if (playerExists(uuid)) {
            setCOINS(uuid, Integer.valueOf(getCoins(uuid).intValue() + COINS.intValue()));
        } else {
            createPlayer(uuid);
            addCOINS(uuid, COINS);
        }
    }

    public static void removeCoins(String uuid, Integer COINS) {
        if (playerExists(uuid)) {
            setCOINS(uuid, Integer.valueOf(getCoins(uuid).intValue() - COINS.intValue()));
        } else {
            createPlayer(uuid);
            removeCoins(uuid, COINS);
        }
    }

}
