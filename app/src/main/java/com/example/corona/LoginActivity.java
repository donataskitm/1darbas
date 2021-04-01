package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*import android.widget.Toast;*/

public class LoginActivity extends AppCompatActivity { //klases pradzia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //funkc. pradzia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas  //This method receives the parameter savedInstanceState, which is a Bundle object containing the activity's previously saved state. If the activity has never existed before, the value of the Bundle object is null.
        setContentView(R.layout.activity_login); //suteikti langui si vaizda, kodas siejamas su vaizdu
        EditText usernameet = findViewById(R.id.user_name); //susiejamas elementas su kintamuoju kode
        EditText passwordet = findViewById(R.id.user_password);
        Button loginb = findViewById(R.id.login_btn);
        Button regb = findViewById(R.id.register_btn);

        CheckBox rememberMe = findViewById(R.id.remember_me);

        User user = new User(LoginActivity.this); //turi duomenis

        rememberMe.setChecked(user.isRememberedForLogin()); //kokia paskutini karta buvo suteikta reiksme (true arba fase)

        if (rememberMe.isChecked()){ //patikriname is karto uzkrovus langa
            usernameet.setText(user.getUserNameForLogin(), TextView.BufferType.EDITABLE);  //editText viduje pateiksime
        //galima bus redaguoti(pasikeisti), del EDITABLE
            passwordet.setText(user.getUserPasswordForLogin(), TextView.BufferType.EDITABLE);
        }
        else {
            usernameet.setText("", TextView.BufferType.EDITABLE);  //nes is SharedPreferences interf. galima paiimti is visur
            passwordet.setText("", TextView.BufferType.EDITABLE);
        }

        //kodas susijes su mygtuko paspaudimu
        loginb.setOnClickListener(new View.OnClickListener() {  //new kuriamas objektas
            @Override //paspaudus mygtuka
            public void onClick(View v) { //funkcijos pradzia  pasmaudus mygtuka
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

                  //issaugoti SharedPref. duomenis

                   user.setUserNameForLogin(txtusername);
                   user.setUserPasswordForLogin(txtpassword);

                   if(rememberMe.isChecked()){  //ar pazymejmo checkbox
                       user.setRemembermeKeyForLogin(true);  //norime ji isaugoti. Kad irasyti i SharedPreferences
                   }
                   else{
                       user.setRemembermeKeyForLogin(false);  //kad kita karta nebutu irasyta
                   }


                  // System.out.println("Vartotojo vardas: " + user.getUserName() + "\n" + "Slaptažodis: " + user.getUserPassword());
                  // Toast.makeText(LoginActivity.this, "Vartotojo vardas: " + user.getUserName() + "\n" + "Slaptažodis: " + user.getUserPassword(), Toast.LENGTH_SHORT).show(); //1.

                Intent  gotoSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);//is kur i kur
                    startActivity(gotoSearchActivity);//
                } else  {
                    usernameet.setError(getResources().getString(R.string.login_invalid_credentials_message));
                    //usernameet.setError("Pranešimas");
                    usernameet.requestFocus();
                }
            }
        });

        // REGISTRACIJOS MYGTUKUI
        regb.setOnClickListener(new View.OnClickListener() {  //new kuriamas objektas
            @Override //paspaudus mygtuka
            public void onClick(View v) { //funkcijos pradzia
                Intent gotoSearchActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                 startActivity(gotoSearchActivity);
            }
        });
        //REGISTRACIJOS MYGTUKUI
    } //funkc. pabaiga
} //klases pabaiga

