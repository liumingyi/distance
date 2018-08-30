package top.liumingyi.distance.helpers;

import android.content.Context;
import android.support.v4.util.ArraySet;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import top.liumingyi.distance.data.Label;
import top.liumingyi.tang.utils.SharedPreferencesSaver;

public class LabelSaver extends SharedPreferencesSaver {

  private static final String LIB_KEY = "pre_lib_key_eventInfo";
  private static final String EVENT_KEY = "key_event_infos";

  public LabelSaver(Context context) {
    super(context, LIB_KEY);
  }

  public void saveLabels(List<Label> list) {
    Gson gson = new Gson();
    Set<String> eventSet = new ArraySet<>();
    for (Label eventInfo : list) {
      eventSet.add(gson.toJson(eventInfo));
    }
    save(EVENT_KEY, eventSet);
  }

  public List<Label> getLabels() {
    Set<String> stringSet = getStringSet(EVENT_KEY, new ArraySet<>());
    List<Label> result = new ArrayList<>();
    Gson gson = new Gson();
    for (String s : stringSet) {
      result.add(gson.fromJson(s, Label.class));
    }
    return result;
  }

  public void addLabel(Label label) {
    List<Label> labels = getLabels();
    labels.add(label);
    saveLabels(labels);
  }

  public void deleteLabel(Label label) {
    List<Label> labels = getLabels();
    for (Label la : labels) {
      if (la.getTitle().equals(label.getTitle()) && la.getEndCalender()
          .equals(label.getEndCalender())) {
        labels.remove(la);
        break;
      }
    }
    saveLabels(labels);
  }
}
