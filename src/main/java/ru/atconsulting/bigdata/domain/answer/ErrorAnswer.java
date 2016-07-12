package ru.atconsulting.bigdata.domain.answer;

import org.springframework.hateoas.ResourceSupport;
import ru.atconsulting.bigdata.workflow.actions.Action;

import java.io.Serializable;

/**
 * Created by NSkovpin on 21.06.2016.
 */
public class ErrorAnswer extends ResourceSupport implements Serializable,Answer{

    private String action;

    private String error;

    private String step;

    private String telegramId;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String getAction() {
        return action;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ErrorAnswer that = (ErrorAnswer) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (step != null ? !step.equals(that.step) : that.step != null) return false;
        return telegramId != null ? telegramId.equals(that.telegramId) : that.telegramId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (step != null ? step.hashCode() : 0);
        result = 31 * result + (telegramId != null ? telegramId.hashCode() : 0);
        return result;
    }
}
