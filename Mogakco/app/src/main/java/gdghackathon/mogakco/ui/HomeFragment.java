package gdghackathon.mogakco.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.base.CustomAdapter;
import gdghackathon.mogakco.model.MogakcoEvent;
import gdghackathon.mogakco.tools.bannerImageAdapter;

/**
 * Created by choijinjoo on 2017. 2. 16..
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.recvEvent)
    RecyclerView recvEvent;
    @Bind(R.id.recvPremium)
    RecyclerView recvPremium;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    bannerImageAdapter topBannerAdapter;
    EventAdapter mAdapter;

    Timer swipeTimer;
    Handler handler;
    Runnable update;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        loadEvent();
        initializeLayout();
        return v;
    }

    private void initializeLayout() {

        recvEvent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        topBannerAdapter = new bannerImageAdapter(getFragmentManager());
        recvEvent.setAdapter(mAdapter);

        topBannerAdapter.addBannerImage("http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_1.png");
        topBannerAdapter.addBannerImage("http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_2.png");
        topBannerAdapter.addBannerImage("http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_3.png");
        topBannerAdapter.addBannerImage("http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_4.png");
//        mPager.setAdapter(topBannerAdapter);
//        mPager.setCurrentItem(0);
//        topBannerAdapter.notifyDataSetChanged();
//
//
//        mPager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MapDialog.init(getActivity()).show();
//            }
//        });

        final Resources resources = this.getResources();
        final float density = resources.getDisplayMetrics().density;
        final PremiemBannerAdapter adapter = new PremiemBannerAdapter(getContext(), null);
        recvPremium.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recvPremium.setAdapter(adapter);

        adapter.add("https://firebasestorage.googleapis.com/v0/b/mogakko-f050a.appspot.com/o/banner%2FAWS_Summit.jpg?alt=media&token=9876c967-9ded-45e3-ba90-cb15c4f7e5ce");
        adapter.add("https://firebasestorage.googleapis.com/v0/b/mogakko-f050a.appspot.com/o/banner%2Fazure.jpg?alt=media&token=70a2c753-5440-4445-89b0-cb04609ad211");

//        indicator.setRadius(3 * density);
//        indicator.setGap(3 * density);
//        indicator.setPageColor(resources.getColor(R.color.black));
//        indicator.setFillColor(resources.getColor(R.color.white));
//        indicator.setStrokeWidth(0);
//        indicator.setViewPager(mPager);
//
//
//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                restartSwipeTimer();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        if (handler == null) {
            handler = new Handler();
        }

        if (update == null) {
            update = new Runnable() {
                public void run() {
                    recvPremium.scrollToPosition(i);
                    i = i > 1 ? 0 : i++;

                }
            };
        }

    }

    int i = 0;

    private void loadEvent() {
        databaseReference = databaseReference.child("events");
        mAdapter = new EventAdapter(getActivity(), databaseReference, new EventAdapter.OnClickListener() {
            @Override
            public void OnClick(MogakcoEvent event) {
                startActivity(EventDetailActivity.getStartIntent(getActivity(), event));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void restartSwipeTimer() {
        if (swipeTimer != null) {
            swipeTimer.cancel();
        }
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000);
    }


    class PremiemBannerAdapter extends CustomAdapter<String, PremiemBannerAdapter.BannerViewHolder> {
        public PremiemBannerAdapter(Context context, List<String> data) {
            super(context, data);
        }

        @Override
        public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_banner, null));
        }

        @Override
        public void onBindViewHolder(BannerViewHolder holder, int position) {
            holder.imgvContent.setImageURI(mItems.get(position));
        }

        class BannerViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.imgvContent)
            SimpleDraweeView imgvContent;

            public BannerViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
