package es.recursoscatolicos.bibliaxml;

public class Verse {
        private int bookNumber;
        private int chapterNumber;
        private int number;
        private String text;

        public Verse(int bookNumber, int chapterNumber, int number, String text) {
            this.bookNumber = bookNumber;
            this.chapterNumber = chapterNumber;
            this.number = number;
            this.text = text;
        }

        public int getBookNumber() {
            return bookNumber;
        }

        public int getChapterNumber() {
            return chapterNumber;
        }

        public int getNumber() {
            return number;
        }

        public String getText() {
            return text;
        }
    }

