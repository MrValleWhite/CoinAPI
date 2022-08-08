package eu.raveq.lobbysystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CoinAPIImpl {

    private MySQL mySQL;

    public Integer getCoins(UUID uuid) {
        String qry = "SELECT coins FROM coinapi WHERE uuid=?";
        try (ResultSet rs = mySQL.query(qry, uuid.toString())) {
            if (rs.next()) {
                return rs.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addCoins(UUID uuid, int amount) {
        mySQL.update("UPDATE coinapi SET coins=? WHERE uuid=?", getCoins(uuid) + amount, uuid.toString());
    }

    public void removeCoins(UUID uuid, int amount) {
        int currentCoins = getCoins(uuid);

        if (currentCoins >= amount) {
            mySQL.update("UPDATE coinapi SET coins=? WHERE uuid=?", currentCoins - amount, uuid.toString());
        }
    }

    public void setCoins(UUID uuid, int amount) {
        mySQL.update("UPDATE coinapi SET coins=? WHERE uuid=?", amount, uuid.toString());
    }

    public void initPlayer(UUID uuid) {
        mySQL.update("INSERT INTO coinapi (uuid, coins) VALUES (?,?)", uuid.toString(), 0);
    }

    public boolean doesUserExist(UUID uuid) {
        String qry = "SELECT count(*) AS count FROM coinapi WHERE uuid=?";
        try (ResultSet rs = mySQL.query(qry, uuid.toString())) {
            if (rs.next()) {
                return rs.getInt("count") != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTables() {
        this.mySQL = CoinSystem.getInstance().getMySQL();
        mySQL.update("CREATE TABLE IF NOT EXISTS coinapi (uuid VARCHAR(36), coins INT(35))");
    }

}
