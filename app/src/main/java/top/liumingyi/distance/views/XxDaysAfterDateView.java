package top.liumingyi.distance.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import java.util.Calendar;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.R;

/**
 * xx年xx月xx日 之后x天 是 xx年xx月xx日
 * Created by liumingyi on 2018/4/17.
 */

public class XxDaysAfterDateView extends ConstraintLayout {

  TextView targetDateTv;

  int year;
  int month;
  int date;
  int intentionDay;

  public XxDaysAfterDateView(Context context) {
    this(context, null);
  }

  public XxDaysAfterDateView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public XxDaysAfterDateView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (layoutInflater == null) {
      throw new RuntimeException("Error: can not get layout inflater service");
    }
    layoutInflater.inflate(R.layout.view_xx_days_after_date, this, true);
  }

  @SuppressLint("CheckResult") @Override protected void onFinishInflate() {
    super.onFinishInflate();
    final EditText yearEdt = findViewById(R.id.year_edt);
    EditText monthEdt = findViewById(R.id.month_edt);
    EditText dayEdt = findViewById(R.id.day_edt);
    EditText intentionDayEdt = findViewById(R.id.intention_days_edt);
    targetDateTv = findViewById(R.id.target_date_tv);
    yearEdt.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {

      }
    });
    RxTextView.textChanges(yearEdt).subscribe(charSequence -> year = parseToInt(charSequence));
    RxTextView.textChanges(monthEdt).subscribe(charSequence -> month = parseToInt(charSequence));
    RxTextView.textChanges(dayEdt).subscribe(charSequence -> date = parseToInt(charSequence));
    RxTextView.textChanges(intentionDayEdt)
        .subscribe(charSequence -> intentionDay = parseToInt(charSequence));
  }

  private int parseToInt(CharSequence charSequence) {
    String day = charSequence.toString();
    if ("".equals(day)) {
      return 0;
    }
    return Integer.valueOf(day);
  }

  public void calculate() {
    if (month == 0 || date == 0) {
      return;
    }
    Calendar start = Calendar.getInstance();
    start.set(year, month - 1, date);
    Calendar target = TimeUtils.getTargetCalendar(start, intentionDay);
    targetDateTv.setText(TimeUtils.dateFormat(target));
  }
}
