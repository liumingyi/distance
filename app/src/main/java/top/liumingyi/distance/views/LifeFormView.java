package top.liumingyi.distance.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import top.liumingyi.distance.R;

/**
 * 就是一堆小方格子，代表人的一辈子
 * Created by liumingyi on 2018/4/4.
 */

public class LifeFormView extends View {

  private static final int STROKE_WIDTH = 2;
  private static final float INSTRUCTION_POINT_SIZE = 5;

  private LifeCounter lifeCounter = new LifeCounter();

  Paint borderPaint;
  Paint greenPaint;
  Paint redPaint;
  Paint textPaint;
  Paint grayPaint;

  RectF borderRect = new RectF();
  RectF progressRect = new RectF();
  RectF endRect = new RectF();

  private int padding;

  private int column;
  private int row;

  private float cellWidth;

  private float width;
  private float height;

  private float textHeight;

  private String instruction;
  private Rect textBounds = new Rect();

  private int instructionPointSize;
  private String endStr;
  private String nextLife;

  public LifeFormView(Context context) {
    this(context, null);
  }

  public LifeFormView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LifeFormView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    borderPaint.setColor(Color.BLACK);
    borderPaint.setStyle(Paint.Style.STROKE);
    borderPaint.setStrokeWidth(dip2px(context, STROKE_WIDTH));

    greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    greenPaint.setColor(Color.parseColor("#66d9a9"));

    redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    redPaint.setColor(Color.RED);

    grayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    grayPaint.setColor(Color.parseColor("#CCCCCC"));

    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    textPaint.setColor(Color.GRAY);
    textPaint.setTextSize(dip2px(context, 11));
    textHeight = Math.abs(textPaint.getFontMetrics().ascent);

    padding = dip2px(context, 3);
    borderRect.left = padding;
    borderRect.top = padding;

    instruction = context.getString(R.string.life_form_instruction);
    instructionPointSize = dip2px(context, INSTRUCTION_POINT_SIZE);
    endStr = context.getString(R.string.end);
    nextLife = context.getString(R.string.next_life);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    width = MeasureSpec.getSize(widthMeasureSpec) - padding - padding;
    float cellHeight = cellWidth = width / column;
    height = cellHeight * row;
    borderRect.right = width + padding;
    borderRect.bottom = height + padding;
    setMeasuredDimension((int) width, (int) (height + padding + padding + padding + textHeight));
  }

  @Override protected void onDraw(Canvas canvas) {
    // 绘制渡过的时间
    drawProgress(canvas);

    // 绘制结束点
    drawEndCell(canvas);

    // 将结束点后的表格染灰
    drawUselessCells(canvas);

    // 绘制表格
    drawForm(canvas);

    // 绘制表格说明
    drawInstructionsText(canvas);
  }

  private void drawInstructionsText(Canvas canvas) {
    // 表格规格说明
    float x = padding + width * 0.2f;
    float y = height + padding + padding + textHeight;
    String instr = String.format(instruction, column, row, lifeCounter.getScale());
    canvas.drawText(instr, x, y, textPaint);

    // 红色图标
    textPaint.getTextBounds(instr, 0, instr.length(), textBounds);
    float pointLeft = x + textBounds.width() + instructionPointSize;
    float pointTop =
        height + padding + padding + (textPaint.getFontSpacing() - instructionPointSize) / 2;
    float pointRight = pointLeft + instructionPointSize;
    float pointBottom = pointTop + instructionPointSize;
    canvas.drawRect(pointLeft, pointTop, pointRight, pointBottom, redPaint);

    // 红色图标说明
    canvas.drawText(endStr, pointRight + instructionPointSize, y, textPaint);

    // 灰色图标
    if (lifeCounter.noExtraCell()) {
      return;
    }
    textPaint.getTextBounds(endStr, 0, endStr.length(), textBounds);
    float pointLeft2 =
        pointRight + instructionPointSize + textBounds.width() + instructionPointSize;
    float pointRight2 = pointLeft2 + instructionPointSize;
    canvas.drawRect(pointLeft2, pointTop, pointRight2, pointBottom, grayPaint);
    // 灰色图标说明
    canvas.drawText(nextLife, pointRight2 + instructionPointSize, y, textPaint);
  }

  private void drawUselessCells(Canvas canvas) {
    if (lifeCounter.noExtraCell()) {
      return;
    }
    LifeCounter.Cell endCell = lifeCounter.getEndCell();
    int columnNum = endCell.columnIndex + 1;
    int rowNum = endCell.rowIndex + 1;
    float left = padding + columnNum * cellWidth;
    float top = padding + (rowNum - 1) * cellWidth;
    canvas.drawRect(left, top, padding + width, top + cellWidth, grayPaint);
  }

  private void drawEndCell(Canvas canvas) {
    LifeCounter.Cell endCell = lifeCounter.getEndCell();
    if (endCell == null) {
      return;
    }
    endRect.left = padding + cellWidth * endCell.columnIndex;
    endRect.top = padding + cellWidth * endCell.rowIndex;
    endRect.right = endRect.left + cellWidth;
    endRect.bottom = endRect.top + cellWidth;
    canvas.drawRect(endRect, redPaint);
  }

  private void drawProgress(Canvas canvas) {
    int rowNum = lifeCounter.getCompleteRows();
    for (int i = 0; i < rowNum; i++) {
      drawLine(canvas, i);
    }
    int cellNum = lifeCounter.getCellsInLastRow();
    for (int i = 0; i < cellNum; i++) {
      drawCell(canvas, rowNum, i);
    }
  }

  private void drawCell(Canvas canvas, int rowNum, int columnNum) {
    progressRect.left = padding + cellWidth * columnNum;
    progressRect.top = padding + cellWidth * rowNum;
    progressRect.right = progressRect.left + cellWidth;
    progressRect.bottom = progressRect.top + cellWidth;
    canvas.drawRect(progressRect, greenPaint);
  }

  private void drawLine(Canvas canvas, int rowNum) {
    progressRect.left = padding;
    progressRect.top = padding + rowNum * cellWidth;
    progressRect.right = progressRect.left + width;
    progressRect.bottom = progressRect.top + cellWidth;
    canvas.drawRect(progressRect, greenPaint);
  }

  private void drawForm(Canvas canvas) {
    // 绘制边框
    drawBorder(canvas);
    // 绘制分格线
    drawDivideLines(canvas);
  }

  private void drawDivideLines(Canvas canvas) {
    for (int i = 1; i < column; i++) {
      canvas.drawLine(cellWidth * i + padding, padding, cellWidth * i + padding, height + padding,
          borderPaint);
    }
    for (int i = 1; i < row; i++) {
      canvas.drawLine(padding, cellWidth * i + padding, padding + width, cellWidth * i + padding,
          borderPaint);
    }
  }

  private void drawBorder(Canvas canvas) {
    canvas.drawRect(borderRect, borderPaint);
  }

  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public void setTotalYear(int totalYear) {
    lifeCounter.setTotalYear(totalYear);
    column = lifeCounter.getFormColumnNum();
    row = lifeCounter.getFormRowNum();
  }

  public void setProgressYear(int progressYear) {
    lifeCounter.setProgressYear(progressYear);
  }
}
