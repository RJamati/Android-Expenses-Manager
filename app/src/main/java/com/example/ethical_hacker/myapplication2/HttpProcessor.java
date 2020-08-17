package com.example.ethical_hacker.myapplication2;

//package com.engagemytime.app.utilities;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Toast;

        import com.squareup.okhttp.MediaType;
        import com.squareup.okhttp.MultipartBuilder;
        import com.squareup.okhttp.OkHttpClient;
        import com.squareup.okhttp.Request;
        import com.squareup.okhttp.RequestBody;
        import com.squareup.okhttp.Response;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.Objects;

/**
 * Created by comp17 on 5/8/15.
 */
public class HttpProcessor {

    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String PATCH = "PATCH";
    public final static String HEADER_POST = "HEADER_POST";
    public final static String HEADER_GET = "HESADER_GET";
    public final static String DELETE = "DELETE";
    public final static String IMAGE = "IMAGE";

    private Context context;
    private RequestBody requestBody;
    private String url;
    private String method;
    private boolean isShowDialog;
    private AsyncTask httpRequest;
    public Context caller;
    public MultipartBuilder builder;
    private OkHttpClient client;


    public HttpProcessor(Context context, boolean showDialog, String url, String method, RequestBody requestBody) {

        this.context = context;
        this.url = url;
        this.method = method;
        this.requestBody = requestBody;
        this.isShowDialog = showDialog;

    }


    public AsyncTask executeRequest(String TAG) {
        if (isConnected()) {
            httpRequest = new HttpRequest(TAG).execute(requestBody);
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return httpRequest;

    }


    public class HttpRequest extends AsyncTask<RequestBody, String, String> {

        ProgressDialog progressDialog;
        String TAG = "";

        public HttpRequest(String TAG) {
            this.TAG = TAG;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isShowDialog) {
                progressDialog = new ProgressDialog(context);
//                progressDialog.setIndeterminate(true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }


        }

        @Override
        protected String doInBackground(RequestBody... requestBody) {
            String result = "";

            if (Objects.equals(method, POST)) {
                result = makePostRequest(requestBody[0]);

            } else if (Objects.equals(method, GET)) {
                result = makeGetRequest();
            } else if (Objects.equals(method, PATCH)) {
                result = makePatchRequest(requestBody[0]);
            } else if (Objects.equals(method, IMAGE)) {
                result = makeMultyRequest(requestBody[0]);
            } else if (Objects.equals(method, HEADER_GET)) {
                result = makeGetHeaderRequest();
            } else if (Objects.equals(method, HEADER_POST)) {
                result = makePostHeaderRequest(requestBody[0]);
            }

            return result;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (null != progressDialog && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (null != result && null != httpResponserListener) {

                httpResponserListener.responseResult(result, TAG);
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }

        }

    }

    private String makeMultyRequest(RequestBody requestBody) {
        return null;
    }

    private String makePostRequest(RequestBody requestBody) {
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = null;
            response = client.newCall(request).execute();

            result = response.body().string();
            return result;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            Log.e("TAG", e.getMessage());
        }
        return result;

    }

    private String makePatchRequest(RequestBody requestBody) {
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .patch(requestBody)
                .build();

        try {
            Response response = null;
            response = client.newCall(request).execute();

            result = response.body().string();
            return result;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            Log.e("TAG", e.getMessage());
        }
        return result;

    }


    private String makeGetRequest() {

        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = null;
            response = client.newCall(request).execute();

            result = response.body().string();
            return result;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }
        return result;
    }

    private String makeGetHeaderRequest() {

        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("Authorization", "qwertyuiop")
                .header("Content-Type","application/json")
                .url(url)
                .build();

        try {
            Response response = null;
            response = client.newCall(request).execute();

            result = response.body().string();
            return result;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }
        return result;
    }

    private String makePostHeaderRequest(RequestBody requestBody) {
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("Authorization", "qwertyuiop")

                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = null;
            response = client.newCall(request).execute();

            result = response.body().string();
            return result;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            Log.e("TAG", e.getMessage());
        }
        return result;

    }

    // check network connection
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
        return false;
    }


    public HttpResponser httpResponserListener;

    public void setHttpResponserListener(HttpResponser httpResponserListener) {
        this.httpResponserListener = httpResponserListener;
    }

    public interface HttpResponser {

        public void responseResult(String result, String TAG);
    }


}


