package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity { //klases pradzia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //funkc. pradzia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas  //This method receives the parameter savedInstanceState, which is a Bundle object containing the activity's previously saved state. If the activity has never existed before, the value of the Bundle object is null.
        setContentView(R.layout.activity_login); //suteikti langui si vaizda, kodas siejamas su vaizdu
        EditText usernameet = findViewById(R.id.user_name); //susiejamas elementas su kintamuoju kode
        EditText passwordet = findViewById(R.id.user_password);
        Button loginb = findViewById(R.id.login_btn);
        Button regb = findViewById(R.id.register_btn);

        //kodas susijes su mygtuko paspaudimu
        loginb.setOnClickListener(new View.OnClickListener() {  //new kuriamas objektas
            @Override //paspaudus mygtuka
            public void onClick(View v) { //funkcijos pradzia
                String txtusername = usernameet.getText().toString();
                String txtpassword = passwordet.getText().toString();

                //cia if vieta
              //  Toast.makeText(LoginActivity.this, "Vartotojo vardas: " + txtusername + "\n" + "Slaptažodis: " + txtpassword, Toast.LENGTH_SHORT).show(); //1.

                //Intent gotoSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);
               // startActivity(gotoSearchActivity);
                //klaidu zurnalo isvalymas
               usernameet.setError(null);
               passwordet.setError(null);

          //      EditText etUserName = (EditText) findViewById(R.id.txtsername);
          //      String strUserName = etUserName.getText().toString();

           //     if(TextUtils.isEmpty(strUserName)) {
           //         etUserName.setError("Your message");
            //        return;
           //     }


               if (Validation.isCredentialsValid(txtusername) && Validation.isPasswordValid(txtpassword)) {  //skliaust nusak funkcijos pr., kl. pr., sal. pr.
                Intent  gotoSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);//is kur i kur
                    startActivity(gotoSearchActivity);//
                } else  {
                    usernameet.setError(getResources().getString(R.string.login_invalid_credentials_message));
                    //usernameet.setError("Pranešimas");
                    usernameet.requestFocus();
                }
            }
        });

        ///////////////////////////////////////// REGISTRACIJOS MYGTUKUI
        regb.setOnClickListener(new View.OnClickListener() {  //new kuriamas objektas
            @Override //paspaudus mygtuka
            public void onClick(View v) { //funkcijos pradzia
                Intent gotoSearchActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                 startActivity(gotoSearchActivity);
            }
        });
        ////////////////////////////////// REGISTRACIJOS MYGTUKUI
    } //funkc. pabaiga
} //klases pabaiga

