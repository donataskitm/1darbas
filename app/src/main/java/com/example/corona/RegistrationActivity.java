package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditText usernameet = findViewById(R.id.reg_name); //susiejamas elementas su kintamuoju kode
        EditText passwordet = findViewById(R.id.reg_password);
        EditText emailet = findViewById(R.id.reg_email);
        Button regb = findViewById(R.id.register_btn_reg);


        ///////////////////////////////////////// REGISTRACIJOS MYGTUKUI
        regb.setOnClickListener(new View.OnClickListener() {  //new kuriamas objektas
            @Override //paspaudus mygtuka
            public void onClick(View v) { //funkcijos pradzia

                String txtusername = usernameet.getText().toString();
                String txtpassword = passwordet.getText().toString();
                String  txtemail = emailet.getText().toString();

                usernameet.setError(null);
                passwordet.setError(null);
                emailet.setError(null);

                if (Validation.isCredentialsValid(txtusername) && Validation.isPasswordValid(txtpassword) && Validation.isEmailValid(txtemail)) {  //skliaust nusak funkcijos pr., kl. pr., sal. pr.
                    Intent  gotoSearchActivity = new Intent(RegistrationActivity.this, LoginActivity.class);//is kur i kur
                    startActivity(gotoSearchActivity);
                    Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.register_successful_1) + txtusername + "\n" + getResources().getString(R.string.register_successful_2), Toast.LENGTH_LONG).show();
                    
                }
                else if (!Validation.isCredentialsValid(txtusername)) {
                    usernameet.setError(getResources().getString(R.string.login_invalid_credentials_message_name));
                    usernameet.requestFocus();
                }
                else if (!Validation.isPasswordValid(txtpassword)) {
                    passwordet.setError(getResources().getString(R.string.login_invalid_credentials_message_password));
                    passwordet.requestFocus();
                }
                else {
                    emailet.setError(getResources().getString(R.string.login_invalid_credentials_message_email));
                    emailet.requestFocus();
                }

            }
        });




    }


}