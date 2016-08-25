package first.raspberrycontrol;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********************************/
         /*    Define all the buttons    */
        /********************************/
        Switch led1 = (Switch) findViewById(R.id.Led1);
        ToggleButton led2 = (ToggleButton) findViewById(R.id.Led2);
        Button led3 = (Button) findViewById(R.id.Led3);


        /*******************************************************/
         /*  Set an onclick/onchange listener for every button  */
        /*******************************************************/

        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* Switch is led 1 */
                    new Background_get().execute("led1=1");
                } else {
                    new Background_get().execute("led1=0");
                }
            }
        });

        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /* Toggle button is led 2 */
                    new Background_get().execute("led2=1");
                } else {
                    new Background_get().execute("led2=0");
                }
            }
        });

        led3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    /* button is led 3 */
                    new Background_get().execute("led3=1");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Background_get().execute("led3=0");
                }
                return true;
            }
        });
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

    /*****************************************************/
       /*  This is a background process for connecting      */
      /*   to the arduino server and sending               */
     /*    the GET request withe the added data           */
    /*****************************************************/

    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL("http://192.168.2.37/?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
