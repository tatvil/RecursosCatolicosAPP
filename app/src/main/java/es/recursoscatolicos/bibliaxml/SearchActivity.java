package es.recursoscatolicos.bibliaxml;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private VerseAdapter verseAdapter;
    private List<Verse> verseList;
    private List<Verse> allVerses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);

        verseList = new ArrayList<>();
        allVerses = new ArrayList<>();
        verseAdapter = new VerseAdapter(this, verseList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(verseAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().toLowerCase();
                performSearch(query);
            }
        });

        loadVersesFromXML();
    }

    private void loadVersesFromXML() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.bible);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            int bookNumber = -1;
            int chapterNumber = -1;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("book".equals(tagName)) {
                            bookNumber = Integer.parseInt(parser.getAttributeValue(null, "number"));
                        } else if ("chapter".equals(tagName)) {
                            chapterNumber = Integer.parseInt(parser.getAttributeValue(null, "number"));
                        } else if ("verse".equals(tagName)) {
                            int verseNumber = Integer.parseInt(parser.getAttributeValue(null, "number"));
                            String verseText = parser.nextText();
                            Verse verse = new Verse(bookNumber, chapterNumber, verseNumber, verseText);
                            verseList.add(verse);
                            allVerses.add(verse); // Guarda todos los versículos en allVerses
                        }
                        break;
                }

                eventType = parser.next();
            }

            verseAdapter.notifyDataSetChanged();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void performSearch(String query) {
        verseList.clear();
        for (Verse verse : allVerses) {
            if (verse.getText().toLowerCase().contains(query)) {
                verseList.add(verse);
            }
        }
        verseAdapter.notifyDataSetChanged();
    }
}
