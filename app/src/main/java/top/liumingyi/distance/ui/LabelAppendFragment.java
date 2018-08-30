package top.liumingyi.distance.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.orhanobut.logger.Logger;
import java.util.Calendar;
import top.liumingyi.distance.R;
import top.liumingyi.distance.viewmodels.LabelAppendViewModel;
import top.liumingyi.distance.views.PickerView;
import top.liumingyi.distance.views.tagview.TagLayout;
import top.liumingyi.tang.base.BaseViewModelFragment;
import top.liumingyi.tang.views.TRxView;

/**
 * 添加事件(倒计时)页面
 */
public class LabelAppendFragment extends BaseViewModelFragment<LabelAppendViewModel> {

  @BindView(R.id.tag_layout) TagLayout tagLayout;
  @BindView(R.id.picker_view) PickerView pickerView;
  @BindView(R.id.confirm_btn) Button confirmBtn;

  public static LabelAppendFragment newInstance() {
    return new LabelAppendFragment();
  }

  @Override protected LabelAppendViewModel initInjector() {
    return new LabelAppendViewModel();
  }

  @Override protected int getLayoutResID() {
    return R.layout.fragment_label_append;
  }

  @Override protected void initVariables() {

  }

  @Override protected void initView(Bundle savedInstanceState) {
    initKeywords();
    initListeners();
  }

  @SuppressLint("CheckResult") private void initListeners() {
    TRxView.clicks(confirmBtn).subscribe((Object o) -> {
      Logger.d("Btn Click");

      showConfirmDialog(getContext());
    });
    tagLayout.setOnItemClickListener((position, text) -> {
      tagLayout.removeItem(position);
      viewModel.clickKeyword(text);
    });
  }

  @SuppressLint("CheckResult") private void showConfirmDialog(Context context) {
    Calendar selectCalendar = pickerView.getSelectCalendar();
    @SuppressLint("InflateParams") TextView nameTv =
        (TextView) getLayoutInflater().inflate(R.layout.input_label_name_dialog, null);
    RxTextView.textChanges(nameTv).subscribe(name -> {
      viewModel.setLabelName(name);
    });
    new AlertDialog.Builder(context).setTitle("请输入标签名称")
        .setView(nameTv)
        .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
          viewModel.confirm(selectCalendar);
        })
        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
        })
        .setCancelable(true)
        .create()
        .show();
  }

  private void initKeywords() {
    tagLayout.setData(viewModel.getKeywords());
  }

  @Override protected void dataBinding() {

  }
}
