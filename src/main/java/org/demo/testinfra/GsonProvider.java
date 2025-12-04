package org.demo.testinfra;

import com.google.gson.Gson;

public class GsonProvider {

    private static Gson gsonInstance;

    public static Gson getGson(){
        if(null == gsonInstance){
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }
}
