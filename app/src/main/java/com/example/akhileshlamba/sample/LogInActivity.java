package com.example.akhileshlamba.sample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akhileshlamba.entities.House;
import com.example.akhileshlamba.entities.ResCredientials;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class LogInActivity extends AppCompatActivity {


    EditText userNameTv;
    EditText passwordTv;

    public void newUser(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void getMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    public void validate(View view) {
        new Login().execute("", null , null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userNameTv = (EditText) findViewById(R.id.username);
        passwordTv = (EditText) findViewById(R.id.pwd);
    }



    private class Login extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... voids) {
            String url1 = "com.project.entities.rescredientials/validateUser/";
            JSONArray array = RESTConnection.validateUser(RESTConnection.createConnection(url1 + userNameTv.getText().toString()));
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            if (jsonArray.length() != 0) {
                try{
                    if(BCrypt.checkpw(passwordTv.getText().toString(), jsonArray.getJSONObject(0).getString("password"))) {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

                        JSONObject object = jsonArray.getJSONObject(0);

                        JSONObject resident = object.getJSONObject("resid");

                        ResCredientials resCredientials = new ResCredientials();
                        resCredientials.setPassword(object.getString("password"));
                        resCredientials.setUserName(object.getString("username"));
                        resCredientials.setRegDate(new Date(object.getString("regDate")));
                        resCredientials.setResid(object.getInt("resid"));

                        House house = new House();
                        house.setAddress(object.getString("address"));
                        house.setDob(new Date(object.getString("dob")));
                        house.setEmail(object.getString("email"));
                        house.setFirstName(object.getString("firstname"));
                        house.setLastName(object.getString("lastname"));
                        house.setEnergyproviderName(object.getString("energyproviderName"));
                        house.setMobile(object.getLong("mobile"));
                        house.setNoOfOccupants(object.getInt("noOfOccupants"));
                        house.setPostcode(object.getInt("postcode"));
                        house.setResid(object.getInt("resid"));

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("house", house);
                        bundle.putParcelable("credientials", resCredientials);
                        intent.putExtra("profile", bundle);
                        startActivity(intent);
                        finish();
                    }else
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }else
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

            super.onPostExecute(jsonArray);
        }
    }

}
