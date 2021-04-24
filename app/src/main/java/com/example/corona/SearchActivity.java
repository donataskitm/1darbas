package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.SearchView;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity { //AppCompatActivity klase kai reikia veikloms nauju f-ju senuose android

    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats";
    private ArrayList<Corona> coronaList = new ArrayList<Corona>();
    private Adapter adapter; //tarpininkas tarp xml ir searchactivity
    private SearchView searchView = null;
    private RecyclerView recyclerView; //korteliu vaizdas

    @Override
    //The Override-Annotation is just a hint for the compiler that you want to overwrite a certain function. The compiler will then check parent-classes and interfaces if the function exists there. If not, you will get a compile-error
    protected void onCreate(Bundle savedInstanceState) {  //Bundle savedInstanceState - isaugoti veiklos busenai, kur naudojam vel atnaujinus, pradzioj null, pvz.: lango slinkimo pozicija
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_search); //suteikti langui si vaizda

        AsyncFetch asyncFetch = new AsyncFetch(); //paleidziama nauja gija - nuskaitymui JSON is API
        asyncFetch.execute();  //vykdo kas toje klaseje numatyta. Butina execute

        // ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu); // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search); //vartotojas irasys duomenis
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //meniu juostoj lupa
        return super.onOptionsItemSelected(item);
    } // Every time when you press search button on keypad an Activity is recreated which in turn calls this function

    @Override
    protected void onNewIntent(Intent intent) { // Get search query
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);  //vartotojas ives pvz Italy
            if (searchView != null) {
                searchView.clearFocus(); //isvalo kursoriu
            }
            //is visu valstybiu saraso sukuriamas sarasas pagal ieskoma valstyte
ArrayList<Corona> coronaListByCountry = JSON.getCoronaListByCountry(coronaList, query);
        if (coronaListByCountry.size()==0){
            Toast.makeText(this, getResources().getString(R.string.search_no_results)+query, Toast.LENGTH_LONG).show();
        }
            //duomenu perdavimas Adapteriui ir RecyclerView sukurimas
        recyclerView = (RecyclerView) findViewById(R.id.corona_list);
        adapter = new Adapter(SearchActivity.this, coronaListByCountry);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        }
    }




    private class AsyncFetch extends AsyncTask<String, String, JSONObject> { //lygiag uzd. apdorot //AsyncTask perduoda parametrus i doInBackground
        //https://medium.com/nybles/making-use-of-the-android-asynctask-class-30469180a1d2
        //parametrai eina i doInBackground // Params, Progress and Result (per result grazins rezultata)
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { //metodas vykdomas pries doInBackground metoda. Paprasysime palaukti vartotojo, kol gausim duomenis
            super.onPreExecute(); //paleidzia gija pries doInBackground
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false); //negales atsaukti ir tures islaukti
            progressDialog.show(); //rodymas vaizdas kol laukia/igalinti
        }

        @Override
        protected JSONObject doInBackground(String... strings) { //jis bus vykdomas tuo metu kai vartotojas matys progress dialoga
            //skirtas duomenu paemimui is API
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(COVID_API); //perduodamas adresas
                return jsonObject;  //jei viskas ok, grazinam objekta
            } catch (IOException e) {  //isimtys, apdoros duomenisir isvesvartotojui
                Toast.makeText(SearchActivity.this, getResources().getText(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this, getResources().getText(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();
            } //baigiasi JSONE e
            return null;  //jei bus problemu grazinsim null
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) { //po
            progressDialog.dismiss(); //duomenys gauti, naikinam laukimo vaizda

            int statusCode = 0; //kintamasis, priskiriam pradine reiksme
            try {
                statusCode = jsonObject.getInt("statusCode");
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this, getResources().getText(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (statusCode == HttpURLConnection.HTTP_OK) { //statuso kodas
                // System.err.println(jsonObject.toString()); //(jis spaudins kaip klaida ir bus kita spalva)isvedam i terminala JSON faila
                // Toast.makeText(SearchActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                JSONArray jsonArray = null;
                try {
                    jsonArray = JSON.getJSONArray(jsonObject);//perduoda visa sarasa
                    coronaList = JSON.getList(jsonArray);

                    System.out.println("Lithunia covid stats:" + JSON.getCoronaListByCountry(coronaList, "Lithuania"));

                } catch (JSONException e) {
                    System.out.println(getResources().getText(R.string.search_error_reading_data) + e.getMessage());
                    e.printStackTrace();
                }
                System.err.println(jsonObject.toString());
            } else {  //kazkas nepavyko (serveris negrazino 200 kodo)
                String message = null;  //is JSON paiimam zinute
                try {
                    message = jsonObject.getString("message"); //pagal sita reiksme imsim duomenis
                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, getResources().getText(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();

                }
                Toast.makeText(SearchActivity.this, getResources().getText(R.string.search_error_reading_data) + message, Toast.LENGTH_LONG).show();//spausdiname message
            } //baigiasi else
        }// baigiasi onPostExecute
    }// baigiasi AsyncFetch
}// baigiasi SearchActivity

