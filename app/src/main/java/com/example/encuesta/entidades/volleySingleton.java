package com.example.encuesta.entidades;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class volleySingleton {
    private static volleySingleton instanciaVolley;
    private RequestQueue request;
    private static Context contexto;

    private volleySingleton(Context context) {
        contexto = context;
        request = getRequestQueue();


    }



    public static synchronized volleySingleton getInstanciaVolley(Context context){
       if(instanciaVolley == null){

           instanciaVolley = new volleySingleton(context);

       }

        return instanciaVolley;
    }

    private RequestQueue getRequestQueue() {
        if(request==null){

            request = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        request.getCache().clear();
        return request;
    }

    public <T> void addToRequestQueue(Request<T> request){

        getRequestQueue().add(request);

    }
}
