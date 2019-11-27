package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>com.github.doscene.calf.common.entity</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */

/**
 * comment on column "frm_mail"."pid" is '主键'
 * /
 * <p>
 * comment on column "frm_mail"."create_time" is '创建时间'
 * /
 * <p>
 * comment on column "frm_mail"."update_time" is '更新时间'
 * /
 * <p>
 * comment on column "frm_mail"."create_by" is '创建人'
 * /
 * <p>
 * comment on column "frm_mail"."create_ip" is '创建IP'
 * /
 * <p>
 * comment on column "frm_mail"."valuable" is '是否有效 0无效 1有效'
 * /
 * <p>
 * comment on column "frm_mail"."expiredTime" is '过期时间'
 * /
 * <p>
 * comment on column "frm_mail"."department" is '所属部门'
 * /
 * <p>
 * comment on column "frm_mail"."from" is '发送者'
 * /
 * <p>
 * comment on column "frm_mail"."to" is '接收者'
 * /
 * <p>
 * comment on column "frm_mail"."subject" is '主体'
 * /
 * <p>
 * comment on column "frm_mail"."text" is '内容'
 * /
 * <p>
 * comment on column "frm_mail"."post_times" is '发送次数'
 * /
 * <p>
 * comment on column "frm_mail"."status" is '状态'
 * /
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FrmMail extends BaseEntity implements Serializable {
    private String mailFrom;
    private String mailTo;
    private String subject;
    private String text;
    private Integer postTimes;
    private String status;
}
