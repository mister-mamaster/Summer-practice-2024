package ru.opencode.bankinfo.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate dateTime) throws Exception {
        if(null == dateTime) {
            return null;
        }
        return dateTime.format(dateFormat);
    }

    @Override
    public LocalDate unmarshal(String dateTime) throws Exception {
        if(null == dateTime) {
            return null;
        }
        return LocalDate.parse(dateTime, dateFormat);
    }

    public LocalDateAdapter(){}

}