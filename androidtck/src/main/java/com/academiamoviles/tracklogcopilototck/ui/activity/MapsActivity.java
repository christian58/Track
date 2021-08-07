package com.academiamoviles.tracklogcopilototck.ui.activity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
//import androidx.appcompat.app.ActionBarActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.academiamoviles.tracklogcopilototck.R;
import com.academiamoviles.tracklogcopilototck.model.Coordinates;
import com.academiamoviles.tracklogcopilototck.ws.response.Poi;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineCallback;

import com.mapbox.android.core.location.LocationEngineResult;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

//public class MapsActivity extends BaseActivity implements OnMapReadyCallback, PermissionsListener {
public class MapsActivity extends BaseActivity implements PermissionsListener {

    private List<Point> routeCoordinates;

    LatLngBounds.Builder builder = new LatLngBounds.Builder();

    private ProgressBar progressBar;
    private MapView mapView;
    private OfflineManager offlineManager;

    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;

    private SymbolManager symbolManager;

    private PermissionsManager permissionsManager;

    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    private MainActivityLocationCallback callback = new MainActivityLocationCallback((this));

    ///
    //private GoogleMap mMap;
    List<Coordinates> listCoordinates;

    private Toolbar mToolbar;
////


    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";


    private boolean isEndNotified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        listCoordinates = (List<Coordinates>) getIntent().getSerializableExtra("listCoordinates");

        Log.d("TAMANO COORDINATES", String.valueOf(listCoordinates.size()));

        mapView = findViewById(R.id.mapReport);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap2) {

                initRouteCoordinates();

                mapboxMap = mapboxMap2;
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments.

                        symbolManager = new SymbolManager(mapView, mapboxMap, style);
                        symbolManager.setIconAllowOverlap(true);
                        symbolManager.setTextAllowOverlap(true);

                        offlineManager = OfflineManager.getInstance(MapsActivity.this);

                        offlineManager.setOfflineMapboxTileCountLimit(8000);

                        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                                .include(new LatLng(-4.0629, -68.9054)) // Northeast
                                .include(new LatLng(-18.2901, -81.2605)) // Southwest
                                .build();

// Define the offline region
                        OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                                style.getUri(),
                                latLngBounds,
                                9,
                                11,
                                MapsActivity.this.getResources().getDisplayMetrics().density);

                        // Implementation that uses JSON to store Yosemite National Park as the offline region name.
                        byte[] metadata;
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(JSON_FIELD_REGION_NAME, "Yosemite National Park");
                            String json = jsonObject.toString();
                            metadata = json.getBytes(JSON_CHARSET);
                        } catch (Exception exception) {
                            Log.e("TAG", "Failed to encode metadata: " + exception.getMessage());
                            metadata = null;
                        }


                        if (metadata != null) {

                            // Create the region asynchronously
                            offlineManager.createOfflineRegion(definition, metadata,
                                    new OfflineManager.CreateOfflineRegionCallback() {
                                        @Override
                                        public void onCreate(OfflineRegion offlineRegion) {
                                            offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);

                                            progressBar = findViewById(R.id.progress_bar);
                                            startProgress();

// Monitor the download progress using setObserver
                                            offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
                                                @Override
                                                public void onStatusChanged(OfflineRegionStatus status) {

// Calculate the download percentage
                                                    double percentage = status.getRequiredResourceCount() >= 0
                                                            ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                                                            0.0;

                                                    if (status.isComplete()) {
// Download complete
                                                        endProgress(getString(R.string.simple_offline_end_progress_success));


                                                        Log.d("TAG", "Region downloaded successfully.");
                                                    } else if (status.isRequiredResourceCountPrecise()) {
                                                        setPercentage((int) Math.round(percentage));
                                                        Log.d("TAG", String.valueOf(percentage));
                                                    }
                                                }

                                                @Override
                                                public void onError(OfflineRegionError error) {
// If an error occurs, print to logcat
                                                    Log.e("TAG", "onError reason: " + error.getReason());
                                                    Log.e("TAG", "onError message: " + error.getMessage());
                                                }

                                                @Override
                                                public void mapboxTileCountLimitExceeded(long limit) {
// Notify if offline region exceeds maximum tile count
                                                    Log.e("TAG", "Mapbox tile count limit exceeded: " + limit);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(String error) {
                                            Log.e("TAG", "Error: " + error);
                                        }
                                    });



                        }

                        if(routeCoordinates.size()<2){

//                            Log.d("LONGIGI", String.valueOf(listCoordinates.get(0).getLongitude()));
//                            Log.d("LATITI", String.valueOf(listCoordinates.get(0).getLatitude()));
                            IconFactory iconFactory = IconFactory.getInstance(MapsActivity.this);
                            resize2(getResources().getDrawable(R.drawable.markera) ) ;
                            Icon icon = iconFactory.fromBitmap(resize2(getResources().getDrawable(R.drawable.markera) ));

                            SymbolOptions symbolOptions;
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng( routeCoordinates.get(0).latitude(),routeCoordinates.get(0).longitude()))
                                    .icon(icon) );


                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new LatLng(routeCoordinates.get(0).latitude(), routeCoordinates.get(0).longitude()))
                                    .zoom(13)
                                    .tilt(20)
                                    .build();

                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

//                            LatLng coo = new LatLng(listCoordinates.get(0).getLatitude(), listCoordinates.get(0).getLongitude());
            //             mMap.addMarker(new MarkerOptions().position(coo).title("Mi Unidad").icon(BitmapDescriptorFactory.defaultMarker(
            //                     BitmapDescriptorFactory.HUE_RED
            //             )));
            //
            //            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coo, zoomLevel));



                        }else{

                            // POLYYY

                            // Create the LineString from the list of coordinates and then make a GeoJSON
// FeatureCollection so we can add the line to our map as a layer.
                            style.addSource(new GeoJsonSource("line-source",
                                    FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(
                                            LineString.fromLngLats(routeCoordinates)
                                    )})));

// The layer properties for our line. This is where we make the line dotted, set the
// color, etc.
                            style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
//                                    PropertyFactory.lineDasharray(new Float[] {0.01f, 2f}),
                                    PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                                    PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                                    PropertyFactory.lineWidth(5f),
                                    PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
                            ));
                            // END POLY

                            LatLngBounds bounds = builder.build();

                            int width = getResources().getDisplayMetrics().widthPixels;
                            int height = getResources().getDisplayMetrics().heightPixels;
                            int padding = (int) (width * 0.12);
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width,height,padding);
                            mapboxMap.moveCamera(cu);
                            mapboxMap.animateCamera(cu);


           // mMap.animateCamera(CameraUpdateFactory.newLa);
//                            PolylineOptions options = new PolylineOptions();
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            options.color( Color.BLUE );
//            options.width( 5 );
//            options.visible( true );
//
//            for ( int i=0; i<listCoordinates.size(); i++ )
//            {
//
//                options.add( new LatLng( listCoordinates.get(i).getLatitude(), listCoordinates.get(i).getLongitude() ));
//                builder.include(new LatLng(listCoordinates.get(i).getLatitude(), listCoordinates.get(i).getLongitude() ));
//            }
//
//
//            mMap.addPolyline( options );
//            LatLngBounds bounds = builder.build();
//           // System.out.println(bounds);
//
//            int width = getResources().getDisplayMetrics().widthPixels;
//            int height = getResources().getDisplayMetrics().heightPixels;
//            int padding = (int) (width * 0.12);
//            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
//            CameraUpdate cu =CameraUpdateFactory.newLatLngBounds(bounds, width,height,padding);
//            mMap.moveCamera(cu);
//            mMap.animateCamera(cu);
//           // mMap.animateCamera(CameraUpdateFactory.newLa);


                        }




//                        enableLocationComponent(style);



                    }
                });
            }
        });
    }

    private Bitmap resize2(Drawable image) {

        int width= Resources.getSystem().getDisplayMetrics().widthPixels;
        int height=Resources.getSystem().getDisplayMetrics().heightPixels;
        Log.d("MARKERSSIZE_W", String.valueOf(width));
        Log.d("MARKERSSIZE_H", String.valueOf(height));
//        D/MARKERSSIZE_W: 720
//        D/MARKERSSIZE_H: 1396
//        D/MARKERSSIZE_W: 1800
//        D/MARKERSSIZE_H: 2448

        Bitmap b = ((BitmapDrawable)image).getBitmap();
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 90, 135, false);
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 45, 67, false);
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 30, 46, false);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 20, 30, false);
//        return new BitmapDrawable(getResources(), bitmapResized);
        return bitmapResized;
    }

    // Add the mapView lifecycle to the activity's lifecycle methods

    private void initRouteCoordinates() {
        routeCoordinates = new ArrayList<>();//-16.432743, -71.532089
        for(int i= 0; i < listCoordinates.size(); i++){

            routeCoordinates.add(Point.fromLngLat(listCoordinates.get(i).getLongitude(), listCoordinates.get(i).getLatitude()));

            builder.include(new LatLng(listCoordinates.get(i).getLatitude(), listCoordinates.get(i).getLongitude() ));
        }

//        listCoordinates = new ArrayList<>();
//        listCoordinates.add(new Coordinates(-16.430403, -71.535351));
//        listCoordinates.add(new Coordinates(-16.432124, -71.535700));
//        listCoordinates.add(new Coordinates(-16.434867, -71.536447));
//        listCoordinates.add(new Coordinates(-16.435495, -71.536597));
//        listCoordinates.add(new Coordinates(-16.432743, -71.532089));



// Create a list to store our line coordinates.

//        routeCoordinates.add(Point.fromLngLat(-71.535351,-16.430403));
//        routeCoordinates.add(Point.fromLngLat(-71.535700, -16.432124));
//        routeCoordinates.add(Point.fromLngLat(-71.536447, -16.434867));
//        routeCoordinates.add(Point.fromLngLat(-71.536597, -16.435495));
//        routeCoordinates.add(Point.fromLngLat(-71.532089, -16.432743));


    }




    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle){
        Log.d("AQUI", "enableLocationComponent: ");
        if(PermissionsManager.areLocationPermissionsGranted(this)){

            LocationComponentOptions customLocationComponentOptions =
                    LocationComponentOptions.builder(this)
//                            .
//                            .elevation(5)
                            .accuracyAlpha(.6f)
                            .accuracyColor(Color.RED)

//                            .maxZoomIconScale(14)
//                            .minZoomIconScale(9)
//                            .foregroundDrawable(R.drawable.auto1)
                            .build();

            locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this,loadedMapStyle)
//                    .useDefaultLocationEngine(false)
//                    .build();
                            .locationComponentOptions(customLocationComponentOptions)

                            .build();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);
//            locationComponent.zoomWhileTracking(19);
//            locationComponent.zoo
            Log.d("AQUI", "enableLocationComponent: sec");

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
//            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
//            locationComponent.setRenderMode(RenderMode.COMPASS);
//            locationComponent.addOnCameraTrackingChangedListener(this);
//            locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);
            locationComponent.zoomWhileTracking(16);

//            CameraMode cameraMode = new CameraMode()

//            CameraPosition position = new CameraPosition.Builder()
//                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
//                    .zoom(11)
////                    .tilt(20)
//                    .build();

            initLocationEngine();

        }else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }




    }


    @SuppressLint("MissingPermission")
    private void initLocationEngine() {

        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);

//        locationEngine.getLastLocation(callback);
//        locationEngine.getLastLocation(callback);
//        request.
//        if (lastLocation != null) {
//            originLayout = lastLocation;
//            setCamerpostion(lastLocation);
//        } else {
//            locationEngine.addLocationEngineListener(this);
//        }
    }

    // Progress bar methods
    private void startProgress() {

// Start and show the progress bar
        isEndNotified = false;
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setPercentage(final int percentage) {
        progressBar.setIndeterminate(false);
        progressBar.setProgress(percentage);
    }

    private void endProgress(final String message) {
// Don't notify more than once
        if (isEndNotified) {
            return;
        }

// Stop and hide the progress bar
        isEndNotified = true;
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

// Show a toast
        Toast.makeText(MapsActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }



    private void setCamerpostion(Location camerpostion) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(camerpostion.getLatitude(), camerpostion.getLongitude()), 13.0));
    }

    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MapsActivity> activityWeakReference;

        MainActivityLocationCallback(MapsActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MapsActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

// Create a Toast which displays the new location's coordinates
//                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
//                        String.valueOf(result.getLastLocation().getLatitude()), String.valueOf(result.getLastLocation().getLongitude())),
//                        Toast.LENGTH_SHORT).show();

// Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                    activity.setCamerpostion(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            MapsActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



//MAPBOX

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();


        if (offlineManager != null) {
            offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
                @Override
                public void onList(OfflineRegion[] offlineRegions) {
                    if (offlineRegions.length > 0) {
// delete the last item in the offlineRegions list which will be yosemite offline map
                        offlineRegions[(offlineRegions.length - 1)].delete(new OfflineRegion.OfflineRegionDeleteCallback() {
                            @Override
                            public void onDelete() {
                                Toast.makeText(
                                        MapsActivity.this,
                                        getString(R.string.basic_offline_deleted_toast),
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                            @Override
                            public void onError(String error) {
                                Log.d("MENSAJE", "onError: "+error);
//                                Timber.e("On delete error: %s", error);
                            }
                        });
                    }
                }

                @Override
                public void onError(String error) {
//                    Timber.e("onListError: %s", error);
                    Log.d("MENSAJE22", "onError: "+error);
                }
            });
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_maps);
//        //getActionBarToolbar();
//        //listCoordinates = (List<Coordinates>) getIntent().getSerializableExtra("listCoordinates");
//
//      /*  listCoordinates = new ArrayList<>();
//        listCoordinates.add(new Coordinates(-16.407816, -71.521734));
//        listCoordinates.add(new Coordinates(-16.408041, -71.522021));
//        listCoordinates.add(new Coordinates(-16.408306, -71.522300));
//        listCoordinates.add(new Coordinates(-16.408960, -71.523714));
//        listCoordinates.add(new Coordinates(-16.409572, -71.523435));
//        listCoordinates.add(new Coordinates(-16.412652, -71.520473));
//*/
//        //System.out.println(this.getApplicationInfo().dataDir);
//
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
////        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
////        if(status == ConnectionResult.SUCCESS){
////            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                    .findFragmentById(R.id.map);
////            mapFragment.getMapAsync(this);
////        }else{
////            //System.out.println("NO SE INICIO MAPA");
////            /*Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity)getApplicationContext(),10);
////            dialog.show();*/
////        }
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        float zoomLevel = 16;
//        UiSettings uiSettings = mMap.getUiSettings();
//        uiSettings.setZoomControlsEnabled(true);
//
//        // Add a marker in Sydney and move the camera
//        /*    LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("HOLA MUNDO").icon(BitmapDescriptorFactory.defaultMarker(
//                BitmapDescriptorFactory.HUE_AZURE
//        )));
//
//
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
//*/
//
//        if(listCoordinates.size()<2){
//             LatLng coo = new LatLng(listCoordinates.get(0).getLatitude(), listCoordinates.get(0).getLongitude());
//             mMap.addMarker(new MarkerOptions().position(coo).title("Mi Unidad").icon(BitmapDescriptorFactory.defaultMarker(
//                     BitmapDescriptorFactory.HUE_RED
//             )));
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coo, zoomLevel));
//        }else{
//            PolylineOptions options = new PolylineOptions();
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            options.color( Color.BLUE );
//            options.width( 5 );
//            options.visible( true );
//
//            for ( int i=0; i<listCoordinates.size(); i++ )
//            {
//
//                options.add( new LatLng( listCoordinates.get(i).getLatitude(), listCoordinates.get(i).getLongitude() ));
//                builder.include(new LatLng(listCoordinates.get(i).getLatitude(), listCoordinates.get(i).getLongitude() ));
//            }
//
//
//            mMap.addPolyline( options );
//            LatLngBounds bounds = builder.build();
//           // System.out.println(bounds);
//
//            int width = getResources().getDisplayMetrics().widthPixels;
//            int height = getResources().getDisplayMetrics().heightPixels;
//            int padding = (int) (width * 0.12);
//            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
//            CameraUpdate cu =CameraUpdateFactory.newLatLngBounds(bounds, width,height,padding);
//            mMap.moveCamera(cu);
//            mMap.animateCamera(cu);
//           // mMap.animateCamera(CameraUpdateFactory.newLa);
//
//        }
//
//
//
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_type_map, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.normal:
//                //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                return true;
//            case R.id.hybrid:
//                //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                return true;
//            case R.id.satellite:
//                //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                return true;
//            case R.id.terrain:
//                //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private Toolbar getActionBarToolbar() {
//        if (mToolbar == null) {
//            mToolbar = (Toolbar) findViewById(R.id.toolbar);
//            if (mToolbar != null) {
//                setSupportActionBar(mToolbar);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            }
//        }
//        return mToolbar;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

}
