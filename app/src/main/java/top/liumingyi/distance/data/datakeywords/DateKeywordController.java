package top.liumingyi.distance.data.datakeywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import top.liumingyi.distance.data.datakeywords.models.DateKeyword;

/**
 * 控制需要显示的'DateKeyword'
 */
public class DateKeywordController {

  @Getter private List<DateKeyword> keywords = new ArrayList<>();

  public DateKeywordController() {
    createDateKeywords();
  }

  /**
   * 创建需要的'DateKeyword'
   */
  private void createDateKeywords() {
    List<Integer> ids = fetchIds();
    List<DateKeyword> shownKeyword = Config.getShownKeyword(ids);
    if (shownKeyword != null) {
      keywords.addAll(shownKeyword);
    }
  }

  private List<Integer> fetchIds() {
    Integer[] ids = { Config.KW_BIRTHDAY, Config.KW_NEXT_YEAR, Config.KW_NEXT_VACATION };
    return Arrays.asList(ids);
  }
}
