package com.paulapps.kereseresapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.model.Pedido;



public class DetallesPedido extends AppCompatActivity {

    Intent intent;
    //TextView tv1,tv2,tv3;
    TextView tituloDetallePedido, categoriaDetallePedidoTV, nombreDetallePedidoTV, descripDetallePedidoTV, emailDetallePedidoTV;
    private Pedido pedido = new Pedido();
    Button btnVolverDetallePedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);

        //cogemos el objeto del intent->Pedido
        pedido = (Pedido) getIntent().getSerializableExtra("PEDIDO");

        //Declaramos botones
        btnVolverDetallePedido = (Button) findViewById(R.id.btnVolverDetallePedido);

        //Declaramos TextView
        tituloDetallePedido = (TextView) findViewById(R.id.tituloDetallePedido);
        categoriaDetallePedidoTV = (TextView) findViewById(R.id.categoriaDetallePedidoTV);
        nombreDetallePedidoTV = (TextView) findViewById(R.id.nombreDetallePedidoTV);
        descripDetallePedidoTV = (TextView) findViewById(R.id.descripDetallePedidoTV);
        emailDetallePedidoTV = (TextView) findViewById(R.id.emailDetallePedidoTV);
/*
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);*/

        //ejemplo de como acceder a los datos de pedido
        tituloDetallePedido.setText(pedido.getTitulo());
        categoriaDetallePedidoTV.setText(pedido.getCategoria());
        nombreDetallePedidoTV.setText(pedido.getPerfil().getNombre());
        descripDetallePedidoTV.setText(pedido.getDescripcion());
        emailDetallePedidoTV.setText(pedido.getPerfil().getEmail());
       /* tv1.setText(pedido.getTitulo());
        tv2.setText(pedido.getCategoria());
        tv3.setText(pedido.getPerfil().getNombre());*/




        btnVolverDetallePedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetallesPedido.this, ListViewActivity.class);
                startActivity(intent);
            }
        });



        setResult(Activity.RESULT_OK,intent);

    }
}
