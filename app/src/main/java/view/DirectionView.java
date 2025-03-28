package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Map;

import model.Weather;

public class DirectionView extends View {

    private Paint circlePaint;
    private Paint trianglePaint;
    private Weather weatherData; // Données météo (direction et force du vent)

    public DirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Peinture pour le cercle extérieur
        circlePaint = new Paint();
        circlePaint.setColor(0xFFB0BEC5); // Gris clair
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        // Peinture pour les triangles représentant le vent
        trianglePaint = new Paint();
        trianglePaint.setColor(0xFFFF5722); // Orange
        trianglePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (weatherData == null) {
            return; // Ne rien dessiner si les données sont absentes
        }

        // Centre et rayon du cercle
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2 - 20;

        // Dessiner le cercle extérieur
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Récupérer les valeurs du vent
        Map<Integer, Weather.WindData> windDirections = weatherData.getWindDirections();

        // Trouver la force maximale pour normaliser les tailles des triangles
        int maxForce = getMaxForce(windDirections);

        // Dessiner chaque triangle pour représenter le vent
        for (Weather.WindData windData : windDirections.values()) {
            int degrees = windData.getCompassDegrees();
            int force = windData.getForce();

            // Convertir les degrés en radians
            float angleRad = (float) Math.toRadians(degrees);

            // Normaliser la taille du triangle en fonction de la force du vent
            float triangleSize = (force / (float) maxForce) * radius;

            // Calculer la position du sommet du triangle
            float tipX = centerX + (float) (triangleSize * Math.sin(angleRad));
            float tipY = centerY - (float) (triangleSize * Math.cos(angleRad));

            // Tracer un triangle pour chaque direction du vent
            Path path = new Path();
            path.moveTo(centerX, centerY);
            path.lineTo(tipX - 10, tipY);
            path.lineTo(tipX + 10, tipY);
            path.close();

            canvas.drawPath(path, trianglePaint);
        }
    }

    // Méthode pour définir les données météo
    public void setWeatherData(Weather weather) {
        this.weatherData = weather;
        invalidate(); // Redessiner la vue après mise à jour des données
    }

    // Méthode pour obtenir la force maximale du vent
    private int getMaxForce(Map<Integer, Weather.WindData> windDirections) {
        int max = 1; // Éviter une division par zéro
        for (Weather.WindData windData : windDirections.values()) {
            if (windData.getForce() > max) {
                max = windData.getForce();
            }
        }
        return max;
    }
}




