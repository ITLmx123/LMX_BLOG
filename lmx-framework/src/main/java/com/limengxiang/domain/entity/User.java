package com.limengxiang.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.limengxiang.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2022-09-07 15:18:46
 */

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User extends BaseEntity {
    //主键@TableId

    private Long id;
    //用户名
    @NotBlank(message = "用户名不能为空")
    private String userName;
    //昵称
    private String nickName;
    //密码
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "\\w{6,12}")
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}",message = "邮箱格式不符合")
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
}

