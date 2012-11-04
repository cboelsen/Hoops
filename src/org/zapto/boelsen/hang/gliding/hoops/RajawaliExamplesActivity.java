package org.zapto.boelsen.hang.gliding.hoops;

import java.util.Arrays;
import java.util.List;

import org.zapto.boelsen.hang.gliding.hoops.R;
import org.zapto.boelsen.hang.gliding.hoops.ui.ExamplesAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;


public class RajawaliExamplesActivity extends ListActivity {
	private ExampleItem[] mItems = { 
            new ExampleItem("00. Hang Gliding Hoops", HoopsActivity.class) };

	public void onCreate(Bundle savedInstanceState) {
		String[] strings = new String[mItems.length];
		for (int i = 0; i < mItems.length; i++) {
			strings[i] = mItems[i].title;
		}
		List<String> list = Arrays.asList(strings);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setListAdapter(new ExamplesAdapter(this, list));

		TextView linkView = (TextView) findViewById(R.id.textView1);
		linkView.setPadding(10, 0, 10, 20);
		linkView.setTextSize(20);
		linkView.setText(getString(R.string.github_url));
		linkView.setTextColor(0xaaffffff);
		linkView.setLinkTextColor(0xaaffffff);

		Linkify.addLinks(linkView, Linkify.WEB_URLS);

		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		linkView.setTypeface(font);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		startActivity(new Intent(this, mItems[position].exampleClass));
	}

	class ExampleItem {
		public String title;
		public Class<?> exampleClass;

		public ExampleItem(String title, Class<?> exampleClass) {
			this.title = title;
			this.exampleClass = exampleClass;
		}
	}
}