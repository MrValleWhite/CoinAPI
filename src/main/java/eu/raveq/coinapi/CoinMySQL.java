package eu.raveq.coinapi;

import org.bukkit.Bukkit;

import java.sql.*;

public class CoinMySQL {

    private String HOST = "";

    private String DATABASE = "";

    private String USER = "";

    private String PASSWORD = "";

    private Connection con;

    public CoinMySQL(String host, String database, String user, String password) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        connect();
    }

    public CoinMySQL() {

    }

    public void connect() {
        try {
            if(this.con == null) {
                this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE
                        + "?autoReconnect=true", this.USER, this.PASSWORD);
                Bukkit.getConsoleSender().sendMessage("§8| §bCoinSystem §8» §7MySQL erfolgreich §everbunden!");
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§8| §bCoinSystem §8» " +
                    "§7Bei der Verbindung ist ein Fehler aufgetreten §8 | §cMYSQL");
        }
    }

    public void close() {
        try {
            if(this.con != null) {
                this.con.close();
                Bukkit.getConsoleSender().sendMessage("§8| §bCoinSystem §8» §7MySQL erfolgreich §cbeendet!");
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§8| §bCoinSystem §8» §7Bei der beendung ist ein Fehler aufgetreten" +
                    " §8| §cMYSQL");
        }
    }

    public void update(String qry) {
        try {
            Statement st = this.con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = this.con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
    }

}
