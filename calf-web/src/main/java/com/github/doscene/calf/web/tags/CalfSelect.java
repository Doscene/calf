package com.github.doscene.calf.web.tags;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * <h1>com.github.doscene.calf.web.tags</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class CalfSelect extends TagSupport {
    private Collection collection;
    private String textName;
    private String valueName;
    private String className = "";
    private String style = "";
    private String id = "";
    private String name = "";

    private String headerText = "";
    private String headerValue = "";
    private String selected = "";

    @Override
    public int doStartTag() throws JspException {
        StringBuilder html = new StringBuilder();
        html.append("<select class=\"" + className + "\" style=\"" + style + "\" id=\"" + id + "\" name=\"" + name + "\">");
        if (!Strings.isNullOrEmpty(headerText) || !Strings.isNullOrEmpty(headerValue)) {
            html.append("<option value=\"" + headerValue + "\">" + headerText + "</option>");
        }
        if (collection != null && !collection.isEmpty()) {
            for (Object v : collection) {
                try {
                    String text = BeanUtils.getProperty(v, textName);
                    String value = BeanUtils.getProperty(v, valueName);
                    if (!Strings.isNullOrEmpty(selected) && selected.equals(value)) {
                        html.append("<option value=\"" + value + "\" selected=\"selected\">" + text + "</option>");
                    } else {
                        html.append("<option value=\"" + value + "\">" + text + "</option>");
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    log.error("字段不存在", e);
                    break;
                }
            }
        }
        html.append("</select>");
        try {
            pageContext.getOut().print(html.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }
}
