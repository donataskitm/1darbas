package com.example.corona;

public class Corona {
    private String country;
    private String lastUpdate;
    private String keyId;
    private int confirmed;
    private int deaths;


    public Corona(String country, String lastUpdate, String keyId, int confirmed, int deaths){
        this.country = country;
        this.keyId = keyId;
        this.lastUpdate = lastUpdate;
        this.confirmed = confirmed;
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getKeyId() {
        return keyId;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "Corona{" +
                "country='" + country + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", keyId='" + keyId + '\'' +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                '}';
    }

}
