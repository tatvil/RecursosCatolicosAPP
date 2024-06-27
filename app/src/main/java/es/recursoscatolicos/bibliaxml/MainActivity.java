package es.recursoscatolicos.bibliaxml;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView fechaTextView;
    private TextView santoDelDiaTextView;
    private TextView cumpleTextView;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fechaTextView = findViewById(R.id.fechaTextView);
        santoDelDiaTextView = findViewById(R.id.santoDelDiaTextView);
        cumpleTextView = findViewById(R.id.cumpleTextView);
        searchButton = findViewById(R.id.searchButton);

        String fechaFormateada = getFechaActual();
        fechaTextView.setText(fechaFormateada);

        String santoDelDia = getSantoDelDia();
        santoDelDiaTextView.setText(santoDelDia);

        String cumpleanos = getCumpleanos();
        cumpleTextView.setText(cumpleanos);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para obtener la fecha actual en formato "dia semana, dd de mes de yyyy"
    private String getFechaActual() {
        Date fechaHoy = new Date();

        // Obtener el día de la semana
        String diaSemanaNumero = new SimpleDateFormat("u", Locale.getDefault()).format(fechaHoy);
        int diaSemanaIndex = Integer.parseInt(diaSemanaNumero) - 1; // Convertir a índice de array
        int dia = Integer.parseInt(new SimpleDateFormat("d", Locale.getDefault()).format(fechaHoy));
        int mes = Integer.parseInt(new SimpleDateFormat("M", Locale.getDefault()).format(fechaHoy));
        int anno = Integer.parseInt(new SimpleDateFormat("YYYY", Locale.getDefault()).format(fechaHoy));

        // Crear la lista de días de la semana
        ArrayList<String> diasSemana = new ArrayList<>(Arrays.asList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
        ));
        String diaSemana = diasSemana.get(diaSemanaIndex);

        // Crear la lista de los meses del año
        ArrayList<String> meses = new ArrayList<>(Arrays.asList(
                "enero", "febrero", "marzo", "abril", "mayo", "junio",
                "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
        ));


        return diaSemana + ", " + dia + " de " + meses.get(mes - 1) + " de " + anno;
    }

    // Método para obtener el santo del día de la fecha actual
    private String getSantoDelDia() {
        String santo = "Santo no encontrado";
        Date fechaHoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM", Locale.getDefault());
        String fechaFormateada = dateFormat.format(fechaHoy);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.santos);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                if (parser.getName().equals("santo")) {
                    String fecha = parser.getAttributeValue(null, "fecha");
                    if (fecha.equals(fechaFormateada)) {
                        santo = parser.nextText();
                        break;
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return santo;
    }

    private String getCumpleanos () {
        String cumpleano = "";
        Date fechaHoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM", Locale.getDefault());
        String fechaFormateada = dateFormat.format(fechaHoy);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.cumples);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                if (parser.getName().equals("cumple")) {
                    String fecha = parser.getAttributeValue(null, "fecha");
                    if (fecha.equals(fechaFormateada)) {
                        cumpleano += parser.nextText();
                        break;
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return cumpleano;
    }
}
