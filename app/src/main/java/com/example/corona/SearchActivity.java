package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SearchActivity extends AppCompatActivity {

    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats";

    @Override //The Override-Annotation is just a hint for the compiler that you want to overwrite a certain function. The compiler will then check parent-classes and interfaces if the function exists there. If not, you will get a compile-error
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AsyncFetch asyncFetch = new AsyncFetch(); //paleidziama nauja gija - nuskaitymui JSON is API
        asyncFetch.execute();  //vykdo kas toje klaseje numatyta. Butina execute

       // ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);
    }

    private class AsyncFetch extends AsyncTask<String, String, JSONObject> { //lygiag uzd. apdorot
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { //metodas vykdomas pries doInBackground metoda. Paprasysime palaukti vartotojo, kol gausim duomenis
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false); //negales atsaukti ir tures islaukti
            progressDialog.show(); //rodymas vaizdas kol laukia
        }

        @Override
        protected JSONObject doInBackground(String... strings) { //jis bus vykdomas tuo metu kai vartotojas matys progress dialoga
            //skirtas duomenu paemimui is API
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(COVID_API); //perduodamas adresas
                return jsonObject;  //jei viskas ok, grazinam objekta
            } catch (IOException e) {  //isimtys, apdoros duomenisir isvesvartotojui
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getText(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }catch (JSONException e){
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getText(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
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
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getText(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }

            if (statusCode == HttpURLConnection.HTTP_OK) { //statuso kodas
                System.err.println(jsonObject.toString()); //(jis spaudins kaip klaida ir bus kita spalva)isvedam i terminala JSON faila
                Toast.makeText(SearchActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            } else {  //kazkas nepavyko (serveris negrazino 200 kodo)
                String message = null;  //is JSON paiimam zinute
                try {
                    message =  jsonObject.getString("message"); //pagal sita reiksme imsim duomenis
                } catch (JSONException e) {
                    Toast.makeText(
                            SearchActivity.this,
                            getResources().getText(R.string.search_error_reading_data) + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                }
                    Toast.makeText(
                        SearchActivity.this,
                        getResources().getText(R.string.search_error_reading_data) + message, //spausdiname message
                        Toast.LENGTH_LONG
                    ).show();
            } //baigiasi else
        }// baigiasi onPostExecute
    }// baigiasi AsyncFetch
}// baigiasi SearchActivity

