package top.liumingyi.distance.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.functions.Consumer;
import java.util.Calendar;
import top.liumingyi.distance.R;
import top.liumingyi.tang.utils.TimeUtils;

public class DaysBetweenTwoDatesView extends ConstraintLayout {

  private int startYear;
  private int startMonth;
  private int startDay;
  private int endYear;
  private int endMonth;
  private int endDay;
  private TextView apartDaysTv;

  public DaysBetweenTwoDatesView(Context context) {
    this(context, null);
  }

  public DaysBetweenTwoDatesView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DaysBetweenTwoDatesView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (inflater == null) {
      return;
    }
    inflater.inflate(R.layout.view_days_between_two_dates, this, true);
  }

  @SuppressLint("CheckResult") @Override protected void onFinishInflate() {
    super.onFinishInflate();
    apartDaysTv = findViewById(R.id.apart_days_tv);
    EditText yearStartEdt = findViewById(R.id.year_start_edt);
    EditText monthStartEdt = findViewById(R.id.month_start_edt);
    EditText dayStartEdt = findViewById(R.id.day_start_edt);
    EditText yearEndEdt = findViewById(R.id.year_end_edt);
    EditText monthEndEdt = findViewById(R.id.month_end_edt);
    EditText dayEndEdt = findViewById(R.id.day_end_edt);
    RxTextView.textChanges(yearStartEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        startYear = parseToInt(charSequence);
      }
    });
    RxTextView.textChanges(monthStartEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        startMonth = parseToInt(charSequence);
      }
    });
    RxTextView.textChanges(dayStartEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        startDay = parseToInt(charSequence);
      }
    });
    RxTextView.textChanges(yearEndEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        endYear = parseToInt(charSequence);
      }
    });
    RxTextView.textChanges(monthEndEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        endMonth = parseToInt(charSequence);
      }
    });
    RxTextView.textChanges(dayEndEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        endDay = parseToInt(charSequence);
      }
    });
  }

  private int parseToInt(CharSequence charSequence) {
    String day = charSequence.toString();
    if ("".equals(day)) {
      return 0;
    }
    return Integer.valueOf(day);
  }

  public void calculate() {
    Calendar start = Calendar.getInstance();
    start.set(startYear, startMonth, startDay);
    Calendar end = Calendar.getInstance();
    end.set(endYear, endMonth, endDay);
    long apartDays = TimeUtils.calculateApartDays(start, end);
    apartDaysTv.setText(String.valueOf(apartDays));
  }
}
