package view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gxy.library.R;


/**
 * Created by Administrator on 2017/9/8.
 */

public class DialogUtils {

    private Dialog dialog;
    private EditText editText;

    //带编辑框的dialog
    public void shownDialogEdittext(final Context context) {
        dialog = new Dialog(context, R.style.CursorDialogNotFloatTheme);
        dialog.setContentView(R.layout.dialog_edittext);

        //适应键盘
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        editText = (EditText) dialog.findViewById(R.id.tv_edit);
        //设置只能输入数字和小数点
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        TextView tv_yes = (TextView) dialog.findViewById(R.id.tv_yes);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //关闭软键盘
                hintKbOne(editText, context);

                setDismiss();

            }
        });


        TextView tv_no = (TextView) dialog.findViewById(R.id.tv_no);
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDismiss();
            }
        });
        dialog.show();
    }

    public void setDismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }

    }

    //隐藏软键盘
    public void hintKbOne(EditText etFind, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etFind.getWindowToken(), 0);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------------------
     */


    public void shownDialogProgress(Activity context) {
        dialog = new Dialog(context, R.style.CursorDialogNotFloatTheme);
        dialog.setContentView(R.layout.dailog_progress_layout);

        //设置点击外部关闭对话框
        RelativeLayout rl_layout = (RelativeLayout) dialog.findViewById(R.id.rl_tag);
        rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDismiss();
            }
        });


        dialog.show();

    }

    //常用选择dialog
    public void shownDialog(Context context) {
        dialog = new Dialog(context, R.style.CursorDialogNotFloatTheme);
        dialog.setContentView(R.layout.dialog_hint);

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        TextView tv_yes = (TextView) dialog.findViewById(R.id.tv_submit);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDismiss();

            }
        });


        TextView tv_no = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDismiss();
            }
        });
        dialog.show();
    }


}
