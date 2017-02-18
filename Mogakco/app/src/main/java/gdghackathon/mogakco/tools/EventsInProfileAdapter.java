package gdghackathon.mogakco.tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gdghackathon.mogakco.R;
import gdghackathon.mogakco.model.Event;

/**
 * Created by mk on 2017-02-17.
 */

public class EventsInProfileAdapter extends RecyclerView.Adapter<EventsInProfileAdapter.EventsInfProfileViewHolder>{
    private static final String TAG = EventsInProfileAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Event> mEventList;

    public EventsInProfileAdapter(Context mContext, ArrayList<Event> mEventList){
        this.mContext = mContext;
        this.mEventList = mEventList;
    }

    @Override
    public EventsInfProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.events_in_profile_item, parent, false);

        Log.d(TAG, "호출테스트1");
        return new EventsInfProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsInfProfileViewHolder holder, int position) {
        // 이제 뷰에 profile정보 이용해서 묶어주자

        String url = mEventList.get(position).image_url;
        String name = mEventList.get(position).name;
        Glide.with(mContext).load(url).into(holder.imageView);
        holder.textView.setText(name);

    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    class EventsInfProfileViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public EventsInfProfileViewHolder(View itemView){
            super(itemView);
            Log.d(TAG,"호출테스트 Viewholder");
            imageView = (ImageView)itemView.findViewById(R.id.show_imageOfEvents_in_fragment_profile);;
            textView = (TextView)itemView.findViewById(R.id.show_nameOfEvents_in_fragment_profile);

        }


    }
}
