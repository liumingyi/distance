package top.liumingyi.ciel;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * RxBus<br>
 *
 * <p>
 * 功能：一个消息传递总线<br>
 * 使用方法：
 * <br>1.发送消息,RxBus.getDefault.send(xxxEvent)
 * <br>2.接收消息,RxBus.toObservable.subscribe(...)
 * </p>
 * Created by liumingyi on 20/12/2016.
 */

public class RxBus {

  private static volatile RxBus instance;

  private RxBus() {
  }

  public static RxBus getDefault() {
    if (instance == null) {
      synchronized (RxBus.class) {
        if (instance == null) {
          instance = new RxBus();
        }
      }
    }
    return instance;
  }

  private final FlowableProcessor<Object> bus = PublishProcessor.create().toSerialized();

  public void send(Object o) {
    bus.onNext(o);
  }

  public Flowable<Object> toObservable() {
    return bus;
  }
}
