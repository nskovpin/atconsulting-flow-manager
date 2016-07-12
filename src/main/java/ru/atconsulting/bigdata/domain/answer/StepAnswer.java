package ru.atconsulting.bigdata.domain.answer;

import java.io.Serializable;

/**
 * Created by NSkovpin on 21.06.2016.
 */
public class StepAnswer<T> implements Serializable, Answer{

    private static final long serialVersionUID = 6529685098267757690L;

    private String action;

    private String step;

    private String telegramId;

    private T info;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepAnswer<?> that = (StepAnswer<?>) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (step != null ? !step.equals(that.step) : that.step != null) return false;
        if (telegramId != null ? !telegramId.equals(that.telegramId) : that.telegramId != null) return false;
        return info != null ? info.equals(that.info) : that.info == null;

    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (step != null ? step.hashCode() : 0);
        result = 31 * result + (telegramId != null ? telegramId.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}
