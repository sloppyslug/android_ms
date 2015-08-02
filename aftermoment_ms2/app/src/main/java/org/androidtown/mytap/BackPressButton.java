package org.androidtown.mytap;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by MSPark on 2015-08-03.
 */

//이 CLASS는 뒤로가기 버튼을 두번 눌러야지만 앱이 종료되는 것을 하기위해 만든 CLASS 야 //
public class BackPressButton {

private long backKeyPressedTime = 0;
private Toast toast;

private Activity activity;

    public BackPressButton(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }




    private void showGuide() {
        toast = Toast.makeText(activity, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

}