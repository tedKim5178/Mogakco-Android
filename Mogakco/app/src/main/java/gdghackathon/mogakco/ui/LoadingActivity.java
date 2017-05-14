package gdghackathon.mogakco.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.core.AppController;

public class LoadingActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    final String TAG = LoadingActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
//
//        final Intent intent  =
//                AppController.getInstance().getLocalStore().getStringValue("token","").isEmpty() ?
//                        LoginActivity.getStartIntent(LoadingActivity.this) : MainActivity.getStartIntent(LoadingActivity.this);
        final Intent intent = LoginActivity.getStartIntent(LoadingActivity.this);
        Log.d(TAG, "token 테스트 : " + AppController.getInstance().getLocalStore().getStringValue("token", ""));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500);

    }

    private void initializeLayout(){

    }
}
