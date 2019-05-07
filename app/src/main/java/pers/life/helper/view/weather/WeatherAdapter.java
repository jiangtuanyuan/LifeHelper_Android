package pers.life.helper.view.weather;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import pers.life.helper.R;
import pers.life.helper.entity.JokeEntity;
import pers.life.helper.entity.WeatherEntity;

public class WeatherAdapter extends BaseItemDraggableAdapter<WeatherEntity.FutureBean, BaseViewHolder> {
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_wind)
    TextView tvWind;

    public WeatherAdapter(List<WeatherEntity.FutureBean> data) {
        super(R.layout.weather_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeatherEntity.FutureBean item) {
        helper.setText(R.id.tv_week, item.getWeek())
                .setText(R.id.tv_weather, item.getWeather())
                .setText(R.id.tv_temperature, item.getTemperature())
                .setText(R.id.tv_wind, item.getWind());
    }
}
