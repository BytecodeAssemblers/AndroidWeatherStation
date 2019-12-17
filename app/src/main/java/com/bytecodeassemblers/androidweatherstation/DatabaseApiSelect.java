package com.bytecodeassemblers.androidweatherstation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DatabaseApiSelect {

    private RequestQueue Queue;

    private String databaseSelectEndpoint;

    private Context context;

    private JSONObject payload;

    /*--------------------*/


    public RequestQueue getQueue() {
        return Queue;
    }

    public void setQueue(RequestQueue queue) {
        Queue = queue;
    }

    public String getDatabaseSelectEndpoint() {
        return databaseSelectEndpoint;
    }

    public void setDatabaseSelectEndpoint(String databaseSelectEndpoint) {
        this.databaseSelectEndpoint = databaseSelectEndpoint;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload;
    }

    /*--------------------*/

    public void executeSelect(){                            /*this function inserts data from the api calls that are implemented*/

        /* Instantiate the RequestQueue.*/
        Queue = Volley.newRequestQueue(context);


/*Request a response from the provided URL.*/

        JsonObjectRequest databaseRequest = new JsonObjectRequest(Request.Method.POST, databaseSelectEndpoint,payload,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("SUCCESS");

                    }
                }

                ,

                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("ERROR On Select");
                System.out.println(error.getMessage());

            }
        });
        // Add the request to the RequestQueue.
        Queue.add(databaseRequest);


    }


}
