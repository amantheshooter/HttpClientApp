package testapplication.webkul.httpclientapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements onAsyncTaskCompleteInterface {

    private MobikulHttpUrlConnection mMobikulHttpUrlConnection;
    private AsyncTask<String, Void, String> mAsyncTask;

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
                int spacesToIndentEachLevel = 2;
                try {
                    request();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void request() {
        mMobikulHttpUrlConnection = new MobikulHttpUrlConnection(this);
        mAsyncTask = mMobikulHttpUrlConnection.getHttpUrlConnTask();
        try {
            mAsyncTask.execute(((EditText) findViewById(R.id.url_et)).getText().toString(), getPostDataBytes());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public String getPostDataBytes() throws UnsupportedEncodingException {

        EditText key1 = (EditText) findViewById(R.id.key1_et);
        EditText value1 = (EditText) findViewById(R.id.value1_et);
        EditText key2 = (EditText) findViewById(R.id.key2_et);
        EditText value2 = (EditText) findViewById(R.id.value2_et);
        EditText key3 = (EditText) findViewById(R.id.key3_et);
        EditText value3 = (EditText) findViewById(R.id.value3_et);
        EditText key4 = (EditText) findViewById(R.id.key4_et);
        EditText value4 = (EditText) findViewById(R.id.value4_et);
        EditText key5 = (EditText) findViewById(R.id.key5_et);
        EditText value5 = (EditText) findViewById(R.id.value5_et);
        EditText key6 = (EditText) findViewById(R.id.key6_et);
        EditText value6 = (EditText) findViewById(R.id.value6_et);
        EditText key7 = (EditText) findViewById(R.id.key7_et);
        EditText value7 = (EditText) findViewById(R.id.value7_et);
        EditText key8 = (EditText) findViewById(R.id.key8_et);
        EditText value8 = (EditText) findViewById(R.id.value8_et);
        EditText key9 = (EditText) findViewById(R.id.key9_et);
        EditText value9 = (EditText) findViewById(R.id.value9_et);
        EditText key10 = (EditText) findViewById(R.id.key10_et);
        EditText value10 = (EditText) findViewById(R.id.value10_et);
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(key1.getText().toString(), value1.getText().toString());
        params.put(key2.getText().toString(), value2.getText().toString());
        params.put(key3.getText().toString(), value3.getText().toString());
        params.put(key4.getText().toString(), value4.getText().toString());
        params.put(key5.getText().toString(), value5.getText().toString());
        params.put(key6.getText().toString(), value6.getText().toString());
        params.put(key7.getText().toString(), value7.getText().toString());
        params.put(key8.getText().toString(), value8.getText().toString());
        params.put(key9.getText().toString(), value9.getText().toString());
        params.put(key10.getText().toString(), value10.getText().toString());

        StringBuilder postDataString = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postDataString.length() != 0) postDataString.append('&');
            postDataString.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postDataString.append('=');
            postDataString.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        Log.d("params", params + "");
//        return postDataString.toString().getBytes("UTF-8");
        return postDataString.toString();
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

    @Override
    public void onTaskCompleted(String backResult, String methodName) {
//        Snackbar.make((LinearLayout) findViewById(R.id.container), backResult + "", Snackbar.LENGTH_LONG).show();
        ((TextView)findViewById(R.id.result)).setText(Html.fromHtml("<pre>"+backResult+"</pre>"));

    }
}
