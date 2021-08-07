package com.academiamoviles.tracklogcopilototck.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.academiamoviles.tracklogcopilototck.R;
import com.academiamoviles.tracklogcopilototck.database.DatabaseHelper;
import com.academiamoviles.tracklogcopilototck.ui.Configuration;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.academiamoviles.tracklogcopilototck.ws.response.Poi;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineCallback;

import com.mapbox.android.core.location.LocationEngineResult;


import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolDragListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import androidx.annotation.NonNull;

/**
 * Created by Android on 06/02/2017.
 */

public class CopilotoActivity extends BaseActivity implements OnMapReadyCallback, PermissionsListener {

    public static final String COD_RUTA = "cod_ruta";
    public static final String COD_RUTA_2 = "98";
    public  String COD_RUTA_4 = "9";
    private MediaPlayer mp;
    public static final String SHOW_MAP = "show_map";

//    private MapView mapView;

//  SAVE DATABASE
DatabaseHelper mDBHelper;

    public static final Date currentTime = Calendar.getInstance().getTime();

    public  Date currentTime2;
    public  Date currentTime2F;


    private boolean isEndNotified;
    private ProgressBar progressBar;
    private MapView mapView;
    private OfflineManager offlineManager;

    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;

    public MarkerOptions auto_marker;
    public Marker auto_marker_f;


    private static final String MAKI_ICON_CAFE = "cafe-15";
    private static final String MAKI_ICON_HARBOR = "harbor-15";
    private static final String MAKI_ICON_AIRPORT = "airport-15";

    private SymbolManager symbolManager;
    private Symbol symbol;

    private PermissionsManager permissionsManager;

    private LocationEngine locationEngine;
    private LocationEngine locationEngineSecond;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1L;
//    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS;

    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);
//    private


    Location originLayout;


    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";
    public int updateZoom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MAPBOX

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));


        // END MAPBOX
        setContentView(R.layout.copiloto_activity);

        //MAPBOX

        mapView = (MapView) findViewById(R.id.mapViewBox);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

//        mapView.getMapAsync(new OnMapReadyCallback() {
//
//
//        });





        currentTime2 = Calendar.getInstance().getTime();
        Log.d("TiempooInicial", String.valueOf(currentTime2));

        mDBHelper = new DatabaseHelper(this);

        Log.d("START", "COMENZO TIENPO A CORRER: ");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (savedInstanceState != null)
        	return;
        new Thread(new Runnable() {
			@Override
			public void run() {
		        playSound(R.raw.ruta_inicio);
			}
		}).start();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap2) {





        mapboxMap = mapboxMap2;
        mapboxMap.setCameraPosition(new CameraPosition.Builder()
            .zoom(19)
            .build()
        );
//        mapboxMap.addM
//                this.map
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                //INI
                // Create symbol manager object.
                symbolManager = new SymbolManager(mapView, mapboxMap, style);

                // Set non-data-driven properties.
                symbolManager.setIconAllowOverlap(true);
                symbolManager.setTextAllowOverlap(true);
//
//                // Create a symbol at the specified location.
//                SymbolOptions symbolOptions = new SymbolOptions()
//                .withLatLng(new LatLng(-16.433854, -71.538263))//-16.434114, -71.538210   -16.433854, -71.538263
////                .withLatLng()
//                .withIconImage(MAKI_ICON_HARBOR)
//                .withIconSize(1.3f);
//
//// Use the manager to draw the symbol.
//                symbol = symbolManager.create(symbolOptions);
                //END

//                symbolManager.addClickListener(new OnSymbolClickListener() {
//                    @Override
//                    public boolean onAnnotationClick(Symbol symbol) {
//                        Toast.makeText(CopilotoActivity.this,
//                                getString(R.string.clicked_symbol_toast), Toast.LENGTH_SHORT).show();
//                        symbol.setIconImage(MAKI_ICON_CAFE);
//                        symbolManager.update(symbol);
//                        return true;
//                    }
//                });
//
//                symbolManager.addLongClickListener((new OnSymbolLongClickListener() {
//                    @Override
//                    public boolean onAnnotationLongClick(Symbol symbol) {
//                        Toast.makeText(CopilotoActivity.this,
//                                getString(R.string.long_clicked_symbol_toast), Toast.LENGTH_SHORT).show();
//                        symbol.setIconImage(MAKI_ICON_AIRPORT);
//                        symbolManager.update(symbol);
//                        return true;
//                    }
//                }));
//
//                symbolManager.addDragListener(new OnSymbolDragListener() {
//                    @Override
//                    public void onAnnotationDragStarted(Symbol annotation) {
//
//                    }
//
//                    @Override
//                    public void onAnnotationDrag(Symbol annotation) {
//
//                    }
//
//                    @Override
//                    public void onAnnotationDragFinished(Symbol annotation) {
//
//                    }
//                });
//                Toast.makeText(CopilotoActivity.this,
//                        getString(R.string.symbol_listener_instruction_toast), Toast.LENGTH_SHORT).show();

                //OFFLINE
                // Set up the OfflineManager
                offlineManager = OfflineManager.getInstance(CopilotoActivity.this);

                offlineManager.setOfflineMapboxTileCountLimit(8000);

// Create a bounding box for the offline region
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
                        CopilotoActivity.this.getResources().getDisplayMetrics().density);

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


//                IconFactory iconFactory2 = IconFactory.getInstance(CopilotoActivity.this);
////        resize2(getResources().getDrawable(R.drawable.markerr) ) ;
//                final Icon iconMarker = iconFactory2.fromBitmap(resize2(getResources().getDrawable(R.drawable.auto1) ));



//                auto_marker = new MarkerOptions().position(new LatLng(-16.433035, -71.535909))
//                        .icon(iconMarker);
//                mapboxMap.addMarker(auto_marker);

//                auto_marker_f = new Marker(auto_marker);

//                mapboxMap.addMarker(auto_marker);
//                        .position(new LatLng( poiMarker.nLatPoi,poiMarker.nLonPoi))
//                        .icon(icon));

                enableLocationComponent(style);


            }
        });
    }

//    private Drawable resizeDraw(Drawable image) {
//        ImageView appIcon1ImageView = (ImageView)findViewById(R.drawable);
//        appIcon1ImageView.setImageDrawable(getDrawable(R.drawable.app1));
//        appIcon1ImageView.setTag(R.drawable.app1);
//
////        Bitmap b = ((BitmapDrawable)image).getBitmap();
////        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
////        return new BitmapDrawable(getResources(), bitmapResized);
//
//
//    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle){
        Log.d("AQUI", "enableLocationComponent: ");
        if(PermissionsManager.areLocationPermissionsGranted(this)){
///
//            locationComponent = mapboxMap.getLocationComponent();
//            locationComponent.activateLocationComponent(this,loadedMapStyle);
//            locationComponent.setLocationComponentEnabled(true);


///
            LocationComponentOptions customLocationComponentOptions =
                    LocationComponentOptions.builder(this)
//                            .
//                            .elevation(5)
                            .accuracyAlpha(.6f)//si
                            .accuracyColor(Color.RED)//si

//                            .maxZoomIconScale(14)
//                            .minZoomIconScale(9)
//                            .foregroundDrawable(R.drawable.auto1)
//                            .foregroundDrawable(R.drawable.auto1_100x100 )
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
            locationComponent.setLocationComponentEnabled(true);//muestra el punto

            // Set the component's camera mode
//            locationComponent.setCameraMode(CameraMode.TRACKING);//no

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);//este
//            locationComponent.setRenderMode(RenderMode.COMPASS);
//            locationComponent.addOnCameraTrackingChangedListener(this);
//            locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);

            locationComponent.zoomWhileTracking(16);
//            locationComponent.zoomWhileTracking(14);

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


//        locationEngineSecond = new LostLocationEngine()
//        Location lastLocation = locationEngine.getLastLocation(callback);
//        Log.d("posActual", );

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

//    @Override
//    public void onLocationChanged(Location location) {
//        if (location != null) {
//            originLayout = location;
//            setCamerpostion(location);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());
            }
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setCamerpostion(Location camerpostion) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(camerpostion.getLatitude(), camerpostion.getLongitude()), 13.0));
    }

    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<CopilotoActivity> activityWeakReference;

        MainActivityLocationCallback(CopilotoActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            CopilotoActivity activity = activityWeakReference.get();

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
            CopilotoActivity activity = activityWeakReference.get();
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
                                        CopilotoActivity.this,
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
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }







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
        Toast.makeText(CopilotoActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void RotationCamera(float inclinacion, float longitud, float latitud){
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(latitud, longitud))
//                .zoom(15)
                .bearing(inclinacion)
//                .tilt(inclinacion)
                .build();

        mapboxMap.easeCamera(CameraUpdateFactory.newCameraPosition(position),500);
//        mapboxMap.setCameraPosition;
//        mapboxMap.moveCamera();
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

    public void AddMarkerAuto(Double latitudd, Double longitudd){
        Log.d("ACTUALIZADO_MARKER", String.valueOf(latitudd));
        Log.d("ACTUALIZADO_MARKER", String.valueOf(longitudd));
//        auto_marker.position(new LatLng(latitudd,longitudd) );
//        mapboxMap.updateMarker();
//        mapboxMap.addMarker(auto_marker);
    }

    public void CurrentPosition(List<Poi> pois, Float inclinacion){

        IconFactory iconFactory = IconFactory.getInstance(CopilotoActivity.this);
        resize2(getResources().getDrawable(R.drawable.markerr) ) ;
        Icon icon = iconFactory.fromBitmap(resize2(getResources().getDrawable(R.drawable.markerr) ));

        SymbolOptions symbolOptions;
        for (Poi poiMarker : pois) {
            Log.d("LONG YO", String.valueOf("INGRESANDO"));
            Log.d("POS NOMB", poiMarker.cNomPoi);
            Log.d("POS lat", "val " + String.valueOf(poiMarker.nLatPoi));
            Log.d("POS lon", String.valueOf(poiMarker.nLonPoi) + "pos ");


//
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng( poiMarker.nLatPoi,poiMarker.nLonPoi))
                    .icon(icon));

        }


//        IconFactory iconFactory = IconFactory.getInstance(CopilotoActivity.this);
////        Icon icon = iconFactory.fromResource(R.drawable.markerr);
//        resize2(getResources().getDrawable(R.drawable.markerr) ) ;
//        Icon icon = iconFactory.fromBitmap(resize2(getResources().getDrawable(R.drawable.markerr) ));
////
//        mapboxMap.addMarker(new MarkerOptions()
//                .position(new LatLng( -16.434328,-71.538428))
//                .icon(icon));
////                .icon(icon));


//m.setIcon(resize2(getResources().getDrawable(R.drawable.markerr)));


            Log.d("ACTUAL", "CurrentPosition: OBJ");
        Log.d("SIZEEE", String.valueOf(pois.size()));
//            Log.d("ACTUAL", "CurrentPosition Lat: "+location.getLatitude());
//            Log.d("ACTUAL", "CurrentPosition Log: "+location.getLongitude());
        //ADD IN MAP BOX
//        SymbolOptions symbolOptions;
//        for (Poi poiMarker : pois) {
//            Log.d("LONG YO", String.valueOf("INGRESANDO"));
//            Log.d("POS NOMB", poiMarker.cNomPoi);
//            Log.d("POS lat", "val " + String.valueOf(poiMarker.nLatPoi));
//            Log.d("POS lon", String.valueOf(poiMarker.nLonPoi) + "pos ");
//
////            Log.d("POS lat", poiMarker.nLatPoi);
//
//
////            Log.d("POS lat", "CurrentPosition: ");
//            symbolOptions = new SymbolOptions()
//                    .withLatLng(new LatLng(poiMarker.nLatPoi, poiMarker.nLonPoi))//-16.434328, -71.538428
//                    .withIconImage(MAKI_ICON_AIRPORT)
////                    .withI
//                    .withIconSize(1.3f);
//
//// Use the manager to draw the symbol.
//            symbol = symbolManager.create(symbolOptions); //-16.434328, -71.538428
//
//            Log.d("PLSS", "  END");
////            GeoPoint startPointM;
////            startPointM = new GeoPoint(poiMarker.nLatPoi, poiMarker.nLonPoi);
//
//        }
//END IUN MAP

//         Create a symbol at the specified location.
//        SymbolOptions symbolOptions2 = new SymbolOptions()
//                .withLatLng(new LatLng(-16.434328, -71.538428))//-16.434328, -71.538428
//                .withIconImage(MAKI_ICON_AIRPORT)
//                .withIconSize(1.3f);
//
//// Use the manager to draw the symbol.
//        symbol = symbolManager.create(symbolOptions2); //-16.434328, -71.538428
//
//
//         symbolOptions = new SymbolOptions()
//                .withLatLng(new LatLng(-16.42595, -71.524263))//-16.434328, -71.538428
//                .withIconImage(MAKI_ICON_AIRPORT)
//                .withIconSize(1.3f);
//
//// Use the manager to draw the symbol.
//        symbol = symbolManager.create(symbolOptions); //-16.434328, -71.538428

        Log.d("ACTUAL", "ENDD: OBJ");
//        if (updateZoom % 3 == 0){
//            Log.d("ACTUAL", "CurrentPosition: OBJ");
//            Log.d("ACTUAL", "CurrentPosition Lat: "+location.getLatitude());
//            Log.d("ACTUAL", "CurrentPosition Log: "+location.getLongitude());
//
//            CameraPosition position = new CameraPosition.Builder()
//                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
//                    .zoom(11)
////                    .tilt(20)
//                    .build();
//
//        }else{
//            Log.d("ACTUAL", "CurrentPosition: OBJ");
//            Log.d("ACTUAL", "CurrentPosition Lat: "+location.getLatitude());
//            Log.d("ACTUAL", "CurrentPosition Log: "+location.getLongitude());
//
//            CameraPosition position = new CameraPosition.Builder()
//                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
////                    .zoom(10)
////                    .tilt(20)
//                    .build();
//        }





    }


    //END MAP BOX

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //super.onBackPressed();
                Log.d("back1", "ir a generar reporte: ");// generar report

                showAlert();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDateIni(){

        Log.d("FUNCIONO", COD_RUTA_4);
    }

    private void showAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Mensaje");
        alertDialog.setMessage("¿Seguro que desea desactivar el copiloto?, se generará un reporte");
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                COD_RUTA_4 = "100";


                Log.d("TiempooInicial2", String.valueOf(currentTime2));

                currentTime2F= Calendar.getInstance().getTime();
                Log.d("TiempooFinal", String.valueOf(currentTime2F));
                long diff = currentTime2.getTime() - currentTime2F.getTime();
                long seconds = diff / 1000;
                Log.d("TIMESeconds", String.valueOf(seconds));

                long diff2 = currentTime2F.getTime() - currentTime2.getTime();
                long seconds2 = diff2 / 1000;
                long minutesf = seconds2 / 60;
                int minutes = (int)(seconds2 /60 );
                Log.d("TIMESeconds", String.valueOf(minutesf));

                Log.d("IDD?", String.valueOf(Configuration.IDPLATES_HAS_PATHS));
                mDBHelper.createPlatePathsTime(Configuration.IDPLATES_HAS_PATHS,minutes);

            	new Thread(new Runnable() {
					@Override
					public void run() {
		            	playSound(R.raw.ruta_fin);
					}
				}).start();
                Intent reportIntent = new Intent(getApplicationContext(), ReportsActivity.class);
                startActivity(reportIntent);
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d("back", "onBackPressed: ");
        showAlert();
        //super.onBackPressed();
    }

    private void playSound(int resId) {
    	if (mp != null && mp.isPlaying()) {
    		mp.stop();
    		mp.release();
    	}
    	mp = MediaPlayer.create(this, resId);
    	mp.start();
    	while (mp.isPlaying()) {}
    	mp.release();
    	mp = null;
    }
}
