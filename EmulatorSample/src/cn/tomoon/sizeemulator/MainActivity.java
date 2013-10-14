package cn.tomoon.sizeemulator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.AlteredCharSequence;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tomoon.sdk.Emulator;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.dialog).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				final Dialog dlg = builder
						.setTitle(R.string.app_name)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).setNegativeButton("Cancel", null).create();

				dlg.show();

				Emulator.configure(dlg.getWindow());

			}
		});

		findViewById(R.id.toast).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast toast = Toast.makeText(MainActivity.this,
						"Toast!", Toast.LENGTH_SHORT);
				toast.show();
			}
		});

		Emulator.configure(getWindow());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
