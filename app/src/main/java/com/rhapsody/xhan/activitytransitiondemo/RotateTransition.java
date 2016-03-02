package com.rhapsody.xhan.activitytransitiondemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RotateTransition extends Transition {
	private static final String LOG_TAG = "RotateTransition";
	private static final String PROP_ROTATION = "RotateTransition:rotation";
	private float startAngle, endAngle;
	Context context;
	final String transitionName;
	private String purpose;
	public RotateTransition(Context context) {
		this.context = context;
		transitionName = context.getString(R.string.transition_name);
	}

	@Override
	public void captureStartValues(TransitionValues transitionValues) {
		if (TextUtils.isEmpty(transitionValues.view.getTransitionName())) {
			return;
		}

		Log.i(LOG_TAG, ".");
		Log.i(LOG_TAG, "Capturing start value for view = " + transitionValues.view.getTransitionName() + ", purpose = " + purpose);
		Log.i(LOG_TAG, "Before capture: " + dumpMap(transitionValues.values));
		if (transitionValues.view.getTransitionName().equals(transitionName)) {
			captureValues(transitionValues, startAngle);
		} else {
			captureValues(transitionValues, transitionValues.view.getRotation());
		}

	}

	@Override
	public void captureEndValues(TransitionValues transitionValues) {
		if (TextUtils.isEmpty(transitionValues.view.getTransitionName())) {
			return;
		}
		Log.i(LOG_TAG, ".");
		Log.i(LOG_TAG, "Capturing end value for view = " + transitionValues.view.getTransitionName() + ", purpose = " + purpose);
		Log.i(LOG_TAG, "Before capture: " + dumpMap(transitionValues.values));
		if (transitionValues.view.getTransitionName().equals(transitionName)) {
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
		Log.i(LOG_TAG, "Creating animator for view = " + startValues.view.getTransitionName() + ", purpose = " + purpose);
		final View view = endValues.view;
		final float startRotation = (float)startValues.values.get(getPropKey(PROP_ROTATION, view));
		final float endRotation = (float)endValues.values.get(getPropKey(PROP_ROTATION, view));
		Log.i(LOG_TAG, "startRotation = " + startRotation + ", endRotation = " + endRotation);
		// no animation needed.
		if (startRotation == endRotation) {
			Log.w(LOG_TAG, "No animation for view");
			return null;
		}


		Animator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
		rotateAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				Log.i(LOG_TAG, "Transition animation started, purpose = " + purpose);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Log.i(LOG_TAG, "Transition animation ended");
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

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

	public RotateTransition startAngle(float startAngle) {
		this.startAngle = startAngle;
		return this;
	}

	public RotateTransition endAngle(float endAngle) {
		this.endAngle = endAngle;
		return this;
	}

	public RotateTransition purpose(String purpose) {
		this.purpose = purpose;
		return this;
	}
}
