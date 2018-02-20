package com.paulapps.kereseresapp.activities.ver_pedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paulapps.kereseresapp.R;

public class VerDetallePedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_pedido);

        getSupportActionBar().hide();   //quitamos el ActionBar, es decir, el rectangulo azul con el nombre de la app

    }
}
