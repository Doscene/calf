package com.github.doscene.calf.common.utils;

import com.github.doscene.calf.common.entity.FrmCode;
import com.github.doscene.calf.mapper.FrmCodeMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>com.github.doscene.calf.common.utils</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
public class CodeUtils {
    private static final FrmCodeMapper frmCodeMapper = SpringUtils.getBean(FrmCodeMapper.class);
    private static final ConcurrentHashMap<String, List<FrmCode>> cache = new ConcurrentHashMap<>();

    /**
     * 根据父字典标识获取子字典
     *
     * @param token 字典
     */
    public static List<FrmCode> getSubCodes(String token) {
        List<FrmCode> result;
        if (cache.containsKey(token)) {
            log.info("==============》从缓存中拉取字典{}", token);
            result = cache.get(token);
        } else {
            result = frmCodeMapper.selectFrmCodeByParentToken(token, null);
            log.info("==============》钝化字典{}", token);
            cache.put(token, result);
        }
        return result;
    }


    public static String getSubCode(String token, String text) {
        return frmCodeMapper.selectFrmCodeByParentToken(token, text).get(0).getCodeName();
    }

    public static String getSubCode(String token) {
        return frmCodeMapper.selectFrmCodeByToken(token).getCodeText();
    }
}
