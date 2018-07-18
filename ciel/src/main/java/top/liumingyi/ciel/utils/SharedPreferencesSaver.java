package top.liumingyi.ciel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;

/**
 * 使用SharedPreferences作为存储的类的基类
 * Created by liumingyi on 2017/9/5.
 */

public abstract class SharedPreferencesSaver {

  private SharedPreferences preferences;

  public SharedPreferencesSaver(Context context, String libKey) {
    preferences = context.getSharedPreferences(libKey, Context.MODE_PRIVATE);
  }

  protected String getString(String key, String defValue) {
    return preferences.getString(key, defValue);
  }

  protected int getInt(String key, int defValue) {
    return preferences.getInt(key, defValue);
  }

  protected boolean getBoolean(String key, boolean defValue) {
    return preferences.getBoolean(key, defValue);
  }

  protected long getLong(String key, long defValue) {
    return preferences.getLong(key, defValue);
  }

  protected float getFloat(String key, float defValue) {
    return preferences.getFloat(key, defValue);
  }

  protected Set<String> getStringSet(String key, Set<String> defValue) {
    return preferences.getStringSet(key, defValue);
  }

  protected void save(String key, String value) {
    preferences.edit().putString(key, value).apply();
  }

  protected void save(String key, int value) {
    preferences.edit().putInt(key, value).apply();
  }

  protected void save(String key, boolean value) {
    preferences.edit().putBoolean(key, value).apply();
  }

  protected void save(String key, long value) {
    preferences.edit().putLong(key, value).apply();
  }

  protected void save(String key, float value) {
    preferences.edit().putFloat(key, value).apply();
  }

  protected void save(String key, Set<String> value) {
    preferences.edit().putStringSet(key, value).apply();
  }

  protected void remove(String key) {
    preferences.edit().remove(key).apply();
  }

  /**
   * 是否有存储的数据
   *
   * @return true表示有已保存的数据
   */
  protected boolean hasData(String key) {
    return preferences.contains(key);
  }

  public void clean() {
    preferences.edit().clear().apply();
  }
}
