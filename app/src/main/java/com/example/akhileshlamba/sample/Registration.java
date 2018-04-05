package com.example.akhileshlamba.sample;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.DatePicker;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Calendar;

public class Registration extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText postcode;
    EditText userName;
    EditText email;
    EditText pwd;
    EditText contact;
    EditText address;
    EditText energy;
    TextView dob;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.pwd);
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.address);
        energy = (EditText) findViewById(R.id.energy);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        postcode = (EditText) findViewById(R.id.postcode);
        dob = (TextView) findViewById(R.id.dateTv);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.occupants, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void pickDate(View view){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DATE);

        month = month;

        DatePickerDialog pickerDialog = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String dateSet = i2 + "/" + (i1+1) + "/" + i;
                dob.setText(dateSet);
            }
        }, year, month, dayOfMonth);
        pickerDialog.show();
    }

    public void createUser(View view){

        String username = userName.getText().toString();
        String firstname = firstName.getText().toString();
        String lastname = lastName.getText().toString();
        String pword = pwd.getText().toString();
        String emailId = email.getText().toString();
        String phone = contact.getText().toString();
        String code = postcode.getText().toString();
        String dateOfBirth = dob.getText().toString();
        String energyProvider = energy.getText().toString();
        String add = address.getText().toString();
        String occupants = spinner.getSelectedItem().toString();
        String pwdd = BCrypt.hashpw(pword, BCrypt.gensalt());
        Log.i("email", emailId);
        Log.i("password", pwdd);
        Log.i("result", String.valueOf(BCrypt.checkpw("rahul", pwdd)));

        if(username.length() == 0 || firstname.length() == 0 || lastname.length() == 0 || pword.length() == 0 ||
                emailId.length() == 0 || phone.length() == 0 || code.length() == 0 || dateOfBirth.length() == 0 ||
                energyProvider.length() == 0 || add.length() == 0 || occupants.length() == 0) {
            Toast.makeText(this,"No field should be empty", Toast.LENGTH_LONG).show();
        } else {

            String[] data = {username, firstname, lastname, pwdd, emailId, phone, code, dateOfBirth, energyProvider, add, occupants};
            new RegistrationTask().execute(data);
        }




    }

    private class RegistrationTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... voids) {
            String url1 = "com.project.entities.house/createUser";
            HttpURLConnection urlConnection = RESTConnection.createConnection(url1);
            try{

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                JSONObject object = new JSONObject();
                object.put("username", voids[0].toString());
                object.put("firstname", voids[1].toString());
                object.put("lastname", voids[2].toString());
                object.put("pwd", voids[3].toString());
                object.put("emailId", voids[4].toString());
                object.put("phone", Integer.valueOf(voids[5]));
                object.put("code", Integer.valueOf(voids[6]));
                object.put("dateOfBirth", voids[7].toString());
                object.put("energyProvider", voids[8].toString());
                object.put("add", voids[9].toString());
                object.put("occupants", Integer.valueOf(voids[10]));

                OutputStreamWriter wr= new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");

                wr.write(object.toString());
                wr.flush();
                wr.close();

                Log.i("URLCOnnection", urlConnection.toString());

                int code = urlConnection.getResponseCode();
                Log.i("code", String.valueOf(code));
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer json = new StringBuffer(1024);
                String tmp="";
                while((tmp=reader.readLine())!=null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());
                Log.i("JSON", json.toString());
                return data;

            }catch(Exception e){
                Log.e("Exception", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
        }
    }


}
