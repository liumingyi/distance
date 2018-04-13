package top.liumingyi.distance.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import java.util.Calendar;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.R;

/**
 * 用于计算我的第xxx天是xx年xx月xx日的控件
 * Created by liumingyi on 2018/4/16.
 */

public class MyXxDateView extends ConstraintLayout {

  private TextView targetDateTv;
  private Calendar birthday;
  private int intentionDays;

  public MyXxDateView(Context context) {
    this(context, null);
  }

  public MyXxDateView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MyXxDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (layoutInflater == null) {
      throw new RuntimeException("Error: can not get layout inflater service");
    }
    layoutInflater.inflate(R.layout.view_my_xx_date, this, true);
  }

  @SuppressLint("CheckResult") @Override protected void onFinishInflate() {
    super.onFinishInflate();
    targetDateTv = findViewById(R.id.my_target_date_tv);
    EditText myDaysEdit = findViewById(R.id.my_xx_days_edt);
    RxTextView.textChanges(myDaysEdit).subscribe(charSequence -> {
      String number = charSequence.toString();
      if ("".equals(number)) {
        return;
      }
      intentionDays = Integer.valueOf(number);
    });
  }

  public void setBirthday(Calendar birthday) {
    this.birthday = birthday;
  }

  public void calculate() {
    Calendar targetCalendar = TimeUtils.getTargetCalendar(birthday, intentionDays);
    if (targetCalendar == null) {
      return;
    }
    targetDateTv.setText(TimeUtils.dateFormat(targetCalendar));
  }
}
