package mj.eastco;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class login extends Activity {
    EditText usernameText;
    EditText passwordText;
    TextView registerBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Check if user ever login
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String UID = sharedPref.getString("UID", "null");

        super.onCreate(savedInstanceState);
        if (UID != "null") {
            startActivity(new Intent(getApplicationContext(), mainActivity.class));
            finish();
        }
        else {
            setContentView(R.layout.activity_login);

            //Initizalize View Component
            registerBtn = (TextView) findViewById(R.id.textView);
            loginBtn = (Button) findViewById(R.id.button);
            usernameText = (EditText) findViewById(R.id.username);
            passwordText = (EditText) findViewById(R.id.password);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), register.class));
                    finish();
                }
            });
        }
    }

    private void signIn() {
        //Initiate Request
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        String url = "http://contraffee.ml/api/v1/login";

        //Request Body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", usernameText.getText().toString());
            jsonBody.put("password", passwordText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();

        //POST Method
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        JSONObject responseObject = null;

                        try {
                            responseObject = new JSONObject(response);

                            //Add shared preference
                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("UID", responseObject.get("token").toString());
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Move to mainActivity
                        startActivity(new Intent(getApplicationContext(), mainActivity.class));
                        finish();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error);
                        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(postRequest);
    }
}
