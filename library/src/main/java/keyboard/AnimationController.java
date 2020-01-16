package keyboard;


import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationController
{
  public final int rela1 = 1;
  public final int rela2 = 2;

  public final int Default = -1;
  public final int Linear = 0;
  public final int Accelerate = 1;
  public final int Decelerate = 2;
  public final int AccelerateDecelerate = 3;
  public final int Bounce = 4;
  public final int Overshoot = 5;
  public final int Anticipate = 6;
  public final int AnticipateOvershoot = 7;

  private void setEffect(Animation animation, int interpolatorType, long durationMillis, long delayMillis)
  {
    switch (interpolatorType) {
    case 0:
      animation.setInterpolator(new LinearInterpolator());
      break;
    case 1:
      animation.setInterpolator(new AccelerateInterpolator());
      break;
    case 2:
      animation.setInterpolator(new DecelerateInterpolator());
      break;
    case 3:
      animation.setInterpolator(new AccelerateDecelerateInterpolator());
      break;
    case 4:
      animation.setInterpolator(new BounceInterpolator());
      break;
    case 5:
      animation.setInterpolator(new OvershootInterpolator());
      break;
    case 6:
      animation.setInterpolator(new AnticipateInterpolator());
      break;
    case 7:
      animation.setInterpolator(new AnticipateOvershootInterpolator());
    }

    animation.setDuration(durationMillis);
    animation.setStartOffset(delayMillis);
  }

  private void baseIn(View view, Animation animation, long durationMillis, long delayMillis)
  {
    setEffect(animation, -1, durationMillis, delayMillis);
    view.setVisibility(0);
    view.startAnimation(animation);
  }

  private void baseOut(View view, Animation animation, long durationMillis, long delayMillis)
  {
    setEffect(animation, -1, durationMillis, delayMillis);
    animation.setAnimationListener(new MyAnimationListener(view));
    view.startAnimation(animation);
  }

  public void show(View view) {
    view.setVisibility(0);
  }

  public void hide(View view) {
    view.setVisibility(8);
  }

  public void transparent(View view) {
    view.setVisibility(4);
  }

  public void fadeIn(View view, long durationMillis, long delayMillis) {
    AlphaAnimation animation = new AlphaAnimation(0.0F, 1.0F);
    baseIn(view, animation, durationMillis, delayMillis);
  }

  public void fadeOut(View view, long durationMillis, long delayMillis) {
    AlphaAnimation animation = new AlphaAnimation(1.0F, 0.0F);
    baseOut(view, animation, durationMillis, delayMillis);
  }

  public void slideIn(View view, long durationMillis, long delayMillis) {
    TranslateAnimation animation = new TranslateAnimation(2, 1.0F, 2, 0.0F, 
      2, 0.0F, 2, 0.0F);
    baseIn(view, animation, durationMillis, delayMillis);
  }

  public void slideOut(View view, long durationMillis, long delayMillis) {
    TranslateAnimation animation = new TranslateAnimation(2, 0.0F, 2, 
      -1.0F, 2, 0.0F, 2, 0.0F);
    baseOut(view, animation, durationMillis, delayMillis);
  }

  public void scaleIn(View view, long durationMillis, long delayMillis) {
    ScaleAnimation animation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 
      2, 0.5F, 2, 0.5F);
    baseIn(view, animation, durationMillis, delayMillis);
  }

  public void scaleOut(View view, long durationMillis, long delayMillis) {
    ScaleAnimation animation = new ScaleAnimation(1.0F, 0.0F, 1.0F, 0.0F, 
      2, 0.5F, 2, 0.5F);
    baseOut(view, animation, durationMillis, delayMillis);
  }

  public void rotateIn(View view, long durationMillis, long delayMillis) {
    RotateAnimation animation = new RotateAnimation(-90.0F, 0.0F, 1, 0.0F, 
      1, 1.0F);
    baseIn(view, animation, durationMillis, delayMillis);
  }

  public void rotateOut(View view, long durationMillis, long delayMillis) {
    RotateAnimation animation = new RotateAnimation(0.0F, 90.0F, 1, 0.0F, 
      1, 1.0F);
    baseOut(view, animation, durationMillis, delayMillis);
  }

  public void scaleRotateIn(View view, long durationMillis, long delayMillis) {
    ScaleAnimation animation1 = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 
      1, 0.5F, 1, 0.5F);
    RotateAnimation animation2 = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 
      1, 0.5F);
    AnimationSet animation = new AnimationSet(false);
    animation.addAnimation(animation1);
    animation.addAnimation(animation2);
    baseIn(view, animation, durationMillis, delayMillis);
  }

  public void scaleRotateOut(View view, long durationMillis, long delayMillis) {
    ScaleAnimation animation1 = new ScaleAnimation(1.0F, 0.0F, 1.0F, 0.0F, 
      1, 0.5F, 1, 0.5F);
    RotateAnimation animation2 = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 
      1, 0.5F);
    AnimationSet animation = new AnimationSet(false);
    animation.addAnimation(animation1);
    animation.addAnimation(animation2);
    baseOut(view, animation, durationMillis, delayMillis);
  }

  public void slideFadeIn(View view, long durationMillis, long delayMillis) {
    TranslateAnimation animation1 = new TranslateAnimation(2, 1.0F, 2, 
      0.0F, 2, 0.0F, 2, 0.0F);
    AlphaAnimation animation2 = new AlphaAnimation(0.0F, 1.0F);
    AnimationSet animation = new AnimationSet(false);
    animation.addAnimation(animation1);
    animation.addAnimation(animation2);
    baseIn(view, animation, durationMillis, delayMillis);
  }

  public void slideFadeOut(View view, long durationMillis, long delayMillis) {
    TranslateAnimation animation1 = new TranslateAnimation(2, 0.0F, 2, 
      -1.0F, 2, 0.0F, 2, 0.0F);
    AlphaAnimation animation2 = new AlphaAnimation(1.0F, 0.0F);
    AnimationSet animation = new AnimationSet(false);
    animation.addAnimation(animation1);
    animation.addAnimation(animation2);
    baseOut(view, animation, durationMillis, delayMillis);
  }
  private class MyAnimationListener implements Animation.AnimationListener {
    private View view;

    public MyAnimationListener(View view) {
      this.view = view;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
      this.view.setVisibility(8);
    }

    public void onAnimationRepeat(Animation animation)
    {
    }
  }
}