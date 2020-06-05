package com.academiamoviles.tracklogcopilototck.ui.fragment;

//package example.javatpoint.com.toast;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Toast;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

//import com.mapbox.mapboxsdk.Mapbox;
//import com.mapbox.mapboxsdk.annotations.Marker;
//import com.mapbox.mapboxsdk.annotations.MarkerOptions;
//import com.mapbox.mapboxsdk.camera.CameraPosition;
//import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
//import com.mapbox.mapboxsdk.geometry.LatLng;
//import com.mapbox.mapboxsdk.geometry.LatLngBounds;
//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;




import com.academiamoviles.tracklogcopilototck.MyApp;
import com.academiamoviles.tracklogcopilototck.R;
import com.academiamoviles.tracklogcopilototck.database.DatabaseHelper;
import com.academiamoviles.tracklogcopilototck.model.Incidences;
import com.academiamoviles.tracklogcopilototck.model.Paths;
import com.academiamoviles.tracklogcopilototck.model.PlatesHasPaths;
import com.academiamoviles.tracklogcopilototck.polygon.Point;
import com.academiamoviles.tracklogcopilototck.polygon.Polygon;
import com.academiamoviles.tracklogcopilototck.ui.Configuration;
import com.academiamoviles.tracklogcopilototck.ui.UsoApp;
import com.academiamoviles.tracklogcopilototck.ui.activity.CopilotoActivity;
import com.academiamoviles.tracklogcopilototck.ws.response.Cerco;
import com.academiamoviles.tracklogcopilototck.ws.response.Cerco_Response;
import com.academiamoviles.tracklogcopilototck.ws.response.Poi;
import com.academiamoviles.tracklogcopilototck.ws.response.Poi_Response;
import com.academiamoviles.tracklogcopilototck.ws.response.Ruta;
import com.academiamoviles.tracklogcopilototck.ws.response.Ruta_Response;
//import com.mapbox.mapboxsdk.offline.OfflineManager;
//import com.mapbox.mapboxsdk.offline.OfflineRegion;
//import com.mapbox.mapboxsdk.offline.OfflineRegionError;
//import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
//import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
//import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//
//import com.google.android.gms.maps.GoogleMap.CancelableCallback;
//import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
//import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
//import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
//import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.cachemanager.CacheManager;
import org.osmdroid.tileprovider.modules.SqliteArchiveTileWriter;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Android on 07/02/2017.
 */
//OnCameraIdleListener, OnCameraMoveStartedListener, OnCameraMoveListener, OnCameraMoveCanceledListener
public class CopilotoFragment extends BaseFragment implements LocationListener, View.OnClickListener {

    /***   OSMDROID ****/

    private static int REQUEST_WRITE_STORAGE = 1;

    Context context;

    MapView map;
    IMapController mapController;
    GeoPoint startPoint;
    Marker m;


    public RotationGestureOverlay mRotationGestureOverlay;

    Timer timer;
    TimerTask timerTask;
    double latitude;
    double longitude;
    int cnt = 0;

    CacheManager mgr;


//    private MapView mapView;
//    private OfflineManager offlineManager;
//    private MapboxMap currentMapbox;

    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
//    private MapView mMapView;
//    private GoogleMap mapCurrent;
//    private Marker auto;

    float auxPosLat=(float)-12.090503367;
    float auxPosLon=(float)-77.020577392;
//-12.091400, -77.027060   -12.090503367,-77.020577392
    float auxPosLatPos=(float)-12.090503367;
    float auxPosLonPos=(float)-77.020577392;
    boolean cambio = false;

    float angle = 0;
    float angleTemp = 0;


    private boolean isCanceled = false;

    //private PolylineOptions currPolylineOptions;

//    public static final CameraPosition SYDNEY =
//            new CameraPosition.Builder().target(new LatLng(-12.090503367, -77.020577392))
//                    .zoom(15.5f)
//                    .bearing(90) //horizontal
//                    .tilt(45)   //vertical
//                    .build();
//
//    public CameraPosition POS_Actual;
//    public CameraPosition POS_Camera;
    //-33.87365, 151.20689

    boolean isRed = false;
    boolean isAmber = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void upDateMap(){
        Log.d("ACTUALIZACION", "Region downloaded ssssuccessfully.");
    }


    private enum AnuncioType {cerco, poi, velocidad, invalido}

    private class Anuncio {
        AnuncioType tipo;
        String mensaje;

        public Anuncio(AnuncioType tipo, String mensaje) {
            this.tipo = tipo;
            this.mensaje = mensaje;
        }

        public Anuncio() {
        }
    }

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 1 metro

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1 * 1000; // 1 segundo

    //Mensajes
    private static final String MENSAJE_FUERA_CERCO = "No se encuentra en ningún cerco, regrese a la ruta";
    //private static final String MENSAJE_EXCESO_VELOCIDAD = "Exceso de velocidad en el cerco: %s, baje su velocidad a: %d kilómetros por hora";
    private static final String MENSAJE_EXCESO_VELOCIDAD = "Está excediendo la velocidad, su límite es: %d kilómetros por hora";
    private static final String MENSAJE_INGRESO_CERCO = "Ingresó en el cerco: %s, la velocidad máxima es: %d kilómetros por hora.";
    private static final String MENSAJE_CERCA_POI = "Esta cerca del punto: %s";
    private static final String MENSAJE_X_DICTANCIA_POI = "Está a %s metros del punto %s ";
    private static final String MENSAJE_CERCO_NO_ENCONTRADO = "Cerco no encontrado.";
    private static final String MENSAJE_CERCA_VELOCIDAD_LIMITE = "Está cerca de la velocidad límite de: %d kilómetros por hora";

    private static final long MIN_DISTANCIA_POI = 200;
    private static final int RANGO_CERCA_VELOCIDAD_LIMITE = 2;

    //Location Manager
    boolean canGetLocation;
    boolean isNetworkEnabled;
    boolean isGPSEnabled;

    LocationManager locationManager;
    Location locationStart;

    Location auxLocation;

    //View
    LinearLayout viewFlash;
    TextView txtVelocidadGPS, txtVelocidadCerco, txtCerco, txtMensaje, txtTitulo, txtUnidadGPS;

    LinearLayout viewFlash2;
    TextView txtVelocidadGPS2, txtVelocidadCerco2, txtCerco2, txtMensaje2, txtTitulo2, txtUnidadGPS2;

    Button btnShowMap;
    LinearLayout viewLinear1;
    LinearLayout viewLinear2;
    Boolean showMap = true;

    TextToSpeech tts;
    int velocGPS = 0, velocCerco = 0;
    String cercoText = "";

    //Copiloto
    private static final int minPrecision = 50;

    Date dateStart;
    int codRuta;
    Ruta ruta;
    List<Cerco> cercos;
    List<Cerco> cercos_Aux;
    List<Point> poisMap;
    List<Poi> pois;
    List<Polygon> regions;

    Cerco currentCerco;

    Set<Integer> indexMensajesCercoHablados = new ArraySet<>();
    Set<Poi> indexMensajesPoiHablados = new ArraySet<>();

    List<Anuncio> anuncios;
    Anuncio anuncioInvalido;

    boolean isProcess = false;
    int iOutsideCerco = 0;

    boolean isFlashing = false;
    Date dateDentroCerco = new Date();
    UsoApp objUsoApp;
    long tiempoMinimousoApp = 10 * 60;
    Calendar dateUsoApp = Calendar.getInstance();

    boolean firstFueraCerco = true;


    Date dateDentroPoi = new Date();
    long tiempoMinimoPoi = 5;
    int countAnuncioPoi = 1;
    static int cOrange;
    static int cRed;
    static int cRed2;
    static int cAmber;

    long countLocation =0;
    long countLocationBefore =0;
    DatabaseHelper mDBHelper;
    long idGroup=0;
    PlatesHasPaths platesHasPathsCreated;
    int idPlatePaths;

    ValueAnimator colorAnimation;
    int iVelocidadCero = 0;

    MyApp myApp;
	List<Poi> poisIntermedio = new ArrayList<>(Arrays.asList(
			new Poi(12, "IMATA CARGADO", -15.83026, -71.0834),
            new Poi(12, "IMATA VACIO", -15.836506, -71.089061),
			new Poi(12, "SAN JOSE", -16.578987, -71.838162),
			new Poi(596, "IMATA CARGADO", -15.83371, -71.086386),
            new Poi(596, "IMATA VACIO", -15.836506, -71.089061),
			new Poi(596, "SAN CAMILO", -16.686681, -71.916318),
			new Poi(905, "CONDOROMA", -15.296815, -71.137322),
            new Poi(905, "CONGUYA", -14.019947, -71.975583),
			new Poi(869, "SAN CAMILO", -16.686681, -71.916318),
			new Poi(724, "IMATA CARGADO", -15.836506, -71.089061),
            new Poi(724, "IMATA VACIO", -15.83026, -71.0834)));
    int countPoiIntermedio = 1;
    MediaPlayer mp;


    List<Poi> poisAux = new ArrayList<>(Arrays.asList(
            new Poi(12, "POI Curva paucarpata 1", -16.420091, -71.495574),
            new Poi(12, "POI Normal paucarpata 2", -16.421484, -71.496358),
            new Poi(12, "POI modulo paucarpata", -16.422814, -71.512971),
            new Poi(596, "POI rico pollo", -16.424074, -71.515808),
            new Poi(596, "POI King Broaster", -16.420899, -71.508245),
            new Poi(596, "POI Curva Salaverry", -16.433814, -71.537962)



            ));

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /******** OSMDROID ********/

//        ActivityCompat.requestPermissions(requireActivity(),
//                new String[]{WRITE_EXTERNAL_STORAGE},
//                REQUEST_WRITE_STORAGE);
//
//        ActivityCompat.requestPermissions(requireActivity(),
//                new String[]{READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION}, 1);
//
//        ////context = this;
//        //        context = getApplicationContext();
//        context = getContext();
//        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
//
//
//        File osmdroid = new File(Environment.getExternalStorageDirectory().getPath(), "Download/osmdroid");
//        File tiles = new File(osmdroid.getPath(), "tiles");
//
//        org.osmdroid.config.Configuration.getInstance().setOsmdroidBasePath(osmdroid);
//        org.osmdroid.config.Configuration.getInstance().setOsmdroidTileCache(tiles);
//
//        if (osmdroid.isDirectory()) {
//            Log.d("File", "Osmdroid isDirectory");
//        }


//        Mapbox.getInstance((getContext()), "pk.eyJ1IjoiY2hyaXN0aWFuNTgiLCJhIjoiY2s5eGVkNTk5MDAwNDNscGllNXVyY2FmaSJ9.LF3Yh_TfhQMc3O-DYP1d-Q");

        codRuta = getActivity().getIntent().getIntExtra(CopilotoActivity.COD_RUTA, 0);


        mDBHelper = new DatabaseHelper(getContext());

        Paths paths = mDBHelper.getPathsByCodRuta(codRuta);

        PlatesHasPaths platesHasPaths = new PlatesHasPaths(Configuration.userPlate.getPlates().getIdPlates(),paths.getIdPaths(),new Date());
        idPlatePaths = mDBHelper.createPlatePaths(platesHasPaths);
        Configuration.IDPLATES_HAS_PATHS=idPlatePaths;
        Configuration.COD_RUTA = codRuta;



        getDatos();
        createPolygons();
        initTts();

        cOrange = ContextCompat.getColor(getActivity(), R.color.colorGreen);
        cRed = ContextCompat.getColor(getActivity(), R.color.colorRed);
        cRed2 = ContextCompat.getColor(getActivity(), R.color.colorRed2);
        cAmber = ContextCompat.getColor(getActivity(), R.color.colorAmber);

        anuncios = new ArrayList<>();
        anuncioInvalido = new Anuncio(AnuncioType.invalido, MENSAJE_FUERA_CERCO);

        objUsoApp = UsoApp.readIS(getActivity());
        if (objUsoApp == null) {
            objUsoApp = new UsoApp();
        }


        myApp = (MyApp) getActivity().getApplication();


    }

    /***
     * Obtiene los datos almacenados en la memoria
     */
    private void getDatos() {
        Log.d("AVER", "GET DATOS");

        ruta = new Ruta();
        Ruta_Response ruta_response = Ruta_Response.readIS(getContext());
        for (Ruta item : ruta_response.listaRutas) {
            if (item.nCodRuta == codRuta) {
                ruta = item;
            }
        }

        cercos = new ArrayList<>();
        cercos_Aux = new ArrayList<>();
        Cerco_Response cerco_response = Cerco_Response.readIS(getContext());
        Log.d("ANTES_CERCO", String.valueOf(cerco_response.listaCercos.size()));
        for (Cerco item : cerco_response.listaCercos) {
            Log.d("AVERR", String.valueOf(item.nCodRut) + " == " + codRuta);
            cercos_Aux.add(item);
            if (item.nCodRut == codRuta) {
                cercos.add(item);
            }
        }
        Log.d("ANTES SALI CERCO", String.valueOf(cerco_response.listaCercos.size()));
        pois = new ArrayList<>();
        Poi_Response poi_response = Poi_Response.readIS(getContext());
        //pois = poi_response.listaPois;
        Log.d("ANTES", String.valueOf(poi_response.listaPois.size()));
        for (Poi item : poi_response.listaPois) {
            Log.d("AVERR", String.valueOf(item.nCodRutas));
            if (item.nCodRutas!=null && item.nCodRutas.indexOf(codRuta) != -1) {
                pois.add(item);
            }
        }
        Log.d("ANTES SALI", String.valueOf(poi_response.listaPois.size()));

    }

    /**
     * Método que crea el polígono
     */
    private void createPolygons() {
        regions = new ArrayList<>();
        Log.d("CERCO SIZE", String.valueOf(cercos.size()));
        for (Cerco item : cercos) {
            List<Point> coords = new ArrayList<>();
            Log.d("Cerco IN", String.valueOf(item.gPolCer.points.size()));
            for (Cerco.Point point : item.gPolCer.points) {
                Log.d("PuntosCerco", point.y + " , " + point.x);
                Point point2D = new Point(point.x, point.y);
                coords.add(point2D);

            }
            Polygon polygon = new Polygon.Builder().addVertex(coords).build();
            regions.add(polygon);
        }
    }
    /**
     * Obtener puntos para descarga
     */
    private Boolean distanciaPuntos(double x1, double y1, double x2, double y2){
        double res = Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
        if(res > 0.006) return true;
        return true; //test
    }

    private void obtenerPuntos(){
        Log.d("CERCO SIZE", String.valueOf(cercos_Aux.size()));
        poisMap = new ArrayList<>();
        Boolean aux = false;
        double x2 = 0;
        double y2 = 0;
        for (Cerco item : cercos_Aux) {
            List<Point> coords = new ArrayList<>();
            Log.d("Cerco IN", String.valueOf(item.gPolCer.points.size()));
            for (Cerco.Point point : item.gPolCer.points) {
                //obtenemos todo los puntos de todo los cercos
                Point point2D = new Point(point.y, point.x);
                poisMap.add(point2D);
//
//                if(aux == false){
//                    aux = true;
//                    y2 = point.y;
//                    x2 = point.x;
//                    Point point2D = new Point(point.y, point.x);
//                    coords.add(point2D);
//                    poisMap.add(point2D);
//                }
//                for(Point pp : coords){
//                    if(distanciaPuntos(point.x, point.y, pp.x,pp.y)){
//                        Point point2D = new Point(point.y, point.x);
//                        coords.add(point2D);
//                        poisMap.add(point2D);
//                    }
//                }


//                Log.d("PuntosCerco", point.y + " , " + point.x);
//                Point point2D = new Point(point.x, point.y);
//                coords.add(point2D);

            }

//            Polygon polygon = new Polygon.Builder().addVertex(coords).build();
//            regions.add(polygon);
        }
    }
    /**
     * Descargar Mapa
     */



    /**
     * Inicializa el TextToSpeech
     */
    private void initTts() {
        if (tts != null)
            return;

        tts = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale("es", "PE"));
                }
            }
        });
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    private Drawable resize2(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 75, 75, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.copiloto_fragment, container, false);


        /****** OSMdroud*******/

//        ActivityCompat.requestPermissions(getActivity(),
//                new String[]{WRITE_EXTERNAL_STORAGE},
//                REQUEST_WRITE_STORAGE);
//
//        ActivityCompat.requestPermissions(getActivity(),
//                new String[]{READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION}, 1);

        //context = this;
        context = getContext();
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));


        File osmdroid = new File(Environment.getExternalStorageDirectory().getPath(), "Download/osmdroid");
        File tiles = new File(osmdroid.getPath(), "tiles");

        org.osmdroid.config.Configuration.getInstance().setOsmdroidBasePath(osmdroid);
        org.osmdroid.config.Configuration.getInstance().setOsmdroidTileCache(tiles);

        //Log
        Log.d("File", "osmdroid getAbsolutePath : " + osmdroid.getAbsolutePath());
        Log.d("File", "osmdroid getPath : " + osmdroid.getPath());

        if (osmdroid.isDirectory()) {
            Log.d("File", "Osmdroid isDirectory");
        }

        /****/

        map = view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setTilesScaledToDpi(true);//font scale

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        //map.setUseDataConnection(false);

        //sqlwriter
        String output = osmdroid.getAbsolutePath() + File.separator + "test" + ".sqlite";
        SqliteArchiveTileWriter writer = null;
        try {
            writer = new SqliteArchiveTileWriter(output);
            Log.d("output", "output : " + output);

        } catch (Exception e) {
            Log.d("FALLO", "FALLO SALIDA : " + output);
            e.printStackTrace();
        }

        mgr = new CacheManager(map, writer);

        map.addMapListener(new DelayedMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                //download tile cache
//                BoundingBox bb = map.getBoundingBox();
//                int currentZoomLevel = (int) map.getZoomLevelDouble();
//                if (currentZoomLevel > 19) {
//                    currentZoomLevel = 19;
//                }
//                mgr.downloadAreaAsyncNoUI(context, bb, currentZoomLevel, currentZoomLevel, new CacheManager.CacheManagerCallback() {
//                    @Override
//                    public void onTaskComplete() {
//                        Toast.makeText(context, "Download complete!!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void updateProgress(int progress, int currentZoomLevel, int zoomMin, int zoomMax) {
//
//                    }
//
//                    @Override
//                    public void downloadStarted() {
//
//                    }
//
//                    @Override
//                    public void setPossibleTilesInArea(int total) {
//
//                    }
//
//                    @Override
//                    public void onTaskFailed(int errors) {
//
//                    }
//                });

                Log.d("zoom", "onScroll zoom : " + map.getZoomLevelDouble());

                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                map.invalidate();
                Log.d("zoom", "onZoom zoom : " + map.getZoomLevelDouble());
                return false;
            }
        }, 100));

        //IMapController mapController = map.getController();
        mapController = map.getController();
        mapController.setZoom(16.0);
        //GeoPoint startPoint = new GeoPoint(-12.087704, -77.000295);
        startPoint = new GeoPoint(-16.412844, -71.525984);
        mapController.setCenter(startPoint);

        //mapController.zoomTo(Integer.parseInt(15));
        mapController.zoomTo(16);
        map.getController().animateTo(startPoint);


        mRotationGestureOverlay = new RotationGestureOverlay(context, map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(this.mRotationGestureOverlay);




        m = new Marker(map);
        m.setTextLabelBackgroundColor(R.color.colorGreen);
        m.setTextLabelFontSize(14);
        m.setTextLabelForegroundColor(R.color.colorRed);
        //m.setTitle("hello world");
//must set the icon to null last
        //m.setIcon(null);
//        int aux =  R.drawable.vehiculo;
//        Drawable img = resize(getResources().getDrawable(R.drawable.vehiculo));
        m.setIcon(resize(getResources().getDrawable(R.drawable.auto11)));
        m.setPosition(startPoint);
        map.getOverlays().add(m);


//        GeoPoint startPointMa;
//        startPointMa = new GeoPoint(-16.433057, -71.538417);
//        Marker ma = new Marker(map);
//        ma.setTitle("TEST");
//        ma.setIcon(resize2(getResources().getDrawable(R.drawable.auto1)));
//        ma.showInfoWindow();
//        ma.setPosition(startPointMa);
//        map.getOverlays().add(ma);




        //offline tile to cache
        //download tile cache
        mgr = new CacheManager(map);
        BoundingBox bb = new BoundingBox(-12.070593, -76.978261, -12.103398, -77.029700);
        mgr.downloadAreaAsync(context, bb, 14, 14, new CacheManager.CacheManagerCallback() {
            @Override
            public void onTaskComplete() {
                Log.d("data", "cache descargado " + map.getZoomLevelDouble());
                Toast.makeText(context, "Download complete!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateProgress(int progress, int currentZoomLevel, int zoomMin, int zoomMax) {

            }

            @Override
            public void downloadStarted() {

            }

            @Override
            public void setPossibleTilesInArea(int total) {

            }

            @Override
            public void onTaskFailed(int errors) {
                Toast.makeText(context, "Fallo complete!!", Toast.LENGTH_SHORT).show();
                Log.d("cache", "Fallo descarga cache: " + map.getZoomLevelDouble());

            }
        });


/************************/
        viewFlash = (LinearLayout) view.findViewById(R.id.viewFlash);
        txtMensaje = (TextView) view.findViewById(R.id.txtMensaje);
        txtVelocidadGPS = (TextView) view.findViewById(R.id.txtVelocidadGPS);
        txtVelocidadCerco = (TextView) view.findViewById(R.id.txtVelocidadCerco);
        txtCerco = (TextView) view.findViewById(R.id.txtCerco);
        txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
        txtUnidadGPS = (TextView) view.findViewById(R.id.txtUnidadGPS);

        viewFlash2 = (LinearLayout) view.findViewById(R.id.viewFlash2);
        //txtMensaje2 = (TextView) view.findViewById(R.id.txtMensaje);
        txtVelocidadGPS2 = (TextView) view.findViewById(R.id.txtVelocidadGPS2);
        txtVelocidadCerco2 = (TextView) view.findViewById(R.id.txtVelocidadCerco2);
        //txtCerco2 = (TextView) view.findViewById(R.id.txtCerco);
        //txtTitulo2 = (TextView) view.findViewById(R.id.txtTitulo2);
        txtUnidadGPS2 = (TextView) view.findViewById(R.id.txtUnidadGPS2);



        viewLinear1 = (LinearLayout) view.findViewById(R.id.contacts_type);
        viewLinear2 = (LinearLayout) view.findViewById(R.id.contacts_type2);

        btnShowMap = (Button) view.findViewById(R.id.btn_update_view);

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!showMap){
                    viewLinear2.setVisibility(View.GONE);
                    viewLinear1.setVisibility(View.VISIBLE);
                    showMap=!showMap;
                }
                else{
                    viewLinear1.setVisibility(View.GONE);
                    viewLinear2.setVisibility(View.VISIBLE);
                    showMap=!showMap;
                }

            }
        });


//        mapView = (MapView) view.findViewById(R.id.mapView);

//        mapView.onCreate(savedInstanceState);




        Bundle mapViewBundle = null;

        /**
         if (cercos.size() == 0)
         txtTitulo.setText("No se encontró ningún cerco.");
         else {
         txtTitulo.setText(String.format("Tiene %d cercos para esta ruta.", cercos.size()));
         }*/

        if (savedInstanceState != null) {
            cercoText = savedInstanceState.getString("cercoText");
            velocCerco = savedInstanceState.getInt("velocCerco", 0);
            velocGPS = savedInstanceState.getInt("velocGPS", 0);

            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);


        }
        txtVelocidadCerco.setText(getVelocidad(velocCerco));
        txtVelocidadCerco2.setText(getVelocidad(velocCerco));
        txtVelocidadGPS.setText(getVelocidad(velocGPS));
        txtVelocidadGPS2.setText(getVelocidad(velocGPS));
        txtCerco.setText(cercoText);

//        mMapView = (MapView) view.findViewById((R.id.mapView));
//        mMapView.onCreate(mapViewBundle);
//        mMapView.getMapAsync((OnMapReadyCallback) this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniLocation();
    }

    //VALIDACIONES

    private String getMessageFor(int velocity, Cerco cerco) {
        if (cerco == null) {
            return null;
        }
        int maxVelocity = cerco.nVelCer;
        if (velocity > maxVelocity) {
            return String.format(MENSAJE_EXCESO_VELOCIDAD, maxVelocity);
        }
        return null;
    }
    int prevMaxSpeedWarned = 0;
    private void getCautionMessageFor(int velocity, Cerco cerco) {
		isAmber = false;
        if (cerco == null) {
        	prevMaxSpeedWarned = 0;
            return;
        }
        int maxVelocity = cerco.nVelCer;
        if (velocity <= maxVelocity && velocity >= maxVelocity - RANGO_CERCA_VELOCIDAD_LIMITE) {
    		isAmber = true;
        	if (prevMaxSpeedWarned != 0 && prevMaxSpeedWarned == maxVelocity)
        		return;
        	prevMaxSpeedWarned = maxVelocity;
            String message = String.format(MENSAJE_CERCA_VELOCIDAD_LIMITE, maxVelocity);
            anuncios.add(new Anuncio(AnuncioType.velocidad, message));
            return;
        }
        prevMaxSpeedWarned = 0;
        return;
    }
    /**
     * Valida si un punto esta dentro de un polígono.
     *
     * @param point    Punto a validar
     * @param polygons Lista de polígonos
     * @return Retorna de índice del polígono que contiene el punto.
     * Retorna nulo si el punto no está contenido en ningun polígono.
     */
    private int validate(Point point, List<Polygon> polygons) {
        int success = -1;
        int i = 0;
        for (Polygon polygon : polygons) {
            if (polygon.contains(point)) {
                success = i;
                return success;
            }
            i++;

        }
        return success;
    }

    /**
     * Valida si una ubicación está cerca de un POI
     *
     * @param location Ubicación
     * @param pois     Lista de POIs
     * @return Retorna el POI cercano
     */
    private Poi validate(Location location, List<Poi> pois) {
        Log.d("MARCADOR", "ENTREEEE " + pois.size());
        for (Poi poi : pois) {
            Location locationPoi = new Location("");
            locationPoi.setLongitude(poi.nLonPoi);
            locationPoi.setLatitude(poi.nLatPoi);
            float distance = location.distanceTo(locationPoi);

            Log.d("CnombrePoi",String.valueOf(poi.cNomPoi));
//            Log.d("cPais",String.valueOf(poi.cPaisPoi));
//            Log.d("cTipo",String.valueOf(poi.cTipoPoi));
//            Log.d("cNombVia",String.valueOf(poi.cNomViaPoi));
//            Log.d("cMensaje",String.valueOf(poi.mensaje));
//            Log.d("cDisPoi",String.valueOf(poi.nDisPoi));

            if (poi.nDisPoi != null) {
            	if (distance < poi.nDisPoi) {
            		Log.d("CopilotoFragment", "[Custom] Poi detectado a " + distance);
            		return poi;
            	}
        	} else if (distance < MIN_DISTANCIA_POI) {
        		Log.d("CopilotoFragment", "[Default] Poi detectado a " + distance);
                return poi;
            }
        }
        Log.d("MARCADORR", "SALII");
        return null;
    }
    
    private Poi validateIntermedio(Location location, List<Poi> pois) {
        String codUsu = myApp.objUser.userId;
        for (Poi poi : pois) {
            Location locationPoi = new Location("");
            locationPoi.setLongitude(poi.nLonPoi);
            locationPoi.setLatitude(poi.nLatPoi);
            float distance = location.distanceTo(locationPoi);
            if (distance < 100 && poi.nCodUsu.equals(new Integer(codUsu))) {
                return poi;
            }
        }
        return null;
    }
    
    private void playSound(final int resId) {
    	new Thread(new Runnable() {
			@Override
			public void run() {
		    	if (mp != null && mp.isPlaying()) {
		    		mp.stop();
		    		mp.release();
		    	}
		    	mp = MediaPlayer.create(getActivity(), resId);
		    	mp.start();
		    	while (mp.isPlaying()) {}
		    	mp.release();
		    	mp = null;
			}
		}).start();
    }

    private int getVelocityManual(Location location) {
        float distance = locationStart.distanceTo(location);

        if (distance > minPrecision) {
            Date date = new Date();
            long interval = date.getTime() - dateStart.getTime();
            long timeSec = TimeUnit.MILLISECONDS.toSeconds(interval);
            dateStart = date;
            locationStart = location;
            return (int) ((distance / timeSec) * 3.6);
            //return 50;
        }

        return 0;
    }


    private void animationVelocity() {

        /*
        boolean hasVelocity = false;
        for (Anuncio anuncio : anuncios) {
            if (anuncio.tipo == AnuncioType.velocidad) {
                hasVelocity = true;
            }
        }
        */

        if (isRed) {
            viewFlash.setBackgroundColor(cRed);
            viewFlash2.setBackgroundColor(cRed);
            txtVelocidadGPS.setTextColor(cRed);
            txtVelocidadGPS2.setTextColor(cRed);
            txtUnidadGPS.setTextColor(cRed);
            txtUnidadGPS2.setTextColor(cRed);
        } else if (isAmber) {
            viewFlash.setBackgroundColor(cAmber);
            viewFlash2.setBackgroundColor(cAmber);
            txtVelocidadGPS.setTextColor(cAmber);
            txtVelocidadGPS2.setTextColor(cAmber);
            txtUnidadGPS.setTextColor(cAmber);
            txtUnidadGPS2.setTextColor(cAmber);
        } else {
            viewFlash.setBackgroundColor(cOrange);
            viewFlash2.setBackgroundColor(cOrange);
            txtVelocidadGPS.setTextColor(cOrange);
            txtVelocidadGPS2.setTextColor(cOrange);
            txtUnidadGPS.setTextColor(cOrange);
            txtUnidadGPS2.setTextColor(cOrange);
        }

        /*
        int velocity = 0;
        for (Anuncio anuncio : anuncios) {
            if (anuncio.tipo == AnuncioType.velocidad) {
                velocity++;
            }
        }
        int colorActual = 0;
        Drawable background = viewFlash.getBackground();
        if (background instanceof ColorDrawable)
            colorActual = ((ColorDrawable) background).getColor();

        if (velocity > 0) {
            if (!isFlashing && colorActual == cOrange) {
                isFlashing = true;

                //changeColor(colorActual, cRed);

                colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), cRed2, cRed);
                colorAnimation.setDuration(250); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        viewFlash.setBackgroundColor((int) animator.getAnimatedValue());
                        txtVelocidadGPS.setTextColor(cRed);
                        txtUnidadGPS.setTextColor(cRed);

                    }

                });
                colorAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        changeToFalseFlashing();
                        animation.setStartDelay(5000);
                        animation.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        changeToFalseFlashing();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                colorAnimation.start();
            }

        } else {
            if (!isFlashing && colorActual != cOrange) {
                changeColor(colorActual, cOrange);
            }
        }
        */
    }

    /*
    private void changeColor(int fromColor, int toColor) {
        if (colorAnimation != null) {
            colorAnimation.cancel();
        }
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                viewFlash.setBackgroundColor((int) animator.getAnimatedValue());
                txtVelocidadGPS.setTextColor((int) animator.getAnimatedValue());
                txtUnidadGPS.setTextColor((int) animator.getAnimatedValue());

            }

        });
        colorAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                changeToFalseFlashing();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        colorAnimation.start();
    }
    */

    /*
    private void newColor(int toColor) {
        int colorActual = 0;
        Drawable background = viewFlash.getBackground();
        if (background instanceof ColorDrawable)
            colorActual = ((ColorDrawable) background).getColor();

        changeColor(colorActual, toColor);

    }
    */

    private void changeToFalseFlashing() {
        isFlashing = false;
    }

    private void reproducir(final Anuncio anuncio) {
        if (anuncio == null) {
            return;
        }
        if (!tts.isSpeaking()) {
            tts.speak(anuncio.mensaje, TextToSpeech.QUEUE_FLUSH, null, "anuncio");
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    if (anuncios.size() > 0) {
                        anuncios.remove(0);
                    }
                }

                @Override
                public void onDone(String utteranceId) {
                    if (anuncios.size() > 0) {
                        reproducir(anuncios.get(0));
                    }
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }

    }
    //-----------------------

    //MÉTODOS IMPLEMENTADOS
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("cercoText", cercoText);
        outState.putInt("velocGPS", velocGPS);
        outState.putInt("velocCerco", velocCerco);
        super.onSaveInstanceState(outState);

//        mapView.onSaveInstanceState(outState);

        //MapView
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        //mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        //mMapView.onResume();
//        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        //mMapView.onStart();
//        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mMapView.onStop();
//        mapView.onStop();
    }

    @Override
    public void onPause() {
        //mMapView.onPause();
        super.onPause();
        map.onPause();
//        mapView.onPause();

//        if (offlineManager != null) {
//            offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
//                @Override
//                public void onList(OfflineRegion[] offlineRegions) {
//                    if (offlineRegions.length > 0) {
//                        // delete the last item in the offlineRegions list which will be yosemite offline map
//                        offlineRegions[(offlineRegions.length - 1)].delete(new OfflineRegion.OfflineRegionDeleteCallback() {
//                            @Override
//                            public void onDelete() {
////                                Toast.makeText(
////                                        CopilotoFragment.this,
////                                        getString(R.string),
////                                        Toast.LENGTH_LONG
////                                ).show();
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                //Timber.e("On delete error: %s", error);
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onError(String error) {
//                    //Timber.e("onListError: %s", error);
//                }
//            });
//        }

        setRetainInstance(true);

    }

    //OSMdroid
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 1:
                    GeoPoint point = (GeoPoint) msg.obj;
                    map.getController().setCenter(point);
                    Marker marker = new Marker(map);
                    marker.setPosition(point);
                    map.getOverlays().clear();
                    map.getOverlays().add(marker);
                    map.invalidate();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        //mMapView.onDestroy();
        stopGPS();
        super.onDestroy();
//        mapView.onDestroy();

        if (tts != null) {
            //tts.stop();
            tts.shutdown();
        }
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
        //mMapView.onLowMemory();
    }
    //----------------------------------


    //LOCATION MANAGER

    /**
     * Inicializa el Location Manager
     */
    private void iniLocation() {
        if (!GPSProviderEnabled(getActivity())) {
            //this.canGetLocation = false;
            txtMensaje.setVisibility(View.VISIBLE);
        } else {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                txtMensaje.setVisibility(View.GONE);
                this.canGetLocation = true;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                }
            }
        }
    }

    /**
     * Verifica el proveedor de ubicación
     *
     * @param mContext
     * @return True = activo, Falso = inactivo.
     */
    public boolean GPSProviderEnabled(Context mContext) {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            return false;
        }
        return true;
    }

    /**
     * Detiene la actualización de ubicación
     */
    public void stopGPS() {
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(this);
            }
        }
    }

    public float inclinationAngle(float x1, float y1, float x2, float y2){
        if(x2-x1 != 0){
            float m = (y2-y1)/(x2-x1);
            float angle_rad = (float) Math.atan(m);
            float angle2 = (float) (angle_rad*180/3.1415926);
            Log.d("ANGLEE", String.valueOf(angle2));
            float distancia = (float) Math.sqrt(Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
            //0.00003
            if(distancia > 0.00003){
                return angle2;
            }
//        Log.d("X1", String.valueOf(x1));
//        Log.d("Y1", String.valueOf(y1));
//        Log.d("X2", String.valueOf(x2));
//        Log.d("Y2", String.valueOf(y2));
//        Log.d("RAD", String.valueOf(angle_rad));


            return  angle;
//            return  angle2;

        }
        else return angle;

    }

//    public void addMarker(Poi poiMarker){
//        LatLng latLngI = new LatLng(poiMarker.nLatPoi, poiMarker.nLonPoi);
//        //mapCurrent.addMarker(new MarkerOptions().position(latLngI).title(poiMarker.cNomViaPoi));
//
//        String s = poiMarker.cNomPoi;
//        String typeMarker;
//        BitmapDrawable bitmapdraw; //= (BitmapDrawable)getResources().getDrawable(R.drawable.default1);
//        //CASE
//        if (poiMarker.cNomPoi.contains("Curva")) {
//            // it contains world
//            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.curvas);
//        }
//        else{
//            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.default1);
//        }
//
//        Log.d("NombreMARKER1", String.valueOf(poiMarker.cNomPoi));
//        int height = 100;
//        int width = 100;
//        //LatLng posIni = new LatLng(-12.090503367, -77.020577392);
//        //BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.curvas);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLngI);
////        mapCurrent.clear();
//        markerOptions.title(poiMarker.cNomPoi);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).anchor(0.5f,1f);
////        markerOptions
//        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vehiculo));
//        markerOptions.getPosition();
//        //mapCurrent.addMarker(markerOptions).showInfoWindow();
//
//
//    }

    public BitmapDrawable writeOnDrawable(int drawableId, String text){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap bmm  = Bitmap.createBitmap(100,100,null);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(200);

        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.STROKE);
        textStyle.setColor(Color.parseColor("#0000ff"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(100);


        Canvas canvas = new Canvas(bmm);
        //canvas.text
        canvas.drawText("GAAAAA", 0, bm.getHeight()/2, textStyle);

        return new BitmapDrawable(bm);
    }

    public void addMarkerBox(Poi poiMarker){

        GeoPoint startPointM;
        startPointM = new GeoPoint(poiMarker.nLatPoi, poiMarker.nLonPoi);

        Marker m = new Marker(map);
        //m.setTextLabelBackgroundColor(R.color.colorGreen);
        //m.setTextLabelFontSize(14);
        //m.setTextLabelForegroundColor(R.color.colorRed);
        //m.setTextLabelFontSize(80);


        m.setTitle(poiMarker.cNomPoi);


//must set the icon to null last
        //m.setIcon(null);
       // m.setText
//        m.setEnableTextLabelsWhenNoImage(true);
        //m.textlabel
        if (poiMarker.cNomPoi.contains("Curva")) {
            // it contains world
            //bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.curvas);
            //m.setIcon( writeOnDrawable(R.drawable.curvas,poiMarker.cNomPoi));
            m.setIcon(resize2(getResources().getDrawable(R.drawable.curvas)));
//            m.setIcon(null);
//            m.setTextLabelFontSize(500);

        }
        else{
            m.setIcon(resize2(getResources().getDrawable(R.drawable.default1)));
            //bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.default1);
        }
        m.showInfoWindow();
        //m.setIcon(resize(getResources().getDrawable(R.drawable.vehiculo)));
        m.setPosition(startPointM);

        map.getOverlays().add(m);


//        LatLng latLngI = new LatLng(poiMarker.nLatPoi, poiMarker.nLonPoi);
//        //mapCurrent.addMarker(new MarkerOptions().position(latLngI).title(poiMarker.cNomViaPoi));
//
//        String s = poiMarker.cNomPoi;
//        String typeMarker;
//        BitmapDrawable bitmapdraw; //= (BitmapDrawable)getResources().getDrawable(R.drawable.default1);
//        //CASE
//        if (poiMarker.cNomPoi.contains("Curva")) {
//            // it contains world
//            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.curvas);
//        }
//        else{
//            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.default1);
//        }
//
//        Log.d("NombreMARKER1", String.valueOf(poiMarker.cNomPoi));
//        int height = 100;
//        int width = 100;
//        //LatLng posIni = new LatLng(-12.090503367, -77.020577392);
//        //BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.curvas);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLngI);
////        mapCurrent.clear();
//        markerOptions.title(poiMarker.cNomPoi);
////        Icon marke = new Image
////        //markerOptions.icon()
////        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).anchor(0.5f,1f);
////        markerOptions
//        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vehiculo));
//        markerOptions.getPosition();
//        //mapCurrent.addMarker(markerOptions).showInfoWindow();


    }

    @Override
    public void onLocationChanged(Location location) {
        if (!isProcess) {

            isProcess = true;
            try {

                //registrando el uso del app
                long tiempoUsoApp = getInterval(dateUsoApp.getTime(), new Date());
                if (tiempoUsoApp > tiempoMinimousoApp) {
                    dateUsoApp = Calendar.getInstance();
                    objUsoApp.addFecha(dateUsoApp);
                    objUsoApp.saveIS(getActivity());

                }

                //Velocidad del GPS
                int velocidadGPS = location.getSpeed() == -1 ? 0 : (int) (location.getSpeed() * 3.6);

                /**
                 // OBTENCIÓN DE LA VELOCIDAD DE FORMA MANUAL

                 if (locationStart == null) {
                 locationStart = location;
                 dateStart = new Date();
                 }
                 velocidadGPS = getVelocityManual(location);
                 */


                //iVelocidadCero - validamos 4 intentos antes de mostrar la velocidad cero.
                if(velocidadGPS!=0 || (velocidadGPS==0 && iVelocidadCero>=4
                )) {
                    String messageVelocity = getMessageFor(velocidadGPS, currentCerco);
                    if (messageVelocity != null) {
                        removeAnuncio(AnuncioType.velocidad);
                        anuncios.add(new Anuncio(AnuncioType.velocidad, messageVelocity));
                        isRed = true;
                        isAmber = false;
                        //newColor(cRed);
                    } else {
                        removeAnuncio(AnuncioType.velocidad);
                        isRed = false;
                        getCautionMessageFor(velocidadGPS, currentCerco);
                        //newColor(cOrange);
                    }

                    velocGPS = velocidadGPS;
                    txtVelocidadGPS.setText(getVelocidad(velocGPS));
                    txtVelocidadGPS2.setText(getVelocidad(velocGPS));

                    if(velocidadGPS>0)
                        iVelocidadCero=0;

                } else {
                    iVelocidadCero++;
                    isRed = false;
                    isAmber = false;
                }



                /****  DEMO  **/
//                auxLocation = location;
//                auxLocation.setLatitude(auxPosLat);
//                auxLocation.setLongitude(auxPosLon);


                animationVelocity();

                //validate region
                Point point = new Point(location.getLongitude(), location.getLatitude()); //original
//                Point point = new Point(auxLocation.getLongitude(), auxLocation.getLatitude());

                //ubicacion actual
                //auto.setPosition(new LatLng( auxPosLat, auxPosLon));
                //auto.setPosition(new LatLng( -12.091003367, -77.023577392));
                //auto.setPosition(new LatLng(point.y, point.x));

//                mapCurrent.moveCamera(CameraUpdateFactory.newLatLng(auto.getPosition()));
//                POS_Actual =
//                        new CameraPosition.Builder().target(new LatLng(auto.getPosition().latitude, auto.getPosition().longitude))
//                                .zoom(19f)//modificar de forma dinamica
//                                .bearing(0) //horizontal
//                                .tilt(45)   //vertical
//                                .build();
//                mapCurrent.moveCamera(CameraUpdateFactory.newCameraPosition(POS_Actual));






//                //auxPos+=0.001;
//                auxPosLat-=0.000002;
//                auxPosLon-=0.00002;

//                if(cambio){
//                auxPosLat-=0.00005;
//                if(auxPosLat< -12.092864){
//                    auxPosLon+=0.00004;
//                }
//                else{
//                    auxPosLon-=0.00001;
//                }
//
//
//                }
//                else{
//
//                    auxPosLat-=0.000005;
//                auxPosLon-=0.00005;
//                }
//borrado
                mapController = map.getController();
                //mapController.setZoom(17.0);
                //GeoPoint startPoint = new GeoPoint(-12.087704, -77.000295);
//                startPoint = new GeoPoint(auxPosLat, auxPosLon);
                startPoint = new GeoPoint( location.getLatitude(),location.getLongitude());
//                mapController.setCenter(startPoint);
                m.setPosition(startPoint);
                mapController.zoomTo(16);
                map.getController().animateTo(startPoint);
                angle = inclinationAngle((float) location.getLatitude(), (float) location.getLongitude(),auxPosLatPos,auxPosLonPos);

                map.setMapOrientation(-angle+180); // direccion

                map.getController().animateTo(startPoint);
                //map.cam

                /******DEMO*******/
//                mapController = map.getController();
                //mapController.setZoom(17.0);
                //GeoPoint startPoint = new GeoPoint(-12.087704, -77.000295);
//                startPoint = new GeoPoint(auxPosLat, auxPosLon);
                //mapController.setCenter(startPoint);
//                m.setPosition(startPoint);
//                mapController.zoomTo(17);
//
//
//                angle = inclinationAngle(auxPosLat,auxPosLon,auxPosLatPos,auxPosLonPos);
//
//                map.setMapOrientation(-angle+180); // direccion
//
//                map.getController().animateTo(startPoint);
                //GeoPoint startPoint2 = new GeoPoint(startPoint.getLatitude(),startPoint.getLongitude(),angle);
                //map.getController().animateTo(startPoint2);
                //map.getController().setCenter(startPoint);
//                GeoPoint aa = new GeoPoint(startPoint);
//                map.getController().animateTo(aa,3.);

//                //angle = inclinationAngle((float) location.getLatitude(), (float) location.getLongitude(),auxPosLatPos,auxPosLonPos);
//                //if(Math.abs(angle - angleTemp) > 2) angle
//                Log.d("ANGLE", String.valueOf(angle));
//                auxPosLatPos = auxPosLat;
//                auxPosLonPos = auxPosLon;
                auxPosLatPos = (float) location.getLatitude();
                auxPosLonPos = (float) location.getLongitude();


//                POS_Actual = new CameraPosition.Builder()
//                        .target(new LatLng(auxPosLat, auxPosLon))      // Sets the center of the map to Mountain View
//                        .zoom(16)                   // Sets the zoom 17
//                        .bearing((int)angle + 180)                // Sets the orientation of the camera to east
//                        .tilt(60)                   // Sets the tilt of the camera to 30 degrees
//                        .build();                   // Creates a CameraPosition from the builder
                //currentMapbox.animateCamera(CameraUpdateFactory.newCameraPosition(POS_Actual),2000,null);
                //mapCurrent.animateCamera(CameraUpdateFactory.newCameraPosition(POS_Actual),2000,null);

                //mapCurrent.setPosition(new LatLng(a_lat, a_lon));
                //Log.d("MENSAJE","MENSAJEEE");
                //Log.d("POINT", String.valueOf(location.getLatitude()));
                //Log.d("POINT", String.valueOf(point.y));

                int index = validate(point, regions);
                if (index != -1) {

                    //set current cerco
                    currentCerco = cercos.get(index);

                    //actualizando la fecha dentro del cerco
                    dateDentroCerco = new Date();

                    //validate
                    if (!indexMensajesCercoHablados.contains(index)) {
                    	indexMensajesCercoHablados.clear();
                        indexMensajesCercoHablados.add(index);
                        String nombre = currentCerco.cNombre;
                        int velocidad = currentCerco.nVelCer;
                        String mensaje = String.format(MENSAJE_INGRESO_CERCO, nombre, velocidad);

                        removeAnuncio(anuncioInvalido.tipo);
                        removeAnuncio(AnuncioType.cerco);

                        anuncios.add(new Anuncio(AnuncioType.cerco, mensaje));

                        cercoText = "Cerco: " + nombre;
                        txtCerco.setText(cercoText);
                        velocCerco = velocidad;
                        txtVelocidadCerco.setText(getVelocidad(velocCerco));
                        txtVelocidadCerco2.setText(getVelocidad(velocCerco));

                        firstFueraCerco = true;
                    }
                    iOutsideCerco = 0;
                } else {
                    if (iOutsideCerco > 3) {
                        long tiempoFueraCerco = getInterval(dateDentroCerco, new Date());
                        int tiempoMinimoFueraCerco = (ruta.tiempoMinimoFueraCerco == null || ruta.tiempoMinimoFueraCerco == 0) ? 10 : ruta.tiempoMinimoFueraCerco;

                        if ((tiempoFueraCerco > tiempoMinimoFueraCerco)) {
                            firstFueraCerco = false;
                            currentCerco = null;

                            //limpiando los cercos ya hablados
                            indexMensajesCercoHablados = new ArraySet<>();

                            removeAnuncio(AnuncioType.invalido);
                            anuncios.add(anuncioInvalido);
                            cercoText = MENSAJE_CERCO_NO_ENCONTRADO;
                            txtCerco.setText(cercoText);
                            velocCerco = 0;
                            txtVelocidadCerco.setText(getVelocidad(velocCerco));
                            txtVelocidadCerco2.setText(getVelocidad(velocCerco));
                            dateDentroCerco = new Date();
                        }
                    }

                    iOutsideCerco++;
                }


                verifyIncidences(velocGPS,  velocCerco, location.getLatitude(), location.getLongitude());
                countLocation++;

                //validate POI
                Poi resultPoi = validate(location, pois);  //original
//                Poi resultPoi = validate(auxLocation, pois);

                //Poi resultPoi = validate(location, poisAux); //mis marcadores

                if (resultPoi != null) {

                    if (countAnuncioPoi == 1) {
                        dateDentroPoi = new Date();
                        if (!indexMensajesPoiHablados.contains(resultPoi)) {

                            //crear el marker
                            //addMarker(resultPoi);
                            cambio = true;

//                            GeoPoint startPointM;
//                            startPointM = new GeoPoint(resultPoi.nLatPoi, resultPoi.nLonPoi);

//                            Marker startMarker = new Marker(map);
//                            startMarker.setPosition(startPointM);
//                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//                            map.getOverlays().add(startMarker);


                            //enables this opt in feature
//                            Marker.ENABLE_TEXT_LABELS_WHEN_NO_IMAGE = true;
//build the marker  FINAL
//                            Marker m = new Marker(map);
//                            m.setTextLabelBackgroundColor(R.color.colorGreen);
//                            m.setTextLabelFontSize(14);
//                            m.setTextLabelForegroundColor(R.color.colorRed);
//                            m.setTitle("hello world");
////must set the icon to null last
//                            m.setIcon(null);
//                            m.setPosition(startPointM);
//                            map.getOverlays().add(m);

                            addMarkerBox(resultPoi);
//                            LatLng latLngI = new LatLng(resultPoi.nLatPoi, resultPoi.nLonPoi);
//                            currentMapbox.addMarker(new MarkerOptions().position(latLngI) );

//                            Marker myLocMarker = currentMapbox.addMarker(new MarkerOptions()
//                                    .position(myLocation)
//                                    .icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.bluebox, "your text goes here"))));

//                            var marker = new mapboxgl.Marker()
//                                    .setLngLat([30.5, 50.5]).addTo(map);

//                            LatLng latLngI = new LatLng(resultPoi.nLatPoi, resultPoi.nLonPoi);
//                            mapCurrent.addMarker(new MarkerOptions().position(latLngI).title(resultPoi.cNomViaPoi));


                            indexMensajesPoiHablados.add(resultPoi);
                            long distance = resultPoi.nDisPoi != null ? resultPoi.nDisPoi : MIN_DISTANCIA_POI;
                            String message = String.format(MENSAJE_X_DICTANCIA_POI, distance, resultPoi.cNomPoi);

                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                        } else {
                            String message = String.format(MENSAJE_CERCA_POI, resultPoi.cNomPoi);
                            //add to queue message cerco
                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                        }

                        countAnuncioPoi++;

                    } else if (countAnuncioPoi == 2) {
                        long tiempoFueraPoi = getInterval(dateDentroPoi, new Date());
                        if (tiempoFueraPoi > tiempoMinimoPoi) {

                            //removeAnuncio(AnuncioType.poi);
                            if (!indexMensajesPoiHablados.contains(resultPoi)) {
                                indexMensajesPoiHablados.add(resultPoi);
                                long distance = resultPoi.nDisPoi != null ? resultPoi.nDisPoi : MIN_DISTANCIA_POI;
                                String message = String.format(MENSAJE_X_DICTANCIA_POI, distance, resultPoi.cNomPoi);

                                anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            } else {
                                String message = String.format(MENSAJE_CERCA_POI, resultPoi.cNomPoi);
                                //add to queue message cerco
                                anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            }
                            countAnuncioPoi++;
                        }
                    }


                } else {
                    removeAnuncio(AnuncioType.poi);
                    countAnuncioPoi = 1;
                }

                Poi poi = validateIntermedio(location, poisIntermedio);
                if (poi != null) {
                	if (countPoiIntermedio == 1) {
                		playSound(R.raw.ruta_medio);
                		countPoiIntermedio++;
                	}            
                } else {
                	countPoiIntermedio = 1;
                }
                
                Log.d("ANUNCIOS", "" + anuncios.size());

                //anuncios.add(0,new Anuncio(AnuncioType.velocidad, "CURVA CERRADA PUICA"));
                if (anuncios.size() > 0)
                    reproducir(anuncios.get(0));


            } catch (Exception e) {
                e.printStackTrace();
            }

            isProcess = false;
        }


    }

    public void verifyIncidences(int velocGPS, int velocCerco, double latitude, double longitude){
        if(velocGPS >velocCerco && velocCerco!=0){
               // System.out.println("REGISTRA EXCESO>>> VelocCerco: "+ velocCerco+" velocGPS: "+ velocGPS+" latitude: "+latitude+" longitude: "+longitude);
                if(countLocation-1 == countLocationBefore){

                    if(countLocation-1==0){
                        idGroup++;
                        Incidences  incidences = new Incidences(idPlatePaths, velocCerco, velocGPS, latitude, longitude, new Date(), (int)idGroup );
                        mDBHelper.createIncidence(incidences);
                    }else{
                        Incidences  incidences = new Incidences(idPlatePaths, velocCerco, velocGPS, latitude, longitude, new Date(), (int)idGroup );
                        mDBHelper.createIncidence(incidences);
                    }

                }else{
                    idGroup++;
                    Incidences  incidences = new Incidences(idPlatePaths, velocCerco, velocGPS, latitude, longitude, new Date(), (int)idGroup );
                    mDBHelper.createIncidence(incidences);
                }
               // System.out.println("REGISTRA EXCESSO>> GROUP:" + idGroup);
                countLocationBefore= countLocation;
        }
    }

    private void removeAnuncio(AnuncioType tipo) {
        for (int i = anuncios.size() - 1; i >= 0; i--) {
            if (anuncios.get(i).tipo == tipo) {
                Log.d("removeAnuncio", "Tipo: " + tipo);
                anuncios.remove(i);
            }
        }
    }

    private long getInterval(Date dateOld, Date dateNew) {
        long interval = dateNew.getTime() - dateOld.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(interval);
    }

    private String getVelocidad(int velocidad) {
        return String.valueOf(velocidad);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LocationManager", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("LocationManager", "onProviderEnabled");
        iniLocation();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("LocationManager", "onProviderDisabled");
        iniLocation();
    }
    //---------------------
}
