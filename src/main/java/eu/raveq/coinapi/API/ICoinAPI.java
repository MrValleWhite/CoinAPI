package eu.raveq.coinapi.API;

import java.util.UUID;

public interface ICoinAPI {

    Integer getCoins(UUID uuid);

    void addCoins(UUID uuid, int amount);

    void removeCoins(UUID uuid, int amount);

    void setCoins(UUID uuid, int amount);

    boolean doesUserExist(UUID uuid);

}
