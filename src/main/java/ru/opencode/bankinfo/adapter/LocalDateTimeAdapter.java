package ru.opencode.bankinfo.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    //private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        if(null == localDateTime) {
            return null;
        }
        return localDateTime.format(dateFormat);
    }

    @Override
    public LocalDateTime unmarshal(String localDateTime) throws Exception {
        if(null == localDateTime) {
            return null;
        }
        return LocalDateTime.parse(localDateTime, dateFormat);
    }
    public LocalDateTimeAdapter() {}
}