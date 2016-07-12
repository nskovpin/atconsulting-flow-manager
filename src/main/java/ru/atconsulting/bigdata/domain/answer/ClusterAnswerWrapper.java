package ru.atconsulting.bigdata.domain.answer;

/**
 * Created by NSkovpin on 04.07.2016.
 */
public class ClusterAnswerWrapper<T> extends ClusterAnswer<T> {

    private Error telegramError;

    private MainAnswer mainAnswer;

    public Error getTelegramError() {
        return telegramError;
    }

    public void setTelegramError(Error telegramError) {
        this.telegramError = telegramError;
    }

    public MainAnswer getMainAnswer() {
        return mainAnswer;
    }

    public void setMainAnswer(MainAnswer mainAnswer) {
        this.mainAnswer = mainAnswer;
    }
}
