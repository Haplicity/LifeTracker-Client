package com.haplicity.lifecounter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Haplicity on 11/28/2015.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    Context context;
    List<RoomInfo> roomList;
    DataStore dataStore;

    RoomAdapter(List<RoomInfo> rooms, Context c) {
        context = c;
        roomList = rooms;
        dataStore = DataStore.get(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRoomName;
        TextView mRoomDescription;
        TextView mRoomCapacity;

        ViewHolder(View v) {
            super(v);
            mCardView = (CardView)itemView.findViewById(R.id.room_card_view);
            mRoomName = (TextView)itemView.findViewById(R.id.room_name);
            mRoomDescription = (TextView)itemView.findViewById(R.id.room_description);
            mRoomCapacity = (TextView)itemView.findViewById(R.id.room_capacity);

            mCardView.setOnClickListener(
                    new RecyclerView.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clicked(getAdapterPosition());
                        }
                    }
            );
        }
    }

    public void clicked(int position){
        if (roomList.get(position).currentPlayers == 4) {
            dataStore.setRoom(null);
            Toast toast = Toast.makeText(context, "Room is full", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            dataStore.setRoom(roomList.get(position));

            Toast toast = Toast.makeText(context, "'" + dataStore.getRoom().title + "' selected", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRoomName.setText(roomList.get(position).title);
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