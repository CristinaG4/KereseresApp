package com.paulapps.kereseresapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.net.Uri;


import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

public class DetallesPedido extends AppCompatActivity {

    Intent intent;
    TextView tituloDetallePedido, categoriaDetallePedidoTV, nombreDetallePedidoTV, descripDetallePedidoTV, emailDetallePedidoTV,tipoPagoDetallePedidoTV;
    ImageView fotoCategoria;
    private Pedido pedido = new Pedido();
    Button btnVolverDetallePedido,btnContactarDetallePedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);

        //cogemos el objeto del intent->Pedido
        pedido = (Pedido) getIntent().getSerializableExtra("PEDIDO");

        //Declaramos botones
        btnVolverDetallePedido = (Button) findViewById(R.id.btnVolverDetallePedido);
        btnContactarDetallePedido= findViewById(R.id.btnContactarDetallePedido);

        //Declaramos TextView
        tituloDetallePedido = (TextView) findViewById(R.id.tituloDetallePedido);
        categoriaDetallePedidoTV = (TextView) findViewById(R.id.categoriaDetallePedidoTV);
        nombreDetallePedidoTV = (TextView) findViewById(R.id.nombreDetallePedidoTV);
        descripDetallePedidoTV = (TextView) findViewById(R.id.descripDetallePedidoTV);
        emailDetallePedidoTV = (TextView) findViewById(R.id.emailDetallePedidoTV);
        fotoCategoria = findViewById(R.id.fotoCategoria);
        tipoPagoDetallePedidoTV = findViewById(R.id.tipoPagoDetallePedidoTV);

        //ejemplo de como acceder a los datos de pedido
        tituloDetallePedido.setText(pedido.getTitulo());
        categoriaDetallePedidoTV.setText(pedido.getCategoria());
        nombreDetallePedidoTV.setText(pedido.getPerfil().getNombre());
        descripDetallePedidoTV.setText(pedido.getDescripcion());
        emailDetallePedidoTV.setText(pedido.getPerfil().getEmail());
        tipoPagoDetallePedidoTV.setText(pedido.getTipoPago());

        //accedemos a la imagen tipo categoria
        fotoCategoria.setImageResource(seleccionarImagenTipo());

        btnVolverDetallePedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetallesPedido.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        setResult(Activity.RESULT_OK,intent);

    }
    //CODIGO PARA CONTACTAR CON EL USUARIO
    public void contactar(View v){
        String tel=pedido.getPerfil().getTelf().toString();
        if(tel.equals("")){
            Toast.makeText(this,"User hasn´t a phone number defined",Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+tel));
            startActivity(i);
        }
    }

    private  int seleccionarImagenTipo(){
        if(pedido.getCategoria().equalsIgnoreCase("compañia")){
            return R.drawable.amigos;
        }else if (pedido.getCategoria().equalsIgnoreCase("informatica")){
            return R.drawable.ordenador;
        }else if (pedido.getCategoria().equalsIgnoreCase("clases")){
            return R.drawable.clases;
        }else if (pedido.getCategoria().equalsIgnoreCase("hogar")){
            return R.drawable.herramientas;
        }
        return 0;
    }
}
