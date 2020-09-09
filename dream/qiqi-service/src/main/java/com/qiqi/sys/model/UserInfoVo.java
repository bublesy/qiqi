package com.qiqi.sys.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 * @author QiQiDream
 * @since 2019/11/18 10:53
 */
@Data
public class UserInfoVo {

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "菜单")
	private List<MenuVo> menus;

	@ApiModelProperty(value = "按钮权限")
	private List<String> permissions;
}
