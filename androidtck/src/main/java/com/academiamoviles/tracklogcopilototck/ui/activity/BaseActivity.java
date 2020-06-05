package com.academiamoviles.tracklogcopilototck.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.academiamoviles.tracklogcopilototck.MyApp;
import com.academiamoviles.tracklogcopilototck.R;

/**
 * Created by Android on 07/02/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    protected MyApp myApp;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (MyApp) getApplication();
        if (savedInstanceState != null) {
            myApp.objUser  = savedInstanceState.getParcelable("OBJ_LOGIN");
        }
    }

    protected Toolbar getActionBarToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        return mToolbar;
    }


    //--------------------------------------------------
    // Application
    //

    public void updateMyApp(){
        myApp = (MyApp) getApplication();
    }


    public void cerrarSesion() {
        myApp.clearApp();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();

        startActivity(intent);
    }

    public void abrirLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //finish();

    }

    //-------------------------------------------
    // Métodos implementados
    //-------------------------------------------


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("OBJ_LOGIN", myApp.objUser);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMyApp();
    }

}
