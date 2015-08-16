package org.androidtown.mytap;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by constant on 15. 7. 16..
 */
public class MySharedPreference {
    Context context;

    public MySharedPreference(Context context)
    {
        this.context=context;
    }

    //값 불러오기
    public String getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getString("session", "");
    }
    // 값 저장하기
    public void savePreferences(String session){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("session", session);
        editor.commit();
    }
    //값 불러오기
    public String getCommentPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getString("comment", "");
    }
    // 값 저장하기
    public void saveCommentPreferences(String comment){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("comment", comment);
        editor.commit();
    }
    //값 불러오기
    public String getIDPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getString("id", "");
    }
    // 값 저장하기
    public void saveIDPreferences(String id){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", id);
        editor.commit();
    }//값 불러오기
    public String getPasswordPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getString("password", "");
    }
    // 값 저장하기
    public void savePasswordPreferences(String password){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("password", password);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public void removeIDPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("id");
        editor.commit();
    }
    public void removePWDPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("password");
        editor.commit();
    }
    public void removeSessionPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("session");
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
