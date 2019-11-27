package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <h1>系统用户</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */


@Data
@EqualsAndHashCode(callSuper = true)
//@JsonIgnoreProperties(value = {"loginPassword"})
public class SysUser extends BaseEntity implements Serializable {
    private String loginName;
    private transient String loginPassword;
    private String salt;
    /**************用户信息*******************/
    private String trueName;
    private String email;
    private String phone;
    private String gender;
    private Integer age;
    private Date birthday;
    private String headPortrait;
    private Date lastLoginTime;
    private String lastLoginIp;
}