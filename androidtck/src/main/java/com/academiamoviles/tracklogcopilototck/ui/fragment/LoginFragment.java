package com.academiamoviles.tracklogcopilototck.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.academiamoviles.tracklogcopilototck.R;
import com.academiamoviles.tracklogcopilototck.database.DatabaseHelper;
import com.academiamoviles.tracklogcopilototck.model.Users;
import com.academiamoviles.tracklogcopilototck.ui.UsoApp;
import com.academiamoviles.tracklogcopilototck.ui.activity.RutasActivity;
import com.academiamoviles.tracklogcopilototck.util.Constants;
import com.academiamoviles.tracklogcopilototck.util.SeguridadUtils;
import com.academiamoviles.tracklogcopilototck.util.UserUtil;
import com.academiamoviles.tracklogcopilototck.util.Util;
import com.academiamoviles.tracklogcopilototck.ws.asynctask.BaseAsyncTask;
import com.academiamoviles.tracklogcopilototck.ws.asynctask.HttpAsyncTask;
import com.academiamoviles.tracklogcopilototck.ws.response.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Android on 07/02/2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    EditText edtUser, edtPassword;
    TextView txtVersion;
    HttpAsyncTask taskLogin;
    DatabaseHelper mDBHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        mDBHelper = new DatabaseHelper(getContext());
        //mDBHelper = dbhelper.getWritableDatabase();
        edtUser = (EditText) view.findViewById(R.id.edtUser);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        txtVersion = (TextView) view.findViewById(R.id.txtVersion);
        view.findViewById(R.id.btnLogin).setOnClickListener(this);

        String sVersionApp = Util.getPackageInfo(getActivity()).versionName;
        txtVersion.setText("Versión " + sVersionApp);


        try {
            String user = SeguridadUtils.desencriptar(UserUtil.getUser(getActivity()));
            //String userWeb = UserUtil.getUserWeb(getActivity());
            if (!user.equals("")) {
                myApp.objUser = User.readIS(getActivity());
                if (myApp.objUser != null) {
                    //myApp.clearData();
                    UsoApp.deleteIS(getActivity());
                    launchActivity();
                }

                //myApp.clearApp();
                //edtUser.setText(user);
                //edtPassword.setText(pass);
                //login(user, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                myApp.clearApp();
                if (Util.connection(getActivity())) {
                    if (validarCampos()) {
                        login(edtUser.getText().toString().trim(), edtPassword.getText().toString().trim());
                    }
                } else {
                    Util.showToast(getActivity(), Constants.Mensaje.ERROR_CONEXION);
                }
                break;
        }
    }

    private Boolean validarCampos() {

        if (edtUser.getText().toString().trim().equals("")) {
            Util.showToast(getActivity(), "Complete los datos solicitados.");
            return false;
        } else if (edtPassword.getText().toString().trim().equals("")) {
            Util.showToast(getActivity(), "Complete los datos solicitados.");
            return false;
        }

        return true;
    }

    private void login(final String user, final String password) {
        showProgress("Login");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("usuario", user);
        jsonObject.addProperty("password", password);

        taskLogin = new HttpAsyncTask("usuario/login", jsonObject.toString());

        Log.d("RutaWeb", String.valueOf(taskLogin));

        taskLogin.setCallback(new BaseAsyncTask.Callback() {
            @Override
            public void onPostExecuteTask(int requestCode, int resultCode, Object result) {

                hideProgress();
                Log.d("ResCode", String.valueOf(resultCode));
                Log.d("ResOK", String.valueOf(BaseAsyncTask.RESULT_OK));

                if (resultCode == BaseAsyncTask.RESULT_OK) {
                    Log.d("LOGINNN", "FUNCIONA");
                    try {
                        JSONObject jsonResponse = new JSONObject((String) result);
                        if (jsonResponse.getBoolean("ok")) {
                            Log.d("LOGINNNDATA", jsonResponse.getString("data"));
                            myApp.objUser = User.fromJson(jsonResponse.getString("data"));
                            Log.d("INICIO", "MENSAJES Ini");
                            Log.d("LOGIN User ID: ", String.valueOf(myApp.objUser.userId));
                            Log.d("LOGIN Atribute: ", String.valueOf(myApp.objUser.attribute));
                            Log.d("LOGIN User Web", String.valueOf(myApp.objUser.userWeb));
                            Log.d("LOGIN ID Empresa", String.valueOf(myApp.objUser.idEmpresa));
                            Log.d("LOGIN MSG", String.valueOf(myApp.objUser.msg));
//                            Log.d("LOGIN FLAG", String.valueOf(myApp.objUser.msg));
                            Log.d("FIN", "MENSAJES Fin");
                            if(myApp.objUser.msg==null) {

                                //myApp.objUser.userWeb ="2465";
                                //User.saveIS(getContext(), myApp.objUser.toJson());

                                User.saveIS(getContext(), jsonResponse.getString("data"));

                                UserUtil.saveCredentials(getActivity(), SeguridadUtils.encriptar(user), SeguridadUtils.encriptar(password));
                                Log.d("LOGINNN", "FUNCIONAAAAA");
                                mDBHelper.createUser(new Users(user,"","",myApp.objUser.userWeb));
                                launchActivity();
                            } else {
                                showMensajeDialog(myApp.objUser.msg);
                            }
                        } else {
                            Util.showToast(getActivity(), "Usuario inválido.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("ERROR", "ERRORRRR");
                    showMensajeDialog(Constants.Mensaje.ERROR_SC);
                }
            }
        });
        taskLogin.execute();
    }

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), RutasActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (taskLogin != null && taskLogin.getStatus() == AsyncTask.Status.RUNNING) {
            taskLogin.cancel(true);
        }
    }
}
