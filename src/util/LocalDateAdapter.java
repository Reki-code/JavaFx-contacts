package util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) {
        return DateUtil.parse(v);
    }

    public String marshal(LocalDate v) {
        return DateUtil.format(v);
    }
}
