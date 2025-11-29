package org.testinfra;

import com.google.gson.Gson;

public class GsonHelper {

    private static Gson gsonInstance;

    public static Gson getGson(){
        if(null == gsonInstance){
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }
}
