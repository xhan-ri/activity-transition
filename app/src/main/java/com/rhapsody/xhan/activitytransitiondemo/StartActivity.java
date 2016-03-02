package com.rhapsody.xhan.activitytransitiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

	ImageView imageView;
	TransitionSet exitTransitionSet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		imageView = (ImageView)findViewById(R.id.start_image);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				exitTransitionSet = new TransitionSet(StartActivity.this, null);
				exitTransitionSet
						.addTransition(new ChangeBounds())
						.addTransition(new RotateTransition(StartActivity.this).startAngle(-45).endAngle(0).exiting(true));
				exitTransitionSet.setDuration(getResources().getInteger(R.integer.transition_duration));
				getWindow().setSharedElementExitTransition(exitTransitionSet);

				Intent startIntent = new Intent(StartActivity.this, EndActivity.class);
				ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(StartActivity.this, imageView, imageView.getTransitionName());
				startActivity(startIntent, activityOptions.toBundle());

				exitTransitionSet = new TransitionSet(StartActivity.this, null);
				exitTransitionSet
						.addTransition(new ChangeBounds())
						.addTransition(new RotateTransition(StartActivity.this).startAngle(0).endAngle(-45).exiting(false));
				exitTransitionSet.setDuration(getResources().getInteger(R.integer.transition_duration));
				getWindow().setSharedElementExitTransition(exitTransitionSet);
			}
		});
		getWindow().setExitTransition(null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
