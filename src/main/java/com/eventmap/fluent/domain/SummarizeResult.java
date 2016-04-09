package com.eventmap.fluent.domain;

/**
 * Created by huytran on 4/10/16.
 */
public class SummarizeResult {
    private int correctWords;
    private int incorrectWords;
    private int totalWords;
    private int speechSpeedAverage;

    public int getCorrectWords() {
        return correctWords;
    }

    public void setCorrectWords(int correctWords) {
        this.correctWords = correctWords;
    }

    public int getIncorrectWords() {
        return incorrectWords;
    }

    public void setIncorrectWords(int incorrectWords) {
        this.incorrectWords = incorrectWords;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public int getSpeechSpeedAverage() {
        return speechSpeedAverage;
    }

    public void setSpeechSpeedAverage(int speechSpeedAverage) {
        this.speechSpeedAverage = speechSpeedAverage;
    }
}
