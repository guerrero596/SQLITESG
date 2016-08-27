package com.facci.sqlitesg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivitySG extends AppCompatActivity {

    DBHelper dbSQLITE;

    EditText txtNombre,txtApellido,txtSemestre,txtID;

    Button btnInsertar,btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sg);

        dbSQLITE = new DBHelper(this);

    }

    public void insertarClick(View v){

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtSemestre = (EditText) findViewById(R.id.txtSemestre);

        boolean estaInsertado = dbSQLITE.insertar(txtNombre.getText().toString(),txtApellido.getText().toString(),Integer.parseInt(txtSemestre.getText().toString()));

        if(estaInsertado)
            Toast.makeText(MainActivitySG.this,"Datos Ingresados",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivitySG.this,"Lo sentimos ocurrió un error",Toast.LENGTH_SHORT).show();

    }

    public void verTodosClick(View v){

        Cursor res = dbSQLITE.selectVerTodos();
        if(res.getCount() == 0){
            mostrarMensaje("Error","No se encontraron registros");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append("Id : "+res.getString(0)+"\n");
            buffer.append("Nombre : "+res.getString(1)+"\n");
            buffer.append("Apellido : "+res.getString(2)+"\n");
            buffer.append("Semestre : "+res.getInt(3)+"\n\n");
        }

        mostrarMensaje("Registros",buffer.toString());
    }

    public void mostrarMensaje(String titulo, String Mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(Mensaje);
        builder.show();

    }

    public void modificarRegistroClick(View v){


        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtSemestre = (EditText) findViewById(R.id.txtSemestre);
        txtID = (EditText) findViewById(R.id.txtID);

        boolean estaAcutalizado = dbSQLITE.modificarRegistro(txtID.getText().toString(),txtNombre.getText().toString(),txtApellido.getText().toString(),Integer.parseInt(txtSemestre.getText().toString()));

        if (estaAcutalizado == true){
            Toast.makeText(MainActivitySG.this,"Registro Actualizado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivitySG.this,"ERROR: Registro NO Actualizado",Toast.LENGTH_SHORT).show();
        }
    }


    public void eliminarRegistroClick(View v){


        txtID = (EditText) findViewById(R.id.txtID);

        Integer registrosEliminados = dbSQLITE.eliminarRegistro(txtID.getText().toString());

        if(registrosEliminados > 0 ){
            Toast.makeText(MainActivitySG.this,"Registro(s) Eliminado(s)",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivitySG.this,"ERROR: Registro no eliminado",Toast.LENGTH_SHORT).show();
        }

    }

}
