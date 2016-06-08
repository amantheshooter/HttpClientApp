package testapplication.webkul.httpclientapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by shubham.agarwal on 25/5/16.
 */
public class MobikulHttpUrlConnection {


    private final onAsyncTaskCompleteInterface onAsyncTaskCompleteInterface;

    public MobikulHttpUrlConnection(onAsyncTaskCompleteInterface onAsyncTaskCompleteInterface) {
        this.onAsyncTaskCompleteInterface = onAsyncTaskCompleteInterface;
    }

    public AsyncTask<String, Void, String> getHttpUrlConnTask() {

        AsyncTask<String, Void, String> mHttpUrlConnTask = new AsyncTask<String, Void, String>() {
            public String controllerURL;

            @Override
            protected String doInBackground(String... params) {
                controllerURL= params[0];
                return getJsonFromServer(params);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (!result.isEmpty()) {
                    onAsyncTaskCompleteInterface.onTaskCompleted(result,controllerURL );
                }
            }
        };
        return mHttpUrlConnTask;
    }


    public String getJsonFromServer(String... params) {
        BufferedReader inputStream = null;
        URL url = null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "getJsonFromServer: " + url.toString());
        if (url != null) {
            HttpURLConnection httpURLConnection = null;
            String jsonResult = "";
            try {
                byte[] postDataBytes = params[1].getBytes("UTF-8");

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getOutputStream().write(postDataBytes);
                httpURLConnection.getOutputStream().flush();

                int responseCode = httpURLConnection.getResponseCode();
                Log.d("DEBUG", "getJsonFromServer: " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        jsonResult += line;
                    }
                    Log.d("DEBUG", "getJsonFromServer: " + jsonResult);
                }
//                inputStream = new BufferedReader(new InputStreamReader(
//                        httpURLConnection.getInputStream()));
//                jsonResult = inputStream.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    httpURLConnection = null;
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    inputStream = null;
                }
            }
            return jsonResult;
        }

        return "";
    }

}
