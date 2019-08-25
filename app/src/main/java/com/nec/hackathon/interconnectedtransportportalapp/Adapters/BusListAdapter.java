package com.nec.hackathon.interconnectedtransportportalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nec.hackathon.interconnectedtransportportalapp.BookTicketWithBusLocation;
import com.nec.hackathon.interconnectedtransportportalapp.Model.BusInfo;
import com.nec.hackathon.interconnectedtransportportalapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.MyViewHolder> {

    private List<BusInfo> busInfoList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView busNo, destination, arrivalTime, label;
//        public LatLng srcL, destL;
//        public ArrayList<LatLng> midStops;
        public TextView availability;

        public MyViewHolder(View view) {
            super(view);
            busNo = view.findViewById(R.id.busNoTextView);
            destination = view.findViewById(R.id.destinationTextView);
            arrivalTime = view.findViewById(R.id.arrivalTimeTextView);
            label = view.findViewById(R.id.labelTextView);
            availability = view.findViewById(R.id.seatAvailabilityTextView);
        }
    }

    public BusListAdapter(List<BusInfo> busInfoList, Context context) {
        this.busInfoList = busInfoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_info_single_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        BusInfo busInfo = busInfoList.get(position);
        holder.busNo.setText(busInfo.getBusNo());
        holder.destination.setText(busInfo.getDest());
        holder.label.setText(busInfo.getLabel());
        holder.availability.setText(busInfo.getSeatAvailability()+" Seats");
        holder.arrivalTime.setText(busInfo.getArrivalTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookTicketWithBusLocation.class);
                intent.putExtra("midStops", busInfoList.get(position).getMidStops());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return busInfoList.size();
    }
}