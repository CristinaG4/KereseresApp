package com.paulapps.kereseresapp.activities.ver_pedidos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.DetallesPedido;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.model.Pedido;

public class VerDetallePedidoActivity extends AppCompatActivity {

    Intent intent;
    TextView tituloDetallePedido, categoriaVerDetallePedidoTV, nombreVerDetallePedidoTV, direccionVerDetallePedidotv, descripVerDetallePedidoTV, emailVerDetallePedidoTV,tipoPagoVerDetallePedidoTV;
    ImageView fotoCategoria;
    private Pedido pedido = new Pedido();
    Button btnVolverVerDetallePedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_pedido);

        //cogemos el objeto del intent->Pedido
        pedido = (Pedido) getIntent().getSerializableExtra("PEDIDO");

        //Declaramos botones
        btnVolverVerDetallePedido = (Button) findViewById(R.id.btnVolverVerDetallePedido);

        //Declaramos TextView
        tituloDetallePedido = (TextView) findViewById(R.id.tituloDetallePedido);
        categoriaVerDetallePedidoTV = (TextView) findViewById(R.id.categoriaVerDetallePedidoTV);
        nombreVerDetallePedidoTV = (TextView) findViewById(R.id.nombreVerDetallePedidoTV);
        direccionVerDetallePedidotv=(TextView)findViewById(R.id.direccionVerDetallePedidoTV) ;
        descripVerDetallePedidoTV = (TextView) findViewById(R.id.descripVerDetallePedidoTV);
        emailVerDetallePedidoTV = (TextView) findViewById(R.id.emailVerDetallePedidoTV);
        fotoCategoria = findViewById(R.id.fotoCategoria);
        tipoPagoVerDetallePedidoTV = findViewById(R.id.tipoPagoVerDetallePedidoTV);

        //ejemplo de como acceder a los datos de pedido
        tituloDetallePedido.setText(pedido.getTitulo());
        categoriaVerDetallePedidoTV.setText(pedido.getCategoria());
        nombreVerDetallePedidoTV.setText(pedido.getPerfil().getNombre());
        direccionVerDetallePedidotv.setText(pedido.getPerfil().getApart());
        descripVerDetallePedidoTV.setText(pedido.getDescripcion());
        emailVerDetallePedidoTV.setText(pedido.getPerfil().getEmail());
        tipoPagoVerDetallePedidoTV.setText(pedido.getTipoPago());

        //accedemos a la imagen tipo categoria
        fotoCategoria.setImageResource(seleccionarImagenTipo());

        btnVolverVerDetallePedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(VerDetallePedidoActivity.this, verPedidosListViewActivity.class);
                startActivity(intent);
            }
        });

        setResult(Activity.RESULT_OK,intent);

    }

    private  int seleccionarImagenTipo(){
        if(pedido.getCategoria().equalsIgnoreCase("Company/Babysitter")){
            return R.drawable.amigos;
        }else if (pedido.getCategoria().equalsIgnoreCase("Computing")){
            return R.drawable.ordenador;
        }else if (pedido.getCategoria().equalsIgnoreCase("Lessons")){
            return R.drawable.clases;
        }else if (pedido.getCategoria().equalsIgnoreCase("Household items")){
            return R.drawable.herramientas;
        }
        return 0;
    }
}
