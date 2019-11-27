package com.github.doscene.calf.service.frm;

import com.github.doscene.calf.common.entity.FrmCode;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.frm</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface FrmCodeService {
    /**
     * 根据父ID获取字典
     *
     * @param pid 父ID
     * @return 字典
     */
    List<FrmCode> getFrmCodeByParent(String pid);

    /**
     * 根据PID获取字典
     *
     * @param pid 主键
     * @return 字典
     */
    FrmCode getFrmCodeByPid(String pid);

    /**
     * 保存字典
     *
     * @param frmCode 实体
     * @return 结果
     */
    boolean saveFrmCode(FrmCode frmCode);

    /**
     * 修改字典
     *
     * @param frmCode 实体
     * @return 结果
     */
    boolean editFrmCode(FrmCode frmCode);
}
