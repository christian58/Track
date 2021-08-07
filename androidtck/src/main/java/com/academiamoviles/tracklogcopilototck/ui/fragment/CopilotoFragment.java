package com.academiamoviles.tracklogcopilototck.ui.fragment;

//package example.javatpoint.com.toast;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Toast;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
//import org.osmdroid.views.MapView;
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


//import com.mapbox.android.accounts.R;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


//import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
//import com.google.
/**
 * Created by Android on 07/02/2017.
 */
//OnCameraIdleListener, OnCameraMoveStartedListener, OnCameraMoveListener, OnCameraMoveCanceledListener
public class CopilotoFragment extends BaseFragment implements LocationListener, View.OnClickListener {


    Boolean cargarMarker = true;



    Date currentTime;
    /***   OSMDROID ****/
//    Log.d

    private static int REQUEST_WRITE_STORAGE = 1;

    Context context;

//    MapView map;
    IMapController mapController;
    GeoPoint startPoint;
    Marker m;
//     myLocationoverlay = new MyLocationOverlay(getActivity(), openMapView);



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

    //-16.425117, -71.527268 Test multi point MUNICIPALIDAD
    float auxPosLat=(float)-16.425117;
    float auxPosLon=(float)-71.527268;
//CERRO
//    float auxPosLat=(float)-16.433927;
//    float auxPosLon=(float)-71.538634;
//    ,

//    float auxPosLat=(float)-16.438761;
//    float auxPosLon=(float)-71.539128;

    //-16.430484, -71.534597
    //-16.434042, -71.539079 punto cerca
//-12.091400, -77.027060   -12.090503367,-77.020577392
//    -13.844449, -72.225149    -14.017981, -71.973300
    float auxPosLatPos=(float)-16.431228;
    float auxPosLonPos=(float)-71.538901;
//    -16.431228, -71.538901
    boolean cambio = true;

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
//        Log.d("ACTUALIZACION", "Region downloaded ssssuccessfully.");
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
//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 1 metro
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 1 metro
    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 5 * 1000; // 1 segundo
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 segundo

    //Mensajes
    private static final String MENSAJE_FUERA_CERCO = "No se encuentra en ningún cerco, regrese a la ruta";
    //private static final String MENSAJE_EXCESO_VELOCIDAD = "Exceso de velocidad en el cerco: %s, baje su velocidad a: %d kilómetros por hora";
    private static final String MENSAJE_EXCESO_VELOCIDAD = "Está excediendo la velocidad, su límite es: %d kilómetros por hora";
    private static final String MENSAJE_INGRESO_CERCO = "Ingresó en el cerco: %s, la velocidad máxima es: %d kilómetros por hora.";
    private static final String MENSAJE_NOMBRE_CERCO = "Esta en el cerco: %s, la velocidad máxima es: %d kilómetros por hora.";
    private static final String MENSAJE_CERCA_POI = "Esta cerca del punto: %s, la velocidad máxima es: %d kilómetros por hora.";
    private static final String MENSAJE_X_DICTANCIA_POI = "Está a %s metros del punto %s, la velocidad máxima es: %d kilómetros por hora. ";
    private static final String MENSAJE_CERCO_NO_ENCONTRADO = "Cerco no encontrado.";
    private static final String MENSAJE_CERCA_VELOCIDAD_LIMITE = "Está cerca de la velocidad límite de: %d kilómetros por hora";

    private static final long MIN_DISTANCIA_POI = 210;
    private static final long MIN_DISTANCIA_POI_OUT = 40;
    Boolean boolShowMarker_out = false;
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
    Button btnUpdateMap;
    LinearLayout viewLinear1;
    LinearLayout viewLinear2;
    Boolean showMap = false;
    Boolean btn_Update_Map = true;

    LinearLayout viewMarkerLinear;
    TextView txtVelocidadMarker;
    TextView txtPOIMarker;
    Boolean boolShowMarker = false;
    Boolean pregunta1 = false;
    Boolean pregunta2 = false;
    Boolean pregunta3 = false;

    Boolean mostrarCuadro = false;
    int quitarMarker = 0;
    float posMarkerlat=(float)1.090503367;
    float posMarkerlon=(float)-7.020577392;

    Location posMarker = new Location("");
//    posLocationMarker.setLongitude(posMarkerlon);





    LinearLayout viewMarkerLinear2;
    TextView txtVelocidadMarker2;
    TextView txtPOIMarker2;


    TextToSpeech tts;
    int velocGPS = 0, velocCerco = 0;
    String cercoText = "";

    //Copiloto
    private static final int minPrecision = 50;

    int codRuta2;

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

    Date repeticionMensajeCerco = new Date();
    long tiempoRepeticion = 100;
    boolean reproducirAnuncioMarker = false;

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
            new Poi(12, "POI Curva paucarpata 1 (90 km/h)", -16.420091, -71.495574),
            new Poi(12, "POI Normal paucarpata 2 (100 km/h)", -16.421484, -71.496358),
            new Poi(12, "POI modulo paucarpata (20 km/h)", -16.422814, -71.512971),
            new Poi(596, "POI rico pollo (50 km/h)", -16.424074, -71.515808),
            new Poi(596, "POI King Broaster (60 km/h)", -16.420899, -71.508245),
            new Poi(596, "POI Curva Salaverry (80 km/h)",-16.433585, -71.539944)

//
            //37.422065, -122.082955


            ));

    Boolean flagMap = false;

    Boolean showMarkersTotal = true;

    private MapView mapView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));

//        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        currentTime = Calendar.getInstance().getTime();


        //verifyIncidences(velocGPS,  velocCerco, 0, 0);
        /******** OSMDROID ********/


//        Mapbox.getInstance((getContext()), "pk.eyJ1IjoiY2hyaXN0aWFuNTgiLCJhIjoiY2s5eGVkNTk5MDAwNDNscGllNXVyY2FmaSJ9.LF3Yh_TfhQMc3O-DYP1d-Q");

        codRuta = getActivity().getIntent().getIntExtra(CopilotoActivity.COD_RUTA, 0);
        codRuta2 = getActivity().getIntent().getIntExtra(CopilotoActivity.COD_RUTA_2, 0);

//        Log.d(TAG, "onCreate: ");

        mDBHelper = new DatabaseHelper(getContext());

        Paths paths = mDBHelper.getPathsByCodRuta(codRuta);

        PlatesHasPaths platesHasPaths = new PlatesHasPaths(Configuration.userPlate.getPlates().getIdPlates(),paths.getIdPaths(),new Date()); // crea ahora
        idPlatePaths = mDBHelper.createPlatePaths(platesHasPaths); // id de la ruta creada  date de inicio
//        Log.d("IDD", String.valueOf(idPlatePaths));
        /*** LLAMAR A METODOS PARA CREAR UNA TABLA CON ESE ID Y DATOS***/

        Configuration.IDPLATES_HAS_PATHS=idPlatePaths;
        Configuration.COD_RUTA = codRuta;
//        Log.d("IDD?2", String.valueOf(Configuration.IDPLATES_HAS_PATHS));

//        locationStart.setTime(100);

//        Log.d("entro?", "onCreate: ");
        getDatos();
        createPolygons();
        initTts();
//        Log.d("entro?", "onCreateSalio  : ");
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

        //int showMapa = mDBHelper.getUserByUsername(myApp.objUser.attribute);
        //Log.d("SHOWMAPA:" , String.valueOf(getActivity().getIntent().getIntExtra(CopilotoActivity.SHOW_MAP, 0))+"/");

        String auxBool = String.valueOf(getActivity().getIntent().getIntExtra(CopilotoActivity.SHOW_MAP, 0));
       // Log.d("SHOWMAPAUX", auxBool);

        //if(String.valueOf(getActivity().getIntent().getIntExtra(CopilotoActivity.SHOW_MAP, 0))=="1"){
        String aux2 = "1";



        // MAP BOX






    }

    /***
     * Obtiene los datos almacenados en la memoria
     */
    private void getDatos() {
//        Log.d("AVER", "GET DATOS");
        //Log.d("MyApp", String.valueOf(myApp.objUser.userId));
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
//        Log.d("SIZE CERCO", String.valueOf(cerco_response.listaCercos.size()));
        for (Cerco item : cerco_response.listaCercos) {
//            Log.d("AVERR", String.valueOf(item.nCodRut) + " == " + codRuta);
            cercos_Aux.add(item);
            if (item.nCodRut == codRuta) {
                cercos.add(item);
            }
        }
//        Log.d("POIS DE AREQUIPA", String.valueOf(cerco_response.listaCercos.size()));
        pois = new ArrayList<>();
        Poi_Response poi_response = Poi_Response.readIS(getContext());
        //pois = poi_response.listaPois;
//        Log.d("POIS DE AREQUIPA", String.valueOf(poi_response.listaPois.size()));
        for (Poi item : poi_response.listaPois) {
//            Log.d("AVERR", String.valueOf(item.nCodRutas));
            if (item.nCodRutas!=null && item.nCodRutas.indexOf(codRuta) != -1) {
                pois.add(item); //aqui puedo setearlo en un nuevo campo

            }
        }
//        Log.d(" SALI", String.valueOf(poi_response.listaPois.size()));

    }

    /**
     * Método que crea el polígono
     */
    private void createPolygons() {
        regions = new ArrayList<>();
//        Log.d("CERCO SIZE", String.valueOf(cercos.size()));
        for (Cerco item : cercos) {
            List<Point> coords = new ArrayList<>();
//            Log.d("Cerco IN", String.valueOf(item.gPolCer.points.size()));
            for (Cerco.Point point : item.gPolCer.points) {
//                Log.d("PuntosCerco", point.y + " , " + point.x);
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
//        Log.d("CERCO SIZE", String.valueOf(cercos_Aux.size()));
        poisMap = new ArrayList<>();
        Boolean aux = false;
        double x2 = 0;
        double y2 = 0;
        for (Cerco item : cercos_Aux) {
            List<Point> coords = new ArrayList<>();
//            Log.d("Cerco IN", String.valueOf(item.gPolCer.points.size()));
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
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 40, 40, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    private Drawable resize2(Drawable image) {

        int width= Resources.getSystem().getDisplayMetrics().widthPixels;
        int height=Resources.getSystem().getDisplayMetrics().heightPixels;
//        Log.d("MARKERSSIZE_W", String.valueOf(width));
//        Log.d("MARKERSSIZE_H", String.valueOf(height));
//        D/MARKERSSIZE_W: 720
//        D/MARKERSSIZE_H: 1396
//        D/MARKERSSIZE_W: 1800
//        D/MARKERSSIZE_H: 2448

        Bitmap b = ((BitmapDrawable)image).getBitmap();
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 90, 135, false);
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 45, 67, false);
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 30, 46, false);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 20, 30, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.copiloto_fragment, container, false);


        context = getContext();
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));


        File osmdroid = new File(Environment.getExternalStorageDirectory().getPath(), "Download/osmdroid");
        File tiles = new File(osmdroid.getPath(), "tiles");

        org.osmdroid.config.Configuration.getInstance().setOsmdroidBasePath(osmdroid);
        org.osmdroid.config.Configuration.getInstance().setOsmdroidTileCache(tiles);


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



        viewLinear1 = (LinearLayout) view.findViewById(R.id.contacts_type); //mapa
        viewLinear2 = (LinearLayout) view.findViewById(R.id.contacts_type2);

        btnShowMap = (Button) view.findViewById(R.id.btn_update_view);
//        btnUpdateMap = (Button) view.findViewById(R.id.btn_current_position);

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!showMap){

//                    viewLinear2.setEnabled(false);
//                    viewLinear1.setEnabled(true);
                    viewLinear2.setVisibility(View.GONE);
                    viewLinear1.setVisibility(View.VISIBLE);
                    showMap=!showMap;
                }
                else{
//                    viewLinear1.setEnabled(false);
//                    viewLinear2.setEnabled(true);
                    viewLinear1.setVisibility(View.GONE);
                    viewLinear2.setVisibility(View.VISIBLE);
                    showMap=!showMap;
                }

            }
        });


//        if(flagMap){
//            Log.d("BUTTONMAP", "TRUE: ");
//            btnShowMap.setVisibility(View.GONE);
////            btnShowMap.setEnabled(false);
//        }else{
//            Log.d("BUTTONMAP", "FALSE: ");
//        }


        viewMarkerLinear = (LinearLayout) view.findViewById(R.id.viewMarker_container);
        txtVelocidadMarker = (TextView) view.findViewById(R.id.txtVelocidadMarker);
        txtPOIMarker = (TextView) view.findViewById(R.id.nombrePOI);

        viewMarkerLinear2 = (LinearLayout) view.findViewById(R.id.viewMarker_container2);//viewMarker_container2
        txtVelocidadMarker2 = (TextView) view.findViewById(R.id.txtVelocidadMarker2);
        txtPOIMarker2 = (TextView) view.findViewById(R.id.nombrePOI2);


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

        mostrarCuadro = false;
        for (Poi poi : pois) {
            Location locationPoi = new Location("");
            locationPoi.setLongitude(poi.nLonPoi);
            locationPoi.setLatitude(poi.nLatPoi);
            float distance = location.distanceTo(locationPoi);
//            Log.d("DIS", "validate: " + distance);
            if (poi.nDisPoi != null) {
//            if (false) {
            	if (distance < poi.nDisPoi * 3/2) {
                    mostrarCuadro = true;
                    if(!indexMensajesPoiHablados.contains(poi)){
                        return poi;
                    }else{
                        if(reproducirAnuncioMarker){
                            reproducirAnuncioMarker=false;

                            Integer posParen = poi.cNomPoi.indexOf('(');
                            if(posParen != -1){
                                String messageAux = poi.cNomPoi.substring(0,posParen);
                                Integer posParenEnd = poi.cNomPoi.indexOf(')');
                                String numeroVelocidad = poi.cNomPoi.substring(posParen, posParenEnd);
                                int velocidad = Integer.parseInt(numeroVelocidad.replaceAll("[^0-9]+", ""));
//                            String message = String.format(MENSAJE_CERCA_POI, resultPoi.cNomPoi);
                                String message = String.format(MENSAJE_CERCA_POI, messageAux,velocidad);
                                //add to queue message cerco
                                if(boolShowMarker){
                                    anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                                }
//                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            }else{
                                String messageAux = poi.cNomPoi;
//                            String message = String.format(MENSAJE_CERCA_POI, resultPoi.cNomPoi);
                                String message = String.format(MENSAJE_CERCA_POI, messageAux,0);
                                //add to queue message cerco
                                if(boolShowMarker){
                                    anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                                }
//                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            }



                        }
                    }

//            		return poi;
            	}else{
//                    Log.d("DISTANCIA MIN POIQ", "DISTANCIA ES  " + distance);
                }
        	} else if (distance < MIN_DISTANCIA_POI * 3/ 2) {
//                Log.d("DIS", String.valueOf((MIN_DISTANCIA_POI * 3/ 2)));
                mostrarCuadro = true;
                if(!indexMensajesPoiHablados.contains(poi)){
                    return poi;
                }else{
                    if(reproducirAnuncioMarker){
                        reproducirAnuncioMarker=false;


                        Integer posParen = poi.cNomPoi.indexOf('(');
                        if(posParen != -1){
                            String messageAux = poi.cNomPoi.substring(0,posParen);
                            Integer posParenEnd = poi.cNomPoi.indexOf(')');
                            String numeroVelocidad = poi.cNomPoi.substring(posParen, posParenEnd);
                            int velocidad = Integer.parseInt(numeroVelocidad.replaceAll("[^0-9]+", ""));
//                            String message = String.format(MENSAJE_CERCA_POI, resultPoi.cNomPoi);
                            String message = String.format(MENSAJE_CERCA_POI, messageAux,velocidad);
                            //add to queue message cerco
                            if(boolShowMarker){
                                anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            }
//                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                        }else{
                            String messageAux = poi.cNomPoi;
//                            String message = String.format(MENSAJE_CERCA_POI, resultPoi.cNomPoi);
                            String message = String.format(MENSAJE_CERCA_POI, messageAux,0);
                            //add to queue message cerco
                            if(boolShowMarker){
                                anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            }
//                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                        }



                    }
                }


//                return poi;
            }
        }
//        Log.d("MARCADORR", "SALII");
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
//            viewFlash.setBackgroundColor(cOrange);
//            viewFlash2.setBackgroundColor(cOrange);
//            txtVelocidadGPS.setTextColor(cOrange);
//            txtVelocidadGPS2.setTextColor(cOrange);
//            txtUnidadGPS.setTextColor(cOrange);
//            txtUnidadGPS2.setTextColor(cOrange);

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


    }





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
//        map.onResume();
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
//        map.onPause();

        setRetainInstance(true);

    }
    //OSMdroid
//    @SuppressLint("HandlerLeak")
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what) {
//                case 1:
//                    GeoPoint point = (GeoPoint) msg.obj;
//                    map.getController().setCenter(point);
//                    Marker marker = new Marker(map);
//                    marker.setPosition(point);
//                    map.getOverlays().clear();
//                    map.getOverlays().add(marker);
//                    map.invalidate();
//                    break;
//            }
//        }
//    };

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
//        Log.d("location", "entroo: ");
        if (!GPSProviderEnabled(getActivity())) {
//            Log.d("location", "entroo1: ");
            //this.canGetLocation = false;
            txtMensaje.setEnabled(true);
//            txtMensaje.setVisibility(View.VISIBLE);
        } else {
//            Log.d("location", "entroo:2 ");
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                txtMensaje.setVisibility(View.GONE);
//                txtMensaje.setEnabled(false);
                this.canGetLocation = true;
//                Log.d("location", "entroo3: ");
                if (isNetworkEnabled) {
//                    Log.d("location", "entroo4: ");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }
                if (isGPSEnabled) {
//                    Log.d("location", "entroo5: ");
                    //No entra a esta funcion
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    Log.d("AVER", "LAMO?10: ");
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);//
                    locationStart = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //add
//                    mobileLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    Log.d("AVER", "LAMO?11: ");

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
//actual anterior
    public float inclinationAngle(float x1, float y1, float x2, float y2){
        if(x2-x1 != 0){
            float m = (y2-y1)/(x2-x1);
            float angle_rad = (float) Math.atan(m);
            float angle2 = (float) (angle_rad*180/3.1415926);
//            Log.d("ANGLEE", String.valueOf(angle2));
            float distancia = (float) Math.sqrt(Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
            //0.00003
            if(distancia > 0.0002){//0.00003
                if(x1-x2<0){
                    return angle2+180;
                }
                return  angle2;
            }


            return  angle;
//            return  angle2;

        }
        else {
            if(y1-y2>0){
                return 90;
            }
            else return -90;



        }

    }


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
        canvas.drawText(".", 0, bm.getHeight()/2, textStyle);

        return new BitmapDrawable(bm);
    }



    private void addTotalMarker(List<Poi> pois){

        for (Poi poiMarker : pois) {
//            Log.d("LONG YO", String.valueOf("INGRESANDO"));
            GeoPoint startPointM;
            startPointM = new GeoPoint(poiMarker.nLatPoi, poiMarker.nLonPoi);

//            Log.d("PUNTOSSS", poiMarker.cNomPoi);
//            Marker m = new Marker(map);


//            Integer posParen = poiMarker.cNomPoi.indexOf('(');
//            String messageAux;
//            if(posParen==-1){//sin velocidad, sin parentesis
//                messageAux = poiMarker.cNomPoi;
//            }else{//con velocidad-con parentesis
//
//                messageAux = poiMarker.cNomPoi.substring(0,posParen);
//
//            }
//
//            //m.textlabel
//            if (poiMarker.cNomPoi.contains("Curva")) {
//                m.setIcon(resize2(getResources().getDrawable(R.drawable.markerr)));
//            }
//            else{
//                m.setIcon(resize2(getResources().getDrawable(R.drawable.markerr)));
//            }
//            m.setPosition(startPointM);
//            m.setAnchor(0.5f,0);

//            map.getOverlays().add(m);

        }



    }

        public void addMarkerBox(Poi poiMarker){

        GeoPoint startPointM;
        startPointM = new GeoPoint(poiMarker.nLatPoi, poiMarker.nLonPoi);

        Integer posParen = poiMarker.cNomPoi.indexOf('(');
        String messageAux;
        if(posParen != -1){
            messageAux = poiMarker.cNomPoi.substring(0,posParen);
        }else{
            messageAux = poiMarker.cNomPoi;
        }


        Integer posParen2 = poiMarker.cNomPoi.indexOf(')');//HERE
        if(posParen2 != -1){
            Integer posSpace = poiMarker.cNomPoi.indexOf(' ',posParen);
            if(posParen2-posParen > 2){
                String velocidadString = poiMarker.cNomPoi.substring(posParen+1,posSpace);

                posMarker.setLongitude(startPointM.getLongitude());
                posMarker.setLatitude(startPointM.getLatitude());


                posMarkerlon = (float) startPointM.getLongitude();
                posMarkerlat = (float) startPointM.getLatitude();

                txtVelocidadMarker.setText(velocidadString);
//                viewMarkerLinear.setEnabled(true);
                viewMarkerLinear.setVisibility(View.VISIBLE);
                txtPOIMarker.setText(messageAux);

                if(flagMap){
//                    viewMarkerLinear2.setEnabled(false);
                    viewMarkerLinear2.setVisibility(View.GONE);
                }else{
//                    viewMarkerLinear2.setEnabled(true);
                    viewMarkerLinear2.setVisibility(View.VISIBLE);
                }
                txtVelocidadMarker2.setText(velocidadString);
                txtPOIMarker2.setText(messageAux);
                //viewMarkerLinear2.setVisibility(View.VISIBLE);
                boolShowMarker = true;
            }
        }else{
            posMarker.setLongitude(startPointM.getLongitude());
            posMarker.setLatitude(startPointM.getLatitude());


            posMarkerlon = (float) startPointM.getLongitude();
            posMarkerlat = (float) startPointM.getLatitude();

            txtVelocidadMarker.setText("0");
//            viewMarkerLinear.setEnabled(true);
            viewMarkerLinear.setVisibility(View.VISIBLE);
            txtPOIMarker.setText(messageAux);

            if(flagMap){
//                viewMarkerLinear2.setEnabled(false);
                viewMarkerLinear2.setVisibility(View.GONE);
            }else{
//                viewMarkerLinear2.setEnabled(true);
                viewMarkerLinear2.setVisibility(View.VISIBLE);
            }
            txtVelocidadMarker2.setText("0");
            txtPOIMarker2.setText(messageAux);
            //viewMarkerLinear2.setVisibility(View.VISIBLE);
            boolShowMarker = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {



//        Date currentTime = Calendar.getInstance().getTime();
//        Log.d("TIEMPOOO: ", String.valueOf(currentTime));
//-16.438761, -71.539128

//        -16.434042, -71.539079
//        location.setLatitude(-16.434042);
//        location.setLongitude(-71.539079);
//        auxLocation = location;
//        auxLocation.setLatitude(auxPosLat);
        if (!isProcess) {

            isProcess = true;
            try {
//

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

//                Log.d("HDP", "onLocationChanged: ");


                /****  DEMO  **/
//                auxLocation = location;
//                auxLocation.setLatitude(auxPosLat);
//                auxLocation.setLongitude(auxPosLon);

//                -13.844449, -72.225149


                animationVelocity();

                //validate region
                Point point = new Point(location.getLongitude(), location.getLatitude()); //original

//                AddMarkerAuto

//                ((CopilotoActivity)getActivity()).AddMarkerAuto(location.getLatitude(),location.getLongitude());

//                Point point = new Point(auxLocation.getLongitude(), auxLocation.getLatitude());


//                //auxPos+=0.001;
//                auxPosLat+=0.00002;//TEST
//                auxPosLon+=0.0002;

                /****DEMO***/
//                if(auxPosLat < -16.427671 && cambio){
//                    auxPosLat+=0.0005;
//
//                }
//                else{
//                    cambio= false;
//                    auxPosLon-=0.0004;
//                    auxPosLat-=0.0005;
//                }





//                mapController = map.getController();
//                startPoint = new GeoPoint( location.getLatitude(),location.getLongitude());
//                m.setPosition(startPoint);
//                mapController.zoomTo(15);
//                map.getController().animateTo(startPoint);
                angle = inclinationAngle((float) location.getLatitude(), (float) location.getLongitude(),auxPosLatPos,auxPosLonPos);

//                map.setMapOrientation(-angle); // direccion   map.setMapOrientation(-angle+180);
//                map.getController().animateTo(startPoint);
                //map.cam
                if(showMap){
                    ((CopilotoActivity)getActivity()).RotationCamera(angle,(float)location.getLongitude(), (float)location.getLatitude());
                }
                /******** TEST *********/

                /******DEMO*******/

                /****REL ***/
                auxPosLatPos = (float) location.getLatitude();
                auxPosLonPos = (float) location.getLongitude();
                /***** TEST ****/
//                auxPosLatPos = (float) auxLocation.getLatitude();
//                auxPosLonPos = (float) auxLocation.getLongitude();




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

                        repeticionMensajeCerco = new Date();

                    }else{
                        long tiempoMensajeCerco = getInterval(repeticionMensajeCerco, new Date());

                        if(String.valueOf(currentCerco.dRadio) != "null"){
                            tiempoRepeticion = Long.parseLong(currentCerco.cFlagKpi);
                        }

//                        if (tiempoMensajeCerco > tiempoRepeticion) {
                        if (false) {
                            String nombre = currentCerco.cNombre;
                            int velocidad = currentCerco.nVelCer;
                            String mensaje = String.format(MENSAJE_NOMBRE_CERCO, nombre, velocidad);
                            removeAnuncio(anuncioInvalido.tipo);
                            removeAnuncio(AnuncioType.cerco);

                            anuncios.add(new Anuncio(AnuncioType.cerco, mensaje));
                            repeticionMensajeCerco = new Date();
                            reproducirAnuncioMarker = true;
                            countAnuncioPoi = 1;

                        }
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

                if(boolShowMarker){

                    float distance = location.distanceTo(posMarker);

                    //220
                    if(distance < 80){
                        boolShowMarker_out = true;

                    }
                    if(boolShowMarker_out){

                        if(distance > MIN_DISTANCIA_POI_OUT){
                            quitarMarker+=1;
                            if(quitarMarker>2){
                                quitarMarker = 0;

                                if(mostrarCuadro){
//                                    viewMarkerLinear.setEnabled(false);
//                                    viewMarkerLinear2.setEnabled(false);
                                    viewMarkerLinear.setVisibility(View.GONE);
                                    viewMarkerLinear2.setVisibility(View.GONE);
                                    boolShowMarker = false;
                                    boolShowMarker_out = false;
                                }else{
                                    quitarMarker=0;
                                }
//                                viewMarkerLinear.setVisibility(View.GONE);
//                                viewMarkerLinear2.setVisibility(View.GONE);
//                                boolShowMarker = false;
//                                boolShowMarker_out = false;

                            }



                        }else{
                            quitarMarker=0;
                        }
//                        quitarMarker+=1;
                    }




                }

                if(cargarMarker){
                    cargarMarker = false;
                    ((CopilotoActivity)getActivity()).CurrentPosition(pois,0.0f);
                }

                verifyIncidences(velocGPS,  velocCerco, location.getLatitude(), location.getLongitude());
                countLocation++;

                //validate POI
                Poi resultPoi = validate(location, pois);  //original


                if (resultPoi != null) {
                    dateDentroPoi = new Date();
                    if (!indexMensajesPoiHablados.contains(resultPoi)) {



                        addMarkerBox(resultPoi);

                        indexMensajesPoiHablados.add(resultPoi);
                        long distance = resultPoi.nDisPoi != null ? resultPoi.nDisPoi : MIN_DISTANCIA_POI;
                        Integer posParen = resultPoi.cNomPoi.indexOf('(');
                        if(posParen != -1){
                            String messageAux = resultPoi.cNomPoi.substring(0,posParen);

                            Integer posParenEnd = resultPoi.cNomPoi.indexOf(')');
                            String numeroVelocidad = resultPoi.cNomPoi.substring(posParen, posParenEnd);
                            int velocidad = Integer.parseInt(numeroVelocidad.replaceAll("[^0-9]+", ""));

                            //String message = String.format(MENSAJE_X_DICTANCIA_POI, distance, resultPoi.cNomPoi);
                            String message = String.format(MENSAJE_X_DICTANCIA_POI, distance, messageAux,velocidad);
                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            countAnuncioPoi++;
                        }else{
                            String messageAux = resultPoi.cNomPoi;
                            //String message = String.format(MENSAJE_X_DICTANCIA_POI, distance, resultPoi.cNomPoi);
                            String message = String.format(MENSAJE_X_DICTANCIA_POI, distance, messageAux,0);
                            anuncios.add(0, new Anuncio(AnuncioType.poi, message));
                            countAnuncioPoi++;
                        }


                    }

//                        countAnuncioPoi++;




                } else {
                    removeAnuncio(AnuncioType.poi);
                    countAnuncioPoi = 1;
                }

//                Log.d("ANUNCIOS", "" + anuncios.size());

                //anuncios.add(0,new Anuncio(AnuncioType.velocidad, "CURVA CERRADA PUICA"));
                if (anuncios.size() > 0)
                    reproducir(anuncios.get(0));


            } catch (Exception e) {
                e.printStackTrace();
            }

            isProcess = false;
        }


        if(showMarkersTotal){
            showMarkersTotal=false;
            addTotalMarker(pois);
        }

//        Date currentTimeEnd = Calendar.getInstance().getTime();
//        Log.d("TIEMPOOO END: ", String.valueOf(currentTimeEnd));


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
//                Log.d("removeAnuncio", "Tipo: " + tipo);
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
//        Log.d("LocationManager", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
//        Log.d("LocationManager", "onProviderEnabled");
        iniLocation();

    }

    @Override
    public void onProviderDisabled(String provider) {
//        Log.d("LocationManager", "onProviderDisabled");
        iniLocation();
    }
    //---------------------
}
