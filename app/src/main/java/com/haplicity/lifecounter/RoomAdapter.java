package com.haplicity.lifecounter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Haplicity on 11/28/2015.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context mContext;
    List<RoomInfo> roomList;

    RoomAdapter(List<RoomInfo> rooms) {
        roomList = rooms;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mCardView;
        TextView mRoomName;
        TextView mCreatorName;
        TextView mRoomDescription;
        TextView mRoomCapacity;

        ViewHolder(View v) {
            super(v);
            mCardView = (CardView)itemView.findViewById(R.id.room_card_view);
            mRoomName = (TextView)itemView.findViewById(R.id.room_name);
            mCreatorName = (TextView)itemView.findViewById(R.id.creator_name);
            mRoomDescription = (TextView)itemView.findViewById(R.id.room_description);
            mRoomCapacity = (TextView)itemView.findViewById(R.id.room_capacity);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void delete(int position) {
        roomList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRoomName.setText(roomList.get(position).title);
        holder.mCreatorName.setText(roomList.get(position).creator);
        holder.mRoomDescription.setText(roomList.get(position).description);
        holder.mRoomCapacity.setText("" + roomList.get(position).currentPlayers +
                "/" + roomList.get(position).maxPlayers);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView v) {
        super.onAttachedToRecyclerView(v);
    }
}