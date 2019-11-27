package com.github.doscene.calf.mapper;

import com.github.doscene.calf.common.entity.FrmCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.mapper</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface FrmCodeMapper {
    void insertOne(FrmCode frmCode);

    List<FrmCode> selectFrmCodeByParentId(String parentId);

    FrmCode selectFrmCodeById(String pid);

    int updateFrmCode(FrmCode frmCode);

    List<FrmCode> selectFrmCodeByParentToken(@Param("token") String token, @Param("text") String text);

    FrmCode selectFrmCodeByToken(String token);
}
