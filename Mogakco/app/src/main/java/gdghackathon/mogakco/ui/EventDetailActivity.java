package gdghackathon.mogakco.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.core.AppController;
import gdghackathon.mogakco.model.MogakcoEvent;

/**
 * Created by choijinjoo on 2017. 2. 17..
 */

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.mapContainer)
    RelativeLayout mapContainer;
    @Bind(R.id.txtvTitle)
    TextView txtvTitle;
    @Bind(R.id.txtvDate)
    TextView txtvDate;
    @Bind(R.id.txtvDescription)
    TextView txtvDescription;
    @Bind(R.id.btnMapDetail)
    LinearLayout btnMapDetail;
    @Bind(R.id.btnJoin)
    RelativeLayout btnJoin;

    @Bind(R.id.imgvEvent)
    SimpleDraweeView imgvEvent;
    MapView mapView;
    MogakcoEvent mEvent;
    String eventKey;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("events");

    public static Intent getStartIntent(Context context, MogakcoEvent mogakcoEvent) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra("event", mogakcoEvent);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);
        ButterKnife.bind(this);
        mEvent = (MogakcoEvent) getIntent().getSerializableExtra("event");
        eventKey = mEvent.getEventId();

        if (mEvent.getParticipants() != null && !mEvent.getParticipants().isEmpty()) {
            String[] participants = mEvent.getParticipants().split(",");
            for (int i = 0; i < participants.length; i++) {
                if (participants[i].equals(AppController.getInstance().getLocalStore().getStringValue("uid", ""))) {
                    btnJoin.setBackgroundColor(getResources().getColor(R.color.greyDarkLight));
                    btnJoin.setClickable(false);
                }

            }
        }
        initializeLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mEvent.getLatlng() != null && !mEvent.getLatlng().isEmpty()) {
            drawMap(mEvent.getLatlng());
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapContainer.removeView(mapView);
    }

    private void initializeLayout() {
        txtvTitle.setText(mEvent.getName());
        txtvDescription.setText(mEvent.getDescription());

        btnMapDetail.setOnClickListener(this);

        if (mEvent.getImage() != null) {
            imgvEvent.setImageURI(mEvent.getImage());
        }
        btnJoin.setOnClickListener(this);

    }


    private void drawMap(String ll) {
        String[] latlng = ll.split(",");

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(AppController.getInstance().getLocalStore().getDaumMapAPIkey());
        mapContainer.addView(mapView);

        MapPOIItem poiItem = new MapPOIItem();

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Float.parseFloat(latlng[0]), Float.parseFloat(latlng[1]));
        poiItem.setMapPoint(mapPoint);

        MapPointBounds mapPointBounds = new MapPointBounds();
        mapPointBounds.add(mapPoint);
        poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem.setCustomImageResourceId(R.drawable.ic_marker);
        poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem.setCustomImageAutoscale(false);
        poiItem.setCustomSelectedImageResourceId(R.drawable.ic_marker_selected);
        poiItem.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(poiItem);
        mapView.fitMapViewAreaToShowAllPOIItems();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMapDetail:
                startActivity(MapDetailActivity.getStartIntent(EventDetailActivity.this, mEvent));
                break;
            case R.id.btnJoin:
                String participants = mEvent.getParticipants();
                if (mEvent.getParticipants() != null && !mEvent.getParticipants().isEmpty()) {
                    participants += "," + AppController.getInstance().getLocalStore().getStringValue("uid", "");
                } else {
                    participants = AppController.getInstance().getLocalStore().getStringValue("uid", "");
                }
                mEvent.setParticipants(participants);
                databaseReference.child(eventKey).updateChildren((HashMap) (mEvent.toMap()));
                // 사람 이벤트부분도 업데이트 해줘야겠는데...
//                databaseReference.child().updateChildren((HashMap));

                Toast.makeText(this, "참가신청이 완료되었습니다! ", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
