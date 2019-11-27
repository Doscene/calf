package com.github.doscene.calf.service.frm.impl;

import com.github.doscene.calf.common.entity.FrmCode;
import com.github.doscene.calf.mapper.FrmCodeMapper;
import com.github.doscene.calf.service.frm.FrmCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.frm.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
public class FrmCodeServiceImpl implements FrmCodeService {
    private final FrmCodeMapper frmCodeMapper;

    public FrmCodeServiceImpl(FrmCodeMapper frmCodeMapper) {
        this.frmCodeMapper = frmCodeMapper;
    }

    @Override
    public List<FrmCode> getFrmCodeByParent(String pid) {
        return frmCodeMapper.selectFrmCodeByParentId(pid);
    }

    @Override
    public FrmCode getFrmCodeByPid(String pid) {
        return frmCodeMapper.selectFrmCodeById(pid);
    }

    @Override
    public boolean saveFrmCode(FrmCode frmCode) {
        frmCodeMapper.insertOne(frmCode);
        return true;
    }

    @Override
    public boolean editFrmCode(FrmCode frmCode) {
     return    frmCodeMapper.updateFrmCode(frmCode)==1;
    }
}
