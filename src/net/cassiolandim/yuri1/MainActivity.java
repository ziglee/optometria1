package net.cassiolandim.yuri1;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	private TextView primeira;
	private TextView segunda;
	private TextView primeiroNum;
	private TextView segundoNum;
	private EditText palavra;
	private Button proxima;
	private Button enviar;
	private Button instrucoes;
	private String objetivo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        primeira = (TextView) findViewById(R.id.primeira);
        segunda = (TextView) findViewById(R.id.segunda);
        primeiroNum = (TextView) findViewById(R.id.primeiroNum);
        segundoNum = (TextView) findViewById(R.id.segundoNum);
        palavra = (EditText) findViewById(R.id.palavra);
        instrucoes = (Button) findViewById(R.id.instrucoes);
        proxima = (Button) findViewById(R.id.proxima);
        enviar = (Button) findViewById(R.id.enviar);
        
        instrucoes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, InstrucoesActivity.class));
			}
        });
        
        proxima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setupObjetivo();
			}
		});
        
        enviar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String palavraTeste = palavra.getText().toString();
				if (palavraTeste.trim().toUpperCase().equals(objetivo.trim().toUpperCase())) {
					setupObjetivo();
				} else {
					Toast.makeText(MainActivity.this, "Tente novamente", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        setupObjetivo();
        
        SharedPreferences sharedPreferences = this.getSharedPreferences("myPreferences", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("firstTime", true)) {
        	startActivity(new Intent(MainActivity.this, InstrucoesActivity.class));
        	Editor edit = sharedPreferences.edit();
        	edit.putBoolean("firstTime", false);
        	edit.commit();
        }
    }
    
    private void setupObjetivo() {
    	Resources res = getResources();
    	TypedArray palavras = res.obtainTypedArray(R.array.palavras);
    	int count = palavras.length();
    	int random = (int) (Math.random() * count);
    	
		objetivo = palavras.getString(random);
		int length = objetivo.length();
		int meio = length / 2;
		String objetivo1 = objetivo.substring(0, meio);
		String objetivo2 = objetivo.substring(meio, length);
		primeira.setText(objetivo1.toUpperCase());
		segunda.setText(objetivo2.toUpperCase());
		palavra.setText("");
		
		Random randomGenerator = new Random();

		int firstNumber = showRandomInteger(100, 999, randomGenerator);
		int secondNumber = showRandomInteger(100, 999, randomGenerator);
		
		primeiroNum.setText(firstNumber + "+");
		segundoNum.setText("+" + secondNumber);
		
		int padding = 0;
		if (meio > 2)
			padding += 12;
		
		if (meio > 3)
			padding += 12;
		
		if (meio > 4)
			padding += 12;
		
		primeira.setPadding(0, 0, padding, 0);
		segunda.setPadding(padding, 0, 0, 0);
		primeiroNum.setPadding(0, 0, padding, 0);
		segundoNum.setPadding(padding, 0, 0, 0);
	}
    
    private static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}

		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;

		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		return (int) (fraction + aStart);
	}
}
