package view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import gxy.library.R;


public class LoadingDialog extends Dialog {
	private String test;
	private TextView contentTv;
	public LoadingDialog(Context context, boolean cancelable,
                         OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public LoadingDialog(Context context, int theme, String test) {
		super(context,  R.style.CursorDialogNotFloatNotTitleTheme);
		this.test=test;
	}

	public LoadingDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_loading);
		contentTv=(TextView) findViewById(R.id.content);
		contentTv.setText(test==null?"loading":test);
//		setCancelable(false);
	}
	
	
}
