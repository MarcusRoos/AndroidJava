package se.miun.maro1904.dt031.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        mMap = googleMap;
        LatLngBounds kov =  new LatLngBounds((new LatLng(55.001099, 11.10694)), new LatLng(69.063141, 24.16707));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(kov, 100));

        new DatabaseCaller(this::addMarkers).execute();
    }

    private void addMarkers(List<CallHistory> callHistory) {
        for (CallHistory entity : callHistory) {
            if (!entity.getLat().equals("null") && !entity.getLng().equals("null")) {
                LatLng loc = new LatLng(Double.parseDouble(entity.getLat()), Double.parseDouble(entity.getLng()));

                mMap.addMarker(new MarkerOptions()
                        .position(loc)
                        .title(entity.getNumber())
                        .snippet(entity.getDate())
                );
            }
        }
    }
}