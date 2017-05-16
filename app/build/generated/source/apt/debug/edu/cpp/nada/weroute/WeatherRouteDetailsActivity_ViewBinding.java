// Generated code from Butter Knife. Do not modify!
package edu.cpp.nada.weroute;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
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
  }
}
