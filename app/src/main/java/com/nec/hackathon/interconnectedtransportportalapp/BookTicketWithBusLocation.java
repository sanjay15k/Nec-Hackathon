package com.nec.hackathon.interconnectedtransportportalapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.nec.hackathon.interconnectedtransportportalapp.Model.BusInfo;

import java.util.ArrayList;
import java.util.List;

public class BookTicketWithBusLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket_with_bus_location);

        ArrayList<LatLng> midStops = (ArrayList<LatLng>) getIntent().getSerializableExtra("midStops");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookTicketWithBusLocation.this, PayableAmountScreen.class);
                CommonUtils commonUtils = new CommonUtils();
                commonUtils.waitAndNavigate(BookTicketWithBusLocation.this, intent, "Please wait redirecting", 2000);

            }
        });

        double srcX = midStops.get(0).latitude;
        double srcY = midStops.get(0).longitude;
        double destX = midStops.get(midStops.size()-1).latitude;
        double destY = midStops.get(midStops.size()-1).longitude;

        GoogleDirection.withServerKey("AIzaSyBGxDbLF0b_MyKBNJeBPt8TpGk17dC62xs")
                .from(new LatLng(srcX, srcY))
                .to(new LatLng(destX, destY))
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> sectionPositionList = leg.getSectionPoint();
                            System.out.println("################ Marking LIST  "+sectionPositionList);
                            for (int i=1; i<sectionPositionList.size()-1; i++) {
                                LatLng position = sectionPositionList.get(i);
                                googleMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            }
                            List<Step> stepList = leg.getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getApplicationContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                            for (PolylineOptions polylineOption : polylineOptionList) {
                                googleMap.addPolyline(polylineOption);
                            }
                            setCameraWithCoordinationBounds(route);

                        } else {
                            Snackbar.make(findViewById(R.id.map), direction.getStatus(), Snackbar.LENGTH_SHORT).show();
                        }
                        System.out.println("************** "+ direction.getStatus());
                        System.out.println(direction.getErrorMessage());
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
