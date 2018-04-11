package top.liumingyi.distance.views;

import lombok.Getter;

/**
 * {@link LifeFormView} 的绘制计算器
 * Created by liumingyi on 2018/4/8.
 */

class LifeCounter {

  private static final int DEFAULT_SCALE = 1;

  private static final int SPEC_1 = 10 * 10;
  private static final int SPEC_2 = 20 * 20;
  private static final int SPEC_3 = 30 * 30;
  private static final int SPEC_4 = 40 * 40;
  private static final int SPEC_5 = 50 * 50;
  private static final int SPEC_6 = 60 * 60;

  /** 比例尺:每一个格子代表多少年,默认为1年 */
  @Getter private int scale;

  private int totalYear;
  private int progressYear;

  @Getter private int completeRows;
  @Getter private int cellsInLastRow;

  @Getter private int formColumnNum;
  @Getter private int formRowNum;
  @Getter private Cell endCell;

  void setTotalYear(int totalYear) {
    this.totalYear = totalYear;
    this.scale = DEFAULT_SCALE;
    int column = (int) Math.sqrt(calculateSpecification(totalYear));
    int row = calculateTotalRowNum(column, totalYear);
    this.formColumnNum = column;
    this.formRowNum = row;
    this.endCell = generateEndCell(column, row, totalYear);
  }

  private Cell generateEndCell(int column, int row, int totalYear) {
    int endColumn = column - (int) Math.floor((double) (row * column * scale - totalYear) / scale);
    return new Cell(endColumn == 0 ? 0 : endColumn - 1, row == 0 ? 0 : row - 1);
  }

  /**
   * 根据totalYear和列规格计算出需要多少row
   */
  private int calculateTotalRowNum(int column, int totalYear) {
    return (int) Math.ceil((double) totalYear / scale / column);
  }

  /**
   * 根据totalYar计算表格的规格.
   * 支持:10x10,20x20,30x30,40x40,50x50,60x60
   * 最多60x60=3600年.
   *
   * 当 totalYear > 3600年的时候:
   * 每一格表示10年,表格回到(20x20=)400 x 10 = 4000年
   * 当 totalYear > 3万6千年的时候:
   * 每一格子表示100年,表格回到(20x20=)4000 x 10 = 4万年
   * ...以此类推...每一个格子表示的年数即比例尺{@link #scale}
   */
  private int calculateSpecification(int totalYear) {
    int spec;
    if (totalYear <= SPEC_1 * scale) {
      spec = SPEC_1;
    } else if (totalYear <= SPEC_2 * scale) {
      spec = SPEC_2;
    } else if (totalYear <= SPEC_3 * scale) {
      spec = SPEC_3;
    } else if (totalYear <= SPEC_4 * scale) {
      spec = SPEC_4;
    } else if (totalYear <= SPEC_5 * scale) {
      spec = SPEC_5;
    } else if (totalYear <= SPEC_6 * scale) {
      spec = SPEC_6;
    } else {
      //大于当前最大年份
      scale *= 10;
      spec = calculateSpecification(totalYear);
    }
    return spec;
  }

  void setProgressYear(int progressYear) {
    if (totalYear <= 0) {
      throw new RuntimeException("haven't set totalYear");
    }
    if (progressYear > totalYear) {
      throw new RuntimeException("progressYear must be smaller than totalYear");
    }
    this.progressYear = progressYear;
    this.completeRows = progressYear / scale / formColumnNum;
    this.cellsInLastRow = (int) Math.ceil((double) progressYear / scale) % formColumnNum;
  }

  boolean hasExtraCell() {
    return endCell != null && formColumnNum > endCell.columnIndex + 1;
  }

  class Cell {
    int columnIndex;//index , 从0开始
    int rowIndex;

    Cell(int columnIndex, int rowIndex) {
      this.columnIndex = columnIndex;
      this.rowIndex = rowIndex;
    }
  }
}
