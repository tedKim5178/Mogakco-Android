package gdghackathon.mogakco.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.model.MogakcoEvent;

/**
 * Created by choijinjoo on 2017. 2. 17..
 */

public class EventAdapter extends FirebaseRecyclerAdapter<MogakcoEvent, EventAdapter.EventViewHolder> {

    OnClickListener mListener;
    Context mContext;

    interface OnClickListener {
        void OnClick(MogakcoEvent event);
    }

    public EventAdapter(Context context, Query query, OnClickListener listener) {
        super(MogakcoEvent.class, R.layout.item_event, EventAdapter.EventViewHolder.class, query);
        this.mContext = context;
        this.mListener = listener;
    }

    public EventAdapter(Context context, DatabaseReference databaseReference, OnClickListener listener) {
        super(MogakcoEvent.class, R.layout.item_event, EventAdapter.EventViewHolder.class, databaseReference);
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_event, null));
    }

    @Override
    protected void populateViewHolder(EventViewHolder holder, final MogakcoEvent model, int position) {
        holder.txtvTitle.setText(model.getName());
        if (model.getImage() != null) {
            holder.imgvEvent.setImageURI(model.getImage());
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClick(model);
            }
        });
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.container)
        CardView container;
        @Bind(R.id.txtvTitle)
        TextView txtvTitle;
        @Bind(R.id.txtvLocation)
        TextView txtvLocation;
        @Bind(R.id.txtvDate)
        TextView txtvDate;
        @Bind(R.id.imgvEvent)
        SimpleDraweeView imgvEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
