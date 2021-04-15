package com.example.corona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException { //throws dirbtinai sukelia situacija. Iesko catch, jeineranda apdoroja bevarde apdorokle
        InputStream is = new URL(url).openStream();
        try { //
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json; //blokas baigiamas reiksmes grazinimu
        } finally { /// vykdom ir ivykus klaidai
            is.close(); //uzdaro inputstream ir atlaisvina resursus
        }
    }
//paims json obj ir grazins json masyva
    public static ArrayList<Corona> getList(JSONArray jsonArray) throws JSONException{ //konvertuosim JSON i JAVA sarasa
        ArrayList<Corona> coronaList = new ArrayList<Corona>(); //kokios klases objektus talpinsim// gali buti sarase tik vieno tipo elementai
        //isimti duomenis(data) ir issaugoti Corona objektu sarase (coronaList)
        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Corona corona = new Corona( //konstruojamas objektas
                    //kokia seka perduosime duomenis
                    jsonObject.getString("country"),
                    jsonObject.getString("lastUpdate"),
                    jsonObject.getString("keyId"),
                    jsonObject.getInt("confirmed"),
                    jsonObject.getInt("deaths")
            );
            coronaList.add(corona); //trk
        }
        return coronaList;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject) throws JSONException{
        //pasalinama is JSON visa nereikalinga informacija (metaduomenys) paliekant covid19stats masyva
        int jsonLength = jsonObject.toString().length(); //
        String covid19Stats = "{"+ jsonObject.toString().substring(96, jsonLength)+"}";
        //grazina visu ilgiu, konvertuojam i string tipa, po to iskerpam dali is eilutes. Pradeda nuo 96 iki pacio galo.
        //string i JSONObject
        JSONObject jsonObject1 = new JSONObject(covid19Stats);
        //JSONObject i JASONArray
        JSONArray jsonArray = jsonObject1.getJSONArray("covid19Stats");//trk

        return jsonArray; //trk
    }

    public static ArrayList<Corona> getCoronaListByCountry(ArrayList<Corona> coronaArrayList, String country){
        ArrayList<Corona> coronaArrayListByCountry = new ArrayList<Corona>();//skliaustuose galima rezervuoti vietu skaiciu sarase
        System.out.println("apie šalį////////////////////");
        for (Corona corona : coronaArrayList){ //pries(kaireje) sukuriamas tos klases objektas per kurios sarasa iteruojama. Patobulintas for objektu sarasams
             if (corona.getKeyId().contains(country)){//contains iesko zodzio dalies
                 coronaArrayListByCountry.add(corona);
            }
        }
        return coronaArrayListByCountry;
    }

}