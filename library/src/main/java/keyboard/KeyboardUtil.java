package keyboard;


import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.List;

public class KeyboardUtil
{
  private KeyboardView keyboardView;
  private Keyboard k1;
  private Keyboard k2;	
  private Keyboard k3;
  private Keyboard k4;
  private Keyboard k5;
  private boolean isupper = true;
  private EditText ed;
  private IClick onClick;
  private AnimationController animationController = null;

  private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener()
  {
    public void swipeUp()
    {
    }

    public void swipeRight()
    {
    }

    public void swipeLeft()
    {
    }

    public void swipeDown()
    {
    }

    public void onText(CharSequence text)
    {
    }

    public void onRelease(int primaryCode)
    {
    }

    public void onPress(int primaryCode)
    {
    }

    public void onKey(int primaryCode, int[] keyCodes) {
      Editable editable = KeyboardUtil.this.ed.getText();
      int start = KeyboardUtil.this.ed.getSelectionStart();
      String[] sh = {"?","!","/","…","@",":",";","&","^","~","'","\"","(",")","*","#","%","+","-","_","=","\\","•","¥","$","`","×","÷",",","¡","<",">","[","]","{","}","."};
      int shcode = primaryCode - 197;
      if ((197 <= primaryCode) && (primaryCode <= 233)) {
        primaryCode = 300;
      }
      switch (primaryCode) {
      case -3:
        KeyboardUtil.this.hideKeyboard();
        if (KeyboardUtil.this.onClick == null) break;
        KeyboardUtil.this.onClick.OnKeyCompleteClick();

        break;
      case -5:
        if ((editable == null) || (editable.length() <= 0) || 
          (start <= 0)) break;
        editable.delete(start - 1, start);

        break;
      case -1:
        KeyboardUtil.this.changeKey();
        KeyboardUtil.this.keyboardView.setKeyboard(KeyboardUtil.this.k1);
        break;
      case -7:
        KeyboardUtil.this.keyboardView.setKeyboard(KeyboardUtil.this.k1);
        if (KeyboardUtil.this.isupper) break;
        KeyboardUtil.this.changeKey();
        break;
      case -8:
        KeyboardUtil.this.keyboardView.setKeyboard(KeyboardUtil.this.k2);
        break;
      case -9:
        KeyboardUtil.this.keyboardView.setKeyboard(KeyboardUtil.this.k3);
        break;
      case 57419:
        if (start <= 0) break;
        KeyboardUtil.this.ed.setSelection(start - 1);

        break;
      case 57421:
        if (start >= KeyboardUtil.this.ed.length()) break;
        KeyboardUtil.this.ed.setSelection(start + 1);

        break;
      case 300:
    	  editable.insert(start, sh[shcode]);
//        String ss = Character.toString(sh[shcode]) + 
//          editable.toString().replaceAll("[^x00-xff]*", "");
//
//        KeyboardUtil.this.ed.setText(ss);
//        KeyboardUtil.this.ed.setSelection(ss.length());
        break;
      case -10:
    	  break;
      default:
        editable.insert(start, Character.toString((char)primaryCode));
      }
    }
  };

  public KeyboardUtil(Activity ctx, EditText edit, KeyboardType type, int keyId)
  {
    this.ed = edit;
    this.keyboardView = ((KeyboardView)ctx.findViewById(keyId));
    if (type == KeyboardType.CarNumber) {
      this.k1 = new Keyboard(ctx,MResource.getIdByName(ctx,"xml","qwerty"));
      this.k2 = new Keyboard(ctx, MResource.getIdByName(ctx,"xml","symbols"));
      this.k3 = new Keyboard(ctx, MResource.getIdByName(ctx,"xml","capital"));
      this.keyboardView.setKeyboard(this.k1);
    } else if (type == KeyboardType.IDCard) {
    	 this.k1 = new Keyboard(ctx, MResource.getIdByName(ctx, "xml", "qwerty"));
         this.k2 = new Keyboard(ctx, MResource.getIdByName(ctx, "xml", "symbols"));
         this.k3 = new Keyboard(ctx, MResource.getIdByName(ctx, "xml", "capital"));
         this.keyboardView.setKeyboard(this.k2);
    }else if(type==KeyboardType.BankCard){
    	this.k5=new Keyboard(ctx, MResource.getIdByName(ctx, "xml", "bankcard"));
    	this.keyboardView.setKeyboard(this.k5);
    }
    this.keyboardView.setEnabled(true);
    this.keyboardView.setPreviewEnabled(false);
    this.keyboardView.setOnKeyboardActionListener(this.listener);
    this.animationController = new AnimationController();

    hideInputMod(edit, ctx);
  }
  public KeyboardUtil(Activity ctx, EditText edit, boolean issfzh) {
  }
  
  

  public void setOnMyClickListener(IClick _iClick) {
    this.onClick = _iClick;
  }
  public void hideInputMod(EditText et, Activity ctx) {
    if (Build.VERSION.SDK_INT <= 10) {
      et.setInputType(0);
    } else {
      ctx.getWindow().setSoftInputMode(3);
      try {
        Class cls = EditText.class;
        Method[] methods = cls.getMethods();
        Method setShowSoftInputOnFocus = null;
        for (int idx = 0; idx < methods.length; idx++)
        {
          if (methods[idx].getName()
            .equals("setShowSoftInputOnFocus")) {
            setShowSoftInputOnFocus = methods[idx];
          }
          else if (methods[idx].getName().equals(
            "setSoftInputShownOnFocus")) {
            setShowSoftInputOnFocus = methods[idx];
          }
        }
        if (setShowSoftInputOnFocus != null) {
          setShowSoftInputOnFocus.setAccessible(true);
          setShowSoftInputOnFocus.invoke(et, 
            new Object[] { Boolean.valueOf(false) });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isVisible(){
    if(this.keyboardView.getVisibility()==View.VISIBLE){
      return true;
    }else{
      return false;
    }
  }

  private void changeKey() {
    List<Key> keylist = this.k1.getKeys();
    if (!this.isupper) {
      this.isupper = true;
      for (Key key : keylist)
        if ((key.label != null) && (isword(key.label.toString()))) {
          key.label = key.label.toString().toLowerCase();
          key.codes[0] += 32;
        }
    } else {
      this.isupper = false;
      for (Key key : keylist)
        if ((key.label != null) && (isword(key.label.toString()))) {
          key.label = key.label.toString().toUpperCase();
          key.codes[0] -= 32;
        }
    }
  }

  public void showKeyboard() {
    int visibility = this.keyboardView.getVisibility();
    if ((visibility == 8) || (visibility == 4)) {
      this.animationController.fadeIn(this.keyboardView, 500L, 50L);
      this.keyboardView.setVisibility(View.VISIBLE);
    }
  }

  public void hideKeyboard() {
    int visibility = this.keyboardView.getVisibility();
    if (visibility == 0) {
      this.animationController.fadeOut(this.keyboardView, 500L, 50L);
      this.keyboardView.setVisibility(View.GONE);
    }
  }

  private boolean isword(String str) {
    String wordstr = "abcdefghijklmnopqrstuvwxyz";

    return wordstr.indexOf(str.toLowerCase()) > -1;
  }
  public static abstract interface IClick {
    public abstract void OnKeyCompleteClick();
  }
  public static enum KeyboardType {
    CarNumber, IDCard,BankCard;
  }
}