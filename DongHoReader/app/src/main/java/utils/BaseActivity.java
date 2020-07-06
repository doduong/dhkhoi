package utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

import app.cntt.cnhp.hpwaterbarcodereader.Login;

public class BaseActivity extends AppCompatActivity{

    public static final long DISCONNECT_TIMEOUT = 900000; // 30 sec = 30 * 1000 ms
    private long pausedMillis;

    private Handler disconnectHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    /*private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    BaseActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Thông báo");
            alertDialog.setMessage("Kết thúc phiên làm việc, trở về màn hình đăng nhập!");
            alertDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(BaseActivity.this,
                                    Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            dialog.cancel();
                        }
                    });

            alertDialog.show();

            // Perform any required operation on disconnect
        }
    };

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }


    @Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }
    */

    @Override
    public void onResume() {
        //disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
        super.onResume();
        try {
            long currentMillis = Calendar.getInstance().getTimeInMillis();
            /*Log.d("pausedMillis", String.valueOf(pausedMillis));
            Log.d("currentMillis", String.valueOf(currentMillis));
            Log.d("current- paused", String.valueOf(currentMillis - pausedMillis));*/
            if(pausedMillis!= 0 && currentMillis - pausedMillis > DISCONNECT_TIMEOUT) {

                if (!(this instanceof Login)) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            BaseActivity.this);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Thông báo");
                    alertDialog.setMessage("Kết thúc phiên làm việc, trở về màn hình đăng nhập!");
                    alertDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(BaseActivity.this,
                                            Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                    //Intent intent = new Intent(this, Login.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);
                    //finish();
                }
            }else {
                pausedMillis = 0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //resetDisconnectTimer();

    }

    @Override
    public void onStop() {
        super.onStop();
        //stopDisconnectTimer();
        pausedMillis = Calendar.getInstance().getTimeInMillis();
        Log.d("pausedMillis_stop", String.valueOf(pausedMillis));
    }


}
