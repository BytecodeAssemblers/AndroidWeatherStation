package com.bytecodeassemblers.androidweatherstation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestApiCall {

 private JSONObject apiRequestPayload = null;
 private JSONObject apiResponsePayload =null;
 private  String url = "";



////////////////////////////////////////
    public JSONObject getApiResponsePayload() {
        return apiResponsePayload;
    }
    public void setApiResponsePayload(JSONObject apiResponsePayload) {
        this.apiResponsePayload = apiResponsePayload;
    }
///////////////////////////////////////

///////////////////////////////////////

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

///////////////////////////////////////


    public RestApiCall(String url) {
        this.url = url;

    }

///////////////////////////////////////

    public void executeCall() throws IOException, JSONException {                      //this function calls the api and gets a response with an answer (Json object)
        OkHttpClient client =  new OkHttpClient();

          Request request = new Request.Builder().url(this.url).get().build();

            Response response = client.newCall(request).execute();

              apiResponsePayload = new JSONObject(response.body().string());

    }


}
