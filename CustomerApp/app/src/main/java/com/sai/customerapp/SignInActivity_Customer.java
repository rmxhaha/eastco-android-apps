package com.sai.customerapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sai.customerapp.ConnectionManager.AuthenticateCustomer;

import java.util.HashMap;

public class SignInActivity_Customer extends AppCompatActivity {

    EditText eastCoCustomer_Username_Input_LOGIN;
    EditText eastCoCustomer_Password_Input_LOGIN;

    Button eastCoCustomer_Login_Button;

    String SIGN_IN_URL = "http://contraffee.ml/api/v1/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__customer);

        eastCoCustomer_Username_Input_LOGIN = (EditText) findViewById(R.id.eastCoCustomer_Username_Input_LOGIN);
        eastCoCustomer_Password_Input_LOGIN = (EditText) findViewById(R.id.eastCoCustomer_Password_Input_LOGIN);

        eastCoCustomer_Login_Button = (Button) findViewById(R.id.eastCoCustomer_Login_Button);

        eastCoCustomer_Login_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Show dialog box
                final ProgressDialog progressDialog = new ProgressDialog(SignInActivity_Customer.this, R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("register ...");
                progressDialog.show();

                String username = eastCoCustomer_Username_Input_LOGIN.getText().toString().trim();
                String password = eastCoCustomer_Password_Input_LOGIN.getText().toString().trim();

                login(username, password);
            }

        });

    }

    private void login(String username, String password) {

        // USING HTTP URL CONNECTION
        class LoginUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            AuthenticateCustomer ac_LOGIN = new AuthenticateCustomer();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignInActivity_Customer.this, "Please Wait",null, true, true);
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
                data.put("password",params[1]);

                String result = ac_LOGIN.sendPostRequest(SIGN_IN_URL, data);

                return  result;
            }
        }

        LoginUser lu = new LoginUser();
        lu.execute(username, password);

    }

}
