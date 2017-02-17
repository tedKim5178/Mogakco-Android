//package gdghackathon.mogakco.ui;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageButton;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import gdghackathon.mogakco.R;
//
///**
// * Created by mk on 2017-02-18.
// */
//
//public class AddEventActivity extends AppCompatActivity{
//    private static final String TAG = ProfileFragment.class.getSimpleName();
//
//
//    @Bind(R.id.image_location_in_activity_add)
//    ImageButton image_location_in_activity_add;
//
//    MapDialog mapDialog = new MapDialog(this);
//    public String locationLat;
//    public String locationLong;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add);
//        ButterKnife.bind(this);
//
//
//
//    }
//
//    @OnClick(R.id.image_location_in_activity_add)
//    public void onClick(View view){
//        // 이제 이거 버튼이 눌리면 좌표 가져오는거 해야됨.
//        MapDialog.init(this, new MapDialog.GetLocation() {
//            @Override
//            public void getGPS() {
//
//                Log.d(TAG, "여기뜨냐");
//            }
//        }).show();
//    }
//
//
//}
