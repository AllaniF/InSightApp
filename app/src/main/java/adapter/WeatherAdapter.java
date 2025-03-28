package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.insightapp.R;

import java.util.List;

import model.Weather;

public class WeatherAdapter extends BaseAdapter {

    private List<Weather> weatherList;
    private Context context;

    public WeatherAdapter(Context context, List<Weather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_weather, parent, false);
        }

        Weather weather = weatherList.get(position);

        TextView solNumber = convertView.findViewById(R.id.sol_number);
        TextView avgTemp = convertView.findViewById(R.id.avg_temp);
        TextView avgPressure = convertView.findViewById(R.id.avg_pressure);

        solNumber.setText("Sol n°: " + weather.getSol());
        avgTemp.setText("Temperature: " + weather.getAvgTemp() );
        avgPressure.setText("Pression: " + weather.getAvgPressure());

        return convertView;
    }
}
