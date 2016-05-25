package com.example.avinash.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public Button ins;
    public EditText NAME,ADDRESS;
    public TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ins=(Button)findViewById(R.id.button);
        NAME=(EditText)findViewById(R.id.editText);
        ADDRESS=(EditText)findViewById(R.id.editText2);
        status=(TextView)findViewById(R.id.textView3);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        { ins.setEnabled(true);
            ins.setClickable(true);
            Toast.makeText(this, "NETWORK HAI BHAI", Toast.LENGTH_LONG).show();}
        else
        {ins.setEnabled(false);
            ins.setClickable(false);
            Toast.makeText(this, "NETWORK NHI HAI BHAI", Toast.LENGTH_LONG).show();}
    }

    public void insert(View view){
        String name1=NAME.getText().toString();
        String addr1=ADDRESS.getText().toString();

        insertintodatabase(name1,addr1);
    }

    public void insertintodatabase(String name,String addr) {
        class abc extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... param) {
                String name = (String) param[0];
                String addr = (String) param[1];
                String link = "http://madrupam.16mb.com/insertdb.php";

                try {
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("addr", "UTF-8") + "=" + URLEncoder.encode(addr, "UTF-8");
                    URL url = new URL(link);

                    URLConnection con = url.openConnection();
                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    InputStreamReader in = new InputStreamReader(con.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    return sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return new String("Exception: " + e.getMessage());
                }

            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                status.setText("Inserted");
            }
        }
        /*SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, addr);*/
           abc ma=new abc();
        ma.execute(name,addr);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
