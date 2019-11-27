package com.github.doscene.calf.service.mail;

import com.github.doscene.calf.common.entity.FrmMail;

/**
 * <h1>com.github.doscene.calf.service.mail</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface MailService {

    boolean sendMail(FrmMail mail);
}
