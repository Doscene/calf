package com.github.doscene.calf.web.converter;

import com.google.common.base.Strings;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h1>时间转换器</h1>
 *
 * @author lds <a href="https://github.com/doscene">github.com/doscene</a>
 */
public class DateConverter implements Converter<String, Date> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String source) {
        if (Strings.isNullOrEmpty(source)) {
            return null;
        }
        try {
            return sdf.parse(source);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return null;
    }
}
