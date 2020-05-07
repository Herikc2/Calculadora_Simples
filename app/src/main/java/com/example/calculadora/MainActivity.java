package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText numero1;
    EditText numero2;
    TextView resultado;
    ListView lv_historico;

    SQLiteDatabase db;

    //ArrayList<String> historico = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero1 = findViewById(R.id.et_num1);
        numero2  = findViewById(R.id.et_num2);
        resultado = findViewById(R.id.tv_resultado);
        lv_historico = findViewById(R.id.lv_historico);

        criarBancoDeDados();
    }

    public void adicaoResultado(View v){
        resultado.setText("");
        String  num1St = numero1.getText().toString().trim();
        String  num2St = numero2.getText().toString().trim();

        if (num1St.equals("") || num2St.equals("")){
            Toast.makeText(this, "Preencher os campos", Toast.LENGTH_SHORT).show();
        } else{
            float res = Float.valueOf(num1St) + Float.valueOf(num2St);
            resultado.setText("Resultado: " + res);

            gravarResultado("/", num1St, num2St, res);
            limparCamposNumericos();
            Toast.makeText(this, "Operação Realizada Com Sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void subtracaoResultado(View v){
        resultado.setText("");
        String  num1St = numero1.getText().toString().trim();
        String  num2St = numero2.getText().toString().trim();

        if (num1St.equals("") || num2St.equals("")){
            Toast.makeText(this, "Preencher os campos", Toast.LENGTH_SHORT).show();
        } else{
            float res = Float.valueOf(num1St) - Float.valueOf(num2St);
            resultado.setText("Resultado: " + res);

            gravarResultado("/", num1St, num2St, res);
            limparCamposNumericos();
            Toast.makeText(this, "Operação Realizada Com Sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void multiplicacaoResultado(View v){
        resultado.setText("");
        String  num1St = numero1.getText().toString().trim();
        String  num2St = numero2.getText().toString().trim();

        if (num1St.equals("") || num2St.equals("")){
            Toast.makeText(this, "Preencher os campos", Toast.LENGTH_SHORT).show();
        } else{
            float res = Float.valueOf(num1St) * Float.valueOf(num2St);
            resultado.setText("Resultado: " + res);

            gravarResultado("/", num1St, num2St, res);
            limparCamposNumericos();
            Toast.makeText(this, "Operação Realizada Com Sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void divisaoResultado(View v){
        resultado.setText("");
        String  num1St = numero1.getText().toString().trim();
        String  num2St = numero2.getText().toString().trim();

        if (num1St.equals("") || num2St.equals("")){
            Toast.makeText(this, "Preencher os campos", Toast.LENGTH_SHORT).show();
        } else{
            if(num2St.equals("0")){
                resultado.setText("Resultado: Erro");
            }else{
                float res = Float.valueOf(num1St) / Float.valueOf(num2St);

                gravarResultado("/", num1St, num2St, res);
                limparCamposNumericos();
                resultado.setText("Resultado: " + res);
            }
            Toast.makeText(this, "Operação Realizada Com Sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    private void gravarResultado(String op){
        /*historico.add(op);
        ArrayAdapter<String> histArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, historico);
        lv_historico.setAdapter(histArrayAdapter);*/
    }

    private void gravarResultado(String op, String n1, String n2, Float r){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO resultado (operacao, numero1, numero2, resultado) VALUES (");
        sql.append("'" + op + "',");
        sql.append(n1 + ",");
        sql.append(n2 + ",");
        sql.append(r);
        sql.append(")");

        try{
            db.execSQL(sql.toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void limparHistorico(View v){
        /*historico.clear();
        ArrayAdapter<String> histArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, historico);
        lv_historico.setAdapter(histArrayAdapter);

        Toast.makeText(this, "Histórico Apagado", Toast.LENGTH_SHORT).show();*/
    }

    private void limparCamposNumericos(){
        numero1.setText("");
        numero2.setText("");
    }

    private void criarBancoDeDados(){
        db = openOrCreateDatabase("calculadora.db", Context.MODE_PRIVATE, null);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS resultado (");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("operacao VARCHAR(1), ");
        sql.append("numero1 FLOAT(8, 2), ");
        sql.append("numero2 FLOAT(8, 2), ");
        sql.append("resultado FLOAT(8, 2)");
        sql.append(")");

        try{
            db.execSQL(sql.toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
