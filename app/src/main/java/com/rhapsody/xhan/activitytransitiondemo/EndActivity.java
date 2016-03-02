package com.rhapsody.xhan.activitytransitiondemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class EndActivity extends AppCompatActivity {

	ImageView imageView;
	TransitionSet enterTransitionSet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		getWindow().setEnterTransition(null);
		enterTransitionSet = new TransitionSet(this, null);
		enterTransitionSet
				.addTransition(new ChangeBounds())
//				.addTransition(new ChangeImageTransform())
				.addTransition(new RotateTransition(this).startAngle(0).endAngle(405).purpose("End activity enter"))
		;
		enterTransitionSet.setDuration(getResources().getInteger(R.integer.transition_duration));
		getWindow().setSharedElementEnterTransition(enterTransitionSet);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		imageView = (ImageView)findViewById(R.id.end_image);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onBackPressed() {
		enterTransitionSet = new TransitionSet(this, null);
		enterTransitionSet
				.addTransition(new ChangeBounds())
//				.addTransition(new ChangeImageTransform())
				.addTransition(new RotateTransition(this).startAngle(405).endAngle(0).purpose("End activity exit"))
		;
		enterTransitionSet.setDuration(getResources().getInteger(R.integer.transition_duration));
		getWindow().setSharedElementEnterTransition(enterTransitionSet);
		super.onBackPressed();
	}
}
