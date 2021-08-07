package com.academiamoviles.tracklogcopilototck.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.academiamoviles.tracklogcopilototck.model.Incidences;
import com.academiamoviles.tracklogcopilototck.model.Paths;
import com.academiamoviles.tracklogcopilototck.model.Plates;
import com.academiamoviles.tracklogcopilototck.model.PlatesHasPaths;
import com.academiamoviles.tracklogcopilototck.model.Users;
import com.academiamoviles.tracklogcopilototck.ui.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by veraj on 2/08/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "database.db";
    public static  String DBLOCATION ="data/data/com.academiamoviles.tracklogcopilototck/databases/";
    public static final int DATABASE_VERSION = 1;
    private Context mContext;
    protected SQLiteDatabase mDatabase;



    public DatabaseHelper(Context context){
        super(context, DBNAME, null, 1);
        this.mContext = context;
        Log.d("ENTRO", "DatabaseHelper: ");
        if(android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            Log.d("QUE", "AQUI: ");

//            SQLiteOpenHelper helper = new SQLiteOpenHelper(context, DBNAME, null, DATABASE_VERSION) {
//                @Override
//                public void onCreate(SQLiteDatabase db) {
//                    db.disableWriteAheadLogging();  // Here the solution
//                    super.onOpen(db);
//                }
//
//                @Override
//                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//                }
//            };
//            SQLiteDatabase database = helper.getReadableDatabase();
//            DBLOCATION = database.getPath();
//            Log.d("QUE", "AQUI: ");
//            Log.d("ENTRO0", DBLOCATION);
            DBLOCATION = context.getApplicationInfo().dataDir + "/databases/";
        } else
        if(android.os.Build.VERSION.SDK_INT >= 17) {
//            Log.d("ENTRO", "DatabaseHelper: 17 ");
            DBLOCATION = context.getApplicationInfo().dataDir + "/databases/";
            Log.d("ENTRO1", DBLOCATION);
        } else {
//            Log.d("ENTRO", "DatabaseHelper:1 ");
            DBLOCATION = "/data/data/" + context.getPackageName() + "/databases/";
            Log.d("ENTRO2", DBLOCATION);
        }

    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.disableWriteAheadLogging();  // Here the solution
        super.onOpen(sqLiteDatabase);
//        DataAux dbhelperr = new DataAux(mContext);
//        try {
//            dbhelperr.createDatabase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mDatabase = dbhelperr.myDataBase;
//        mDatabase = dbhelperr.getWritableDatabase();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void copyDatabase() throws IOException {
        InputStream myInput = mContext.getAssets().open("database.db");
        String outputFileName = mContext.getDatabasePath(DBNAME).getPath();
        OutputStream myOutput = new FileOutputStream(outputFileName);

        byte [] buffer = new byte[1024];
        int lenght;
        while((lenght = myInput.read(buffer))>0){
            myOutput.write(buffer,0,lenght);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase(){
        Log.d("PATHTF", "YA");
        String dbPath= mContext.getDatabasePath(DBNAME).getPath();
        Log.d("PATHTF", "YAEND");
//        Log.d("PATHTF", String.valueOf(mDatabase.isOpen()));

        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        Log.d("PATH", dbPath);

//        if (android.os.Build.VERSION.SDK_INT >= 9) {
//            MySQLiteOpenHelper helper = new MySQLiteOpenHelper();
//            SQLiteDatabase database = helper.getReadableDatabase();
//            myPath = database.getPath();
//
//        } else {
//            String DB_PATH = Environment.getDataDirectory() + "/data/my.trial.app/databases/";
//            myPath = DB_PATH + dbName;
//        }


        mDatabase = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READWRITE);
//        mDatabase.disableWriteAheadLogging();

        Log.d("PATHTFFf", String.valueOf(mDatabase.isOpen()));
//        Cursor cursorr = mDatabase.rawQuery("select count(*) from users",null);
//        Cursor cursorr = mDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='users'",null);
        Cursor cursorr = mDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='users'",null);
//        Cursor cursorra = mDatabase.rawQuery("SELECT count(*) FROM users",null);
//        int countt = -2;
        if (!cursorr.moveToFirst())
        {
           //cursorr.close();
//            countt = 00;
        }
        int count = cursorr.getInt(0);

//        Log.d("PATH--00", String.valueOf(countt));
        Log.d("PATH--11", String.valueOf(count));
        if(cursorr.getInt(0)== 0){
            //copia la bd
//            createDatabase1();
            Log.d("PLS", "CREATE DATABASE: ");
            String query1 = "CREATE TABLE \"paths\" ( \"idPaths\" INTEGER PRIMARY KEY AUTOINCREMENT, \"nCodRuta\" INTEGER);";
            String query2 = "CREATE TABLE \"incidences\" (\"idIncidences\" INTEGER PRIMARY KEY AUTOINCREMENT, \"idPlates_has_paths\" INTEGER, \"speed\" INTEGER, \"excess\" INTEGER,\"latitude\" REAL,\"longitude\" REAL, \"register\" TEXT,\"idgroup\" INTEGER);";
            String query3 = "CREATE TABLE \"plates\" ( \"idPlates\" INTEGER PRIMARY KEY AUTOINCREMENT, \"serie\" TEXT, \"idUsers\" INTEGER);";
            String query4= "CREATE TABLE \"plates_has_paths\" (\"idPlates_has_paths\" INTEGER PRIMARY KEY AUTOINCREMENT, \"idPlates\" INTEGER, \"idPaths\" INTEGER, \"date\" TEXT);";
            String query5 = "CREATE TABLE sqlite_sequence(name,seq);";
            String query6 = "CREATE TABLE \"users\" (\"idUsers\" INTEGER PRIMARY KEY AUTOINCREMENT, \"firstname\" TEXT, \"lastname\" TEXT, \"phone\" TEXT, \"username\" TEXT);";

            String query7 = "CREATE TABLE \"plates_has_paths_times\" (\"idPlates_has_paths_times\" INTEGER PRIMARY KEY AUTOINCREMENT, \"idPlates_has_paths\" INTEGER, \"time\" INTEGER);";

            mDatabase.execSQL(query1);
            mDatabase.execSQL(query2);
            mDatabase.execSQL(query3);
            mDatabase.execSQL(query4);
//            mDatabase.execSQL(query5);
            mDatabase.execSQL(query6);
            mDatabase.execSQL(query7);

            Log.d("PLSEND", "CREATE DATABASE: END ");

        }

        Cursor cursorTime = mDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='plates_has_paths_times'",null);
        if (!cursorTime.moveToFirst())
        {
            //cursorr.close();
//            countt = 00;
        }

        if(cursorTime.getInt(0)== 0){
            //copia la bd
//            createDatabase1();


            String query7 = "CREATE TABLE \"plates_has_paths_times\" (\"idPlates_has_paths_times\" INTEGER PRIMARY KEY AUTOINCREMENT, \"idPlates_has_paths\" INTEGER, \"time\" INTEGER);";


            mDatabase.execSQL(query7);

            Log.d("PLSEND", "TABLE TIME: END ");

        }

        Log.d("PATH111", String.valueOf(cursorr));
        Log.d("PATH113", String.valueOf(cursorr.getCount()));
//        SELECT count(*) FROM

//        mDatabase.disableWriteAheadLogging();
        Log.d("PATH11", String.valueOf(mDatabase));
//        mDatabase.disableWriteAheadLogging();
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            MySQLiteOpenHelper helper = new MySQLiteOpenHelper();
//            SQLiteDatabase database = helper.getReadableDatabase();
//            myPath = database.getPath();
//
//        } else {
//            String DB_PATH = Environment.getDataDirectory() + "/data/my.trial.app/databases/";
//            myPath = DB_PATH + dbName;
//        }
//
//        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//        checkDB.disableWriteAheadLogging();






    }

    public void closeDatabase(){
        if(mDatabase!=null){
            mDatabase.close();
        }
    }

    public int getLastIdPlatesHasPaths(int idPlates, int idPaths){
        int lastId;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from plates_has_paths where idPlates ="+idPlates+" and idPaths ="+idPaths+" order by date desc ",null);
        cursor.moveToFirst();
        if(cursor.getCount()<=0 ){
            return 0;
        }
        lastId =cursor.getInt(0);
        closeDatabase();
        return lastId;

    }
    public int getLastIdPlatesHasPaths(int idPaths){
        int lastId;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from plates_has_paths where idPaths ="+idPaths+" order by date desc ",null);
        cursor.moveToFirst();
        if(cursor.getCount()<=0 ){
            return 0;
        }
        lastId =cursor.getInt(0);
        closeDatabase();
        return lastId;
    }


    public int getGroups(int idPlates_has_Paths){
        int numGroup;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT DISTINCT idgroup FROM incidences WHERE idPlates_has_paths = "+idPlates_has_Paths+"  order by idgroup desc",null);
        cursor.moveToFirst();
        if(cursor.getCount()<=0 ){
            return 0;
        }
        numGroup =cursor.getInt(0);
        closeDatabase();
        return numGroup;
    }

    public List<Incidences> getIncidences(int idPlates_has_Paths) throws ParseException {
        Incidences incidences = null;
        List<Incidences> incidencesList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM incidences WHERE idPlates_has_paths = "+idPlates_has_Paths,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //users = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(6)); //column register
            incidences = new Incidences(cursor.getInt(1),cursor.getInt(2), cursor.getInt(3),cursor.getDouble(4), cursor.getDouble(5), date, cursor.getInt(7));
            incidences.setIdIncidences(cursor.getInt(0));
            incidencesList.add(incidences);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();
        return incidencesList;
    }


    public void createIncidence(Incidences incidences){
        openDatabase();
        mDatabase.execSQL("INSERT INTO incidences (idPlates_has_paths, speed, excess, latitude, longitude, register, idgroup) VALUES ('"+incidences.getIdPlates_has_paths()+"','"+incidences.getSpeed()+"','"+incidences.getExcess()+"','"+
                            incidences.getLatitude()+"','"+incidences.getLongitude()+"','"+Configuration.createFormat(incidences.getRegister())+"','"+ incidences.getIdgroup()+"')");

        closeDatabase();
    }

    public Paths getPathsByCodRuta(int codRuta){
        Paths paths = null;

        openDatabase();
        Cursor cursor =mDatabase.rawQuery("SELECT * FROM paths WHERE nCodRuta ="+codRuta, null);
        cursor.moveToFirst();
        paths = new Paths(cursor.getInt(1));
        paths.setIdPaths(cursor.getInt(0));
        cursor.close();
        closeDatabase();
        return paths;
    }

    public int createPlatePaths(PlatesHasPaths platesHasPaths){
        openDatabase();

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("idPlates", platesHasPaths.getIdPlates()+"");
        nuevoRegistro.put("idPaths",platesHasPaths.getIdPaths()+"");
        nuevoRegistro.put("date", Configuration.createFormat(platesHasPaths.getDate()));

        Log.d("FormatoTime", Configuration.createFormat(platesHasPaths.getDate()));
        long idPltesHasPaths= mDatabase.insert("plates_has_paths", null, nuevoRegistro);

        //mDatabase.execSQL("INSERT INTO plates_has_paths (idPlates, idPaths, date) VALUES ('"+platesHasPaths.getIdPlates()+"','"+platesHasPaths.getIdPaths()+"','"+platesHasPaths.getDate().toString()+"')");

        //mDatabase

        closeDatabase();

        return (int) idPltesHasPaths;
    }
    public void createPlatePathsTime(int idPlatesHasPaths, int time){
        openDatabase();

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("idPlates_has_paths", idPlatesHasPaths+"");
        nuevoRegistro.put("time",time+"");


        long idPltesHasPaths= mDatabase.insert("plates_has_paths_times", null, nuevoRegistro);

        //mDatabase.execSQL("INSERT INTO plates_has_paths (idPlates, idPaths, date) VALUES ('"+platesHasPaths.getIdPlates()+"','"+platesHasPaths.getIdPaths()+"','"+platesHasPaths.getDate().toString()+"')");

        //mDatabase

        closeDatabase();

        //return (int) idPltesHasPaths;
    }


    public void createPaths(List<Paths> listPaths){
        openDatabase();
        for(int i=0; i<listPaths.size(); i++){
            if(!checkDataExist("paths","nCodRuta",listPaths.get(i).getnCodRuta())){
                mDatabase.execSQL("INSERT INTO paths (nCodRuta) VALUES ('"+listPaths.get(i).getnCodRuta()+"')");
                System.out.println("EXISTE Y REGISTRO NUEVA RUTA");
            }else{
                System.out.println("NO EXISTE Y ACTUALIZO LA RUTA");
            }

        }

        closeDatabase();
    }

    public List<Users> getListUsers(){
        Users users = null;
        List<Users> usersList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM users",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //users = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            users = new Users(cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4));
            users.setIdUsers(cursor.getInt(0));
            usersList.add(users);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();
        return usersList;

    }


    public List<Plates> getListPlatesByUserID(Integer idUser){
        Plates plates = null;
        List<Plates> platesList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM plates WHERE idUsers = "+idUser,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //users = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            plates = new Plates(cursor.getString(1), cursor.getInt(2));

            plates.setIdPlates(cursor.getInt(0));
            platesList.add(plates);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();
        return platesList;

    }

    public Users getUserByUsername(String username){
        Users users = null;

        openDatabase();
        Cursor cursor =mDatabase.rawQuery("SELECT * FROM users WHERE username ="+username, null);
        cursor.moveToFirst();
        users = new Users(cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4));
        users.setIdUsers(cursor.getInt(0));
        cursor.close();
        closeDatabase();
        return users;


    }

    public Plates getPlateBySerie(String serie){
        Plates plates = null;

        openDatabase();
        Cursor cursor =mDatabase.rawQuery("SELECT * FROM plates WHERE serie ='"+serie+"'", null);
        cursor.moveToFirst();
        plates = new Plates(cursor.getString(1),cursor.getInt(2));
        plates.setIdPlates(cursor.getInt(0));
        cursor.close();
        closeDatabase();
        return plates;

    }
    public Plates getPlateByIdPlatesHasPaths(int id){
        Plates plates = null;
        openDatabase();
        Cursor cursor =mDatabase.rawQuery("SELECT p.* FROM plates_has_paths pp JOIN plates p ON pp.idPlates=p.idPlates WHERE idPlates_has_paths=" + id, null);
        cursor.moveToFirst();
        plates = new Plates(cursor.getString(1),cursor.getInt(2));
        plates.setIdPlates(cursor.getInt(0));
        cursor.close();
        closeDatabase();
        return plates;
    }

    public int getTimeByIdPlatesHasPaths(int id){
        Log.d("AQUI", "getTimeByIdPlatesHasPaths: ");
        int time=0;
        openDatabase();
        Cursor cursor =mDatabase.rawQuery("SELECT count(*) FROM plates_has_paths_times WHERE idPlates_has_paths=" + id, null);
        cursor.moveToFirst();

//        if (!cursorr.moveToFirst())
//        {
//            //cursorr.close();
////            countt = 00;
//        }
//        int count = cursorr.getInt(0);
//
////        Log.d("PATH--00", String.valueOf(countt));
//        Log.d("PATH--11", String.valueOf(count));
        if(cursor.getInt(0)== 0){
            time=0;
        }
        else{
            Cursor cursor2 =mDatabase.rawQuery("SELECT time FROM plates_has_paths_times WHERE idPlates_has_paths=" + id, null);
            cursor2.moveToFirst();
            time = cursor2.getInt(0);
        }

//        time = cursor.getInt(0);
//        Log.d("tiempof", String.valueOf(cursor.getInt(0)));
//        Log.d("tiempof", String.valueOf(cursor.getInt(1)));
//        time = new Plates(cursor.getString(1),cursor.getInt(2));
//        plates.setIdPlates(cursor.getInt(0));
        cursor.close();
        closeDatabase();
        return time;
    }

    public void createUser(Users users){
        openDatabase();

//        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

//        Boolean a = mDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='table_name'");
//        Cursor cursor =mDatabase.rawQuery("SELECT count(*) FROM users", null);

//        if (c.moveToFirst()) {
//            while ( !c.isAfterLast() ) {
//                Toast.makeText(activityName.this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
//                c.moveToNext();
//            }
//        }

        if(!checkDataExist("users","username",users.getUsername())){
            mDatabase.execSQL("INSERT INTO users (firstname,lastname,phone,username) VALUES ('"+users.getFirstname()+"','"+users.getLastname()+"','"+users.getPhone()+"','"+users.getUsername()+"')");

        }
        closeDatabase();
    }


    public void createPlate(Plates plates){
        openDatabase();
        if(!checkDataExistWithID("plates","serie", plates.getSerie(),"idUsers",plates.getIdUsers())){
            mDatabase.execSQL("INSERT INTO plates (serie, idUsers) VALUES ('"+plates.getSerie()+"','"+plates.getIdUsers()+"')");
        }
        closeDatabase();
    }


    public boolean checkDataExist(String tableName, String dbfield, String fieldValue) {
        String Query = "Select * from " + tableName + " where " + dbfield + " = '" + fieldValue+"'";
        String Query1 = "Select * from " + tableName + " where " + dbfield + " = \'" + fieldValue+"\'";

        Log.d("ANTERROR", "antes del error: ");
        Log.d("ANTERRORQ", Query);
        Log.d("ANTERRORQ1", Query1);
        Cursor cursor = mDatabase.rawQuery(Query, null);  //error

        Log.d("POSERROR", String.valueOf(cursor));
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public boolean checkDataExist(String tableName, String dbfield, Integer fieldValueID) {
        String Query = "Select * from " + tableName + " where " + dbfield + " = " + fieldValueID;
        Cursor cursor = mDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }




    public boolean checkDataExistWithID(String tableName, String dbfield, String fieldValue, String dbFieldID ,Integer fieldIDValue) {
        String Query = "Select * from " + tableName + " where " + dbfield + " = '" + fieldValue+"' and "+ dbFieldID+" = "+fieldIDValue;
        Cursor cursor = mDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void createRegistersSintetic() throws ParseException {

        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:56:01");
        Incidences in1 = new Incidences(2,15,20,-16.407578, -71.524402,date1 ,1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:56:11");
        Incidences in2 = new Incidences(2,15,22,-16.407578, -71.524402,date2 ,1);
        Date date3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:56:21");
        Incidences in3 = new Incidences(2,15,16,-16.407578, -71.524402,date3 ,1);

        Date date4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:56:31");
        Incidences in4 = new Incidences(2,15,30,-16.407578, -71.524402,date4 ,2);
        Date date5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:56:41");
        Incidences in5 = new Incidences(2,15,35,-16.407578, -71.524402,date5 ,2);
        Date date6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:56:51");
        Incidences in6 = new Incidences(2,15,33,-16.407578, -71.524402,date6 ,2);

        Date date7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:57:12");
        Incidences in7 = new Incidences(2,20,21,-16.407578, -71.524402,date7 ,3);
        Date date8 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:57:22");
        Incidences in8 = new Incidences(2,20,22,-16.407578, -71.524402,date8 ,3);
        Date date9 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-24 12:57:32");
        Incidences in9 = new Incidences(2,20,23,-16.407578, -71.524402,date9 ,3);


        List<Incidences> listIn = new ArrayList<>();
        listIn.add(in1); listIn.add(in2); listIn.add(in3); listIn.add(in4); listIn.add(in5); listIn.add(in6); listIn.add(in7); listIn.add(in8); listIn.add(in9);

        openDatabase();

        for (Incidences incidences:listIn ) {
            mDatabase.execSQL("INSERT INTO incidences (idPlates_has_paths, speed, excess, latitude, longitude, register, idgroup) VALUES ('"+incidences.getIdPlates_has_paths()+"','"+incidences.getSpeed()+"','"+incidences.getExcess()+"','"+
                    incidences.getLatitude()+"','"+incidences.getLongitude()+"','"+Configuration.createFormat(incidences.getRegister())+"','"+ incidences.getIdgroup()+"')");

        }



        closeDatabase();
    }
}
