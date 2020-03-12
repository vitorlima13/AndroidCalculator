package com.example.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ArrayList<String> arrayList = new ArrayList<String>();
    String ValorAtual = "";
    String FormulaCompleta = "";

    public void onClick1 (View v){

        TextView CampoFormula = (TextView) findViewById(R.id.CampoFormula);

        Button button = (Button) v;

        //busca o texto/valores contido nos botões
        ValorAtual = (String) button.getText().toString();

        //SE não contém operador
        if(!ValorAtual.contains("+") && !ValorAtual.contains("-") && !ValorAtual.contains("*") &&!ValorAtual.contains("/")){

            //concatena os textos
            FormulaCompleta = FormulaCompleta + ValorAtual;

            //remove o ultimo valor inserido e preenche com o valor concatenado
            //ex = se digitar 10, o 1 entra no array
            //ao digitar 0, o 1 é removido do array
            //e a concatenação dos textos (10) é inserida no array
            if (arrayList.size()>0){
                arrayList.remove((arrayList.size()-1));
            }
            arrayList.add(FormulaCompleta);
        } else{
            //devido a ultima posição ser removida,
            //em ordem de mantermos a operação inserida
            //precisamos inserir ela 2x
            arrayList.add(ValorAtual);
            arrayList.add(ValorAtual);
            FormulaCompleta="";
        }

        //CampoFormula.setText(CampoFormula.getText().toString() + ValorAtual);
        CampoFormula.setText(arrayList.toString());

    }

    public void onClick2 (View v){

        TextView CampoResultado = (TextView) findViewById(R.id.CampoResultado);

        double calc = 0;
        int tamanho = arrayList.size();

        //Exemplo -> array (2,+,3,*,4,-,3) tamanho = 7

        while (tamanho != 1) {

            if (tamanho>3){
                if (arrayList.get(1).contains("*") || arrayList.get(1).contains("/")){
                    if (arrayList.get(1).contains("*")){calc = Double.parseDouble(arrayList.get(0))*Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("/")){calc = Double.parseDouble(arrayList.get(0))/Double.parseDouble(arrayList.get(2));}

                    //calc = 12 -- array (2,+,3,*,4,-,3)
                    arrayList.remove(0); // (2,+,*,4,-,3)
                    arrayList.remove(0); // (2,+,4,-,3)
                    arrayList.remove(0); // (2,+,-,3)
                    arrayList.add(0,Double.toString(calc)); // (2,+,12,-,3)
                    tamanho=arrayList.size(); // tamanho = 5

                } else if (arrayList.get(3).contains("*") || arrayList.get(3).contains("/")){
                    if (arrayList.get(3).contains("*")){calc = Double.parseDouble(arrayList.get(2))*Double.parseDouble(arrayList.get(4));}
                    if (arrayList.get(3).contains("/")){calc = Double.parseDouble(arrayList.get(2))/Double.parseDouble(arrayList.get(4));}

                    //calc = 12 -- array (2,+,3,*,4,-,3)
                    arrayList.remove(2); // (2,+,*,4,-,3)
                    arrayList.remove(2); // (2,+,4,-,3)
                    arrayList.remove(2); // (2,+,-,3)
                    arrayList.add(2,Double.toString(calc)); // (2,+,12,-,3)
                    tamanho=arrayList.size(); // tamanho = 5

                } else {

                    // (2,+,12,-,3)
                    if (arrayList.get(1).contains("+")){calc = Double.parseDouble(arrayList.get(0))+Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("-")){calc = Double.parseDouble(arrayList.get(0))-Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("*")){calc = Double.parseDouble(arrayList.get(0))*Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("/")){calc = Double.parseDouble(arrayList.get(0))/Double.parseDouble(arrayList.get(2));}

                    //calc = 14
                    arrayList.remove(0); // (+,12,-,3)
                    arrayList.remove(0); // (12,-,3)
                    arrayList.remove(0); // (-,3)
                    arrayList.add(0,Double.toString(calc)); // (14, -, 3)
                    tamanho=arrayList.size(); // tamanho = 3

                }
                // tamanho <=3
            } else {

                if (arrayList.get(1).contains("+")){calc = Double.parseDouble(arrayList.get(0))+Double.parseDouble(arrayList.get(2));}
                if (arrayList.get(1).contains("-")){calc = Double.parseDouble(arrayList.get(0))-Double.parseDouble(arrayList.get(2));}
                if (arrayList.get(1).contains("*")){calc = Double.parseDouble(arrayList.get(0))*Double.parseDouble(arrayList.get(2));}
                if (arrayList.get(1).contains("/")){calc = Double.parseDouble(arrayList.get(0))/Double.parseDouble(arrayList.get(2));}

                // calc = 11
                arrayList.remove(0); // (-, 3)
                arrayList.remove(0); // (3)
                arrayList.remove(0);// ()
                arrayList.add(0,Double.toString(calc)); // (11)
                tamanho=arrayList.size(); // tamanho = 1
                //tamanho 1 encerra o loop

            }
        }

        if(calc%1==0) {
            NumberFormat SemDecimal = new DecimalFormat("0");
            CampoResultado.setText(SemDecimal.format(calc));
        } else {
            NumberFormat ComDecimal = new DecimalFormat("0.##");
            CampoResultado.setText(ComDecimal.format(calc));
        }

    }

    public void clear (View v){

        TextView CampoFormula = (TextView) findViewById(R.id.CampoFormula);
        TextView CampoResultado = (TextView) findViewById(R.id.CampoResultado);
        ValorAtual = "";
        FormulaCompleta = "";
        CampoFormula.setText("");
        CampoResultado.setText("0");
        arrayList.clear();

    }

}
