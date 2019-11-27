package com.github.doscene.calf.service.mail.impl;

import com.github.doscene.calf.common.entity.FrmMail;
import com.github.doscene.calf.service.mail.MailService;
import org.springframework.stereotype.Service;

/**
 * <h1>com.github.doscene.calf.service.mail.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
public class MailServiceImpl implements MailService {

   /* private JmsMessagingTemplate jmsMessagingTemplate;

    public MailServiceImpl(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }*/

    @Override
    public boolean sendMail(FrmMail mail) {

        return false;
    }
}
