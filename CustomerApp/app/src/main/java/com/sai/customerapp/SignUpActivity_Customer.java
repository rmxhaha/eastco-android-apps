package com.sai.customerapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.customerapp.ConnectionManager.AuthenticateCustomer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity_Customer extends AppCompatActivity {

    EditText eastCoCustomer_Username_Input;
    EditText eastCoCustomer_PhoneNumber_Input;
    EditText eastCoCustomer_Password_Input;

    Button eastCoCustomer_Register_Button;

    String SIGN_UP_URL = "http://contraffee.ml/api/v1/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__customer);

        //Initizalize View Component
        eastCoCustomer_Username_Input = (EditText) findViewById(R.id.eastCoCustomer_Username_Input);
        eastCoCustomer_PhoneNumber_Input = (EditText) findViewById(R.id.eastCoCustomer_PhoneNumber_Input);
        eastCoCustomer_Password_Input = (EditText) findViewById(R.id.eastCoCustomer_Password_Input);

        eastCoCustomer_Register_Button = (Button) findViewById(R.id.eastCoCustomer_Register_Button);

        eastCoCustomer_Register_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Show dialog box
                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity_Customer.this, R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("register ...");
                progressDialog.show();

                String username = eastCoCustomer_Username_Input.getText().toString().trim();
                String phonenumber = eastCoCustomer_PhoneNumber_Input.getText().toString().trim();
                String password = eastCoCustomer_Password_Input.getText().toString().trim();

                register(username, phonenumber, password);
            }

        });

    }

    private void register(String username, String phonenumber, String password) {

        // USING HTTP URL CONNECTION
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            AuthenticateCustomer ac_REGISTER = new AuthenticateCustomer();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUpActivity_Customer.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                // STORE THE UID IN SHARED PREFERENCES


            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("username",params[0]);
                data.put("phonenumber",params[1]);
                data.put("password",params[2]);
                data.put("role", "customer");

                String result = ac_REGISTER.sendPostRequest(SIGN_UP_URL, data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(username, phonenumber, password);

    }

}
