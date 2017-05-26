// Generated code from Butter Knife. Do not modify!
package edu.cpp.nada.weroute;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.pwittchen.weathericonview.WeatherIconView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WeatherRouteDetailsActivity_ViewBinding implements Unbinder {
  private WeatherRouteDetailsActivity target;

  @UiThread
  public WeatherRouteDetailsActivity_ViewBinding(WeatherRouteDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WeatherRouteDetailsActivity_ViewBinding(WeatherRouteDetailsActivity target, View source) {
    this.target = target;

    target.wind = Utils.findRequiredViewAsType(source, R.id.wind, "field 'wind'", TextView.class);
    target.pressure = Utils.findRequiredViewAsType(source, R.id.pressure, "field 'pressure'", TextView.class);
    target.visibility = Utils.findRequiredViewAsType(source, R.id.visibility, "field 'visibility'", TextView.class);
    target.rain = Utils.findRequiredViewAsType(source, R.id.rain, "field 'rain'", TextView.class);
    target.humidity = Utils.findRequiredViewAsType(source, R.id.humid, "field 'humidity'", TextView.class);
    target.cloud = Utils.findRequiredViewAsType(source, R.id.cloud, "field 'cloud'", TextView.class);
    target.tempMax = Utils.findRequiredViewAsType(source, R.id.tempMax, "field 'tempMax'", TextView.class);
    target.tempMin = Utils.findRequiredViewAsType(source, R.id.tempMin, "field 'tempMin'", TextView.class);
    target.nowSummary = Utils.findRequiredViewAsType(source, R.id.nowSummary, "field 'nowSummary'", TextView.class);
    target.currentSummary = Utils.findRequiredViewAsType(source, R.id.currentSummary, "field 'currentSummary'", TextView.class);
    target.currentTempView = Utils.findRequiredViewAsType(source, R.id.currentTemp, "field 'currentTempView'", TextView.class);
    target.weatherIconView = Utils.findRequiredViewAsType(source, R.id.weatherIcon, "field 'weatherIconView'", WeatherIconView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WeatherRouteDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.wind = null;
    target.pressure = null;
    target.visibility = null;
    target.rain = null;
    target.humidity = null;
    target.cloud = null;
    target.tempMax = null;
    target.tempMin = null;
    target.nowSummary = null;
    target.currentSummary = null;
    target.currentTempView = null;
    target.weatherIconView = null;
    target.title = null;
  }
}
