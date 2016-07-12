package ru.atconsulting.bigdata.domain;

/**
 * Created by NSkovpin on 09.06.2016.
 */
public class Shutdown {

    private String telegramId;

    private String status;

    private String info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    @Override
    public String toString() {
        return "Shutdown{" +
                "info='" + info + '\'' +
                ", telegramId='" + telegramId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
