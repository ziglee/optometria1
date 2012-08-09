package net.cassiolandim.yuri1;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView palavraImage;
	private EditText palavra;
	private Button proxima;
	private Button enviar;
	private Button instrucoes;
	private String objetivo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		palavraImage = (ImageView) findViewById(R.id.palavraImage);
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
					Toast.makeText(MainActivity.this, "Parabéns!", Toast.LENGTH_SHORT).show();
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
		try {
			palavraImage.setImageBitmap(getBitmapFromAsset(objetivo + ".jpg"));
		} catch (IOException e) {
			Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		palavra.setText("");
	}

	private Bitmap getBitmapFromAsset(String strName) throws IOException {
		AssetManager assetManager = getAssets();
		InputStream istr = assetManager.open(strName);
		return BitmapFactory.decodeStream(istr);
	}
}
