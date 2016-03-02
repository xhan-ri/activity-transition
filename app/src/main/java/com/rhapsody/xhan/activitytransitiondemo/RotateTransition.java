package com.rhapsody.xhan.activitytransitiondemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RotateTransition extends Transition {
	private static final String LOG_TAG = "RotateTransition";
	private static final String PROP_ROTATION = "org.xiaofeng.playground:RotateTransition:rotation";
	private List<String> transitionNameList = new LinkedList<>();
	private float startAngle, endAngle;

	public RotateTransition(String... transitionNames) {
		transitionNameList.addAll(Arrays.asList(transitionNames));
	}

	@Override
	public void captureStartValues(TransitionValues transitionValues) {
		if (TextUtils.isEmpty(transitionValues.view.getTransitionName()) || !transitionNameList.contains(transitionValues.view.getTransitionName())) {
			return;
		}
		Log.i(LOG_TAG, ".");
		Log.i(LOG_TAG, "Capturing start value for view = " + transitionValues.view.getTransitionName());
		Log.i(LOG_TAG, "Before capture: " + dumpMap(transitionValues.values));
		if (transitionValues.view.getTransitionName().equals("animated_image2")) {
			captureValues(transitionValues, startAngle);
		} else {
			captureValues(transitionValues, transitionValues.view.getRotation());
		}

	}

	@Override
	public void captureEndValues(TransitionValues transitionValues) {
		if (TextUtils.isEmpty(transitionValues.view.getTransitionName()) || !transitionNameList.contains(transitionValues.view.getTransitionName())) {
			return;
		}
		Log.i(LOG_TAG, ".");
		Log.i(LOG_TAG, "Capturing end value for view = " + transitionValues.view.getTransitionName());
		Log.i(LOG_TAG, "Before capture: " + dumpMap(transitionValues.values));
		if (transitionValues.view.getTransitionName().equals("animated_image2")) {
			captureValues(transitionValues, endAngle);
		} else {
			captureValues(transitionValues, transitionValues.view.getRotation());
		}
	}

	private void captureValues(TransitionValues transitionValues, float rotation) {
		final View view = transitionValues.view;
		transitionValues.values.put(getPropKey(PROP_ROTATION, view), rotation);
		Log.i(LOG_TAG, "Captured values: " + dumpMap(transitionValues.values));
	}

	@Override
	public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
		if (startValues == null || endValues == null) {
			return null;
		}
		Log.i(LOG_TAG, "Creating animator for view = " + startValues.view.getTransitionName());
		final View view = endValues.view;
		final float startRotation = (float)startValues.values.get(getPropKey(PROP_ROTATION, view));
		final float endRotation = (float)endValues.values.get(getPropKey(PROP_ROTATION, view));
		Log.i(LOG_TAG, "start rotation = " + startRotation + ", endRotation = " + endRotation);
		// no animation needed.
		if (startRotation == endRotation) {
			Log.w(LOG_TAG, "No animation for view");
			return null;
		}

		ValueAnimator rotateAnimator = new ValueAnimator();
		rotateAnimator.setFloatValues(startRotation, endRotation);
		rotateAnimator.setTarget(view);
		rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				view.setRotation((float)animation.getAnimatedValue());
			}
		});

		return rotateAnimator;
	}

	private String getPropKey(String prop, View view) {
		return prop + ":" + view.getTransitionName();
	}

	private static String dumpMap(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key)).append(", ");
		}
		return sb.toString();
	}

	public RotateTransition setStartAngle(float startAngle) {
		this.startAngle = startAngle;
		return this;
	}

	public RotateTransition setEndAngle(float endAngle) {
		this.endAngle = endAngle;
		return this;
	}
}
