package ru.atconsulting.bigdata.domain.user;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by NSkovpin on 09.06.2016.
 */
public class UserInfo extends ResourceSupport{

    private String telegramId;

    private String subscriberNo;

    private String name;

    private String surname;

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User:[telegramId="+telegramId+
                ",subscriberNo="+subscriberNo+
                ",name="+name+
                ",surname="+surname+
                "]" +super.toString();
    }
}
