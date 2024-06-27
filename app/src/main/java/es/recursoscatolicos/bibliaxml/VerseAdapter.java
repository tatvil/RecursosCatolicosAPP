package es.recursoscatolicos.bibliaxml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VerseAdapter extends RecyclerView.Adapter<VerseAdapter.VerseViewHolder> {
    private List<Verse> verseList;
    private Context context;

    public static class VerseViewHolder extends RecyclerView.ViewHolder {
        public TextView bookNameTextView;
        public TextView verseNumberTextView;
        public TextView textView;
        public ImageButton favoriteButton;

        public VerseViewHolder(View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            verseNumberTextView = itemView.findViewById(R.id.verseNumberTextView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public VerseAdapter(Context context, List<Verse> verseList) {
        this.context = context;
        this.verseList = verseList;
    }

    @Override
    public VerseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verse_item, parent, false);
        return new VerseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VerseViewHolder holder, int position) {
        Verse verse = verseList.get(position);
        holder.bookNameTextView.setText(getBookName(verse.getBookNumber()));
        holder.verseNumberTextView.setText("Capítulo " + verse.getChapterNumber() + ", Versículo " + verse.getNumber());
        holder.textView.setText(verse.getText());
    }

    @Override
    public int getItemCount() {
        return verseList.size();
    }

    private String getBookName(int bookNumber) {
        String[] bookNames = context.getResources().getStringArray(R.array.bible_books);
        return bookNames[bookNumber - 1];
    }
}

