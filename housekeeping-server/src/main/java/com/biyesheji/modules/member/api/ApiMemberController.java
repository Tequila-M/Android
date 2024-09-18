package com.biyesheji.modules.member.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.common.utils.R;
import com.biyesheji.modules.member.entity.MemberEntity;
import com.biyesheji.modules.member.service.MemberService;

/**
 * 用户接口
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/api/member")
public class ApiMemberController {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 用户详情
	 * @param userId
	 * @return
	 */
    @GetMapping("info")
    public R info(@RequestAttribute Long userId){
    	MemberEntity member = memberService.queryObject(userId);
        return R.ok().put("member", member);
    }
	
	/**
	 * 更新用户
	 * @param member
	 * @return
	 */
	@RequestMapping("/update")
	public R update(@RequestBody MemberEntity member){
		memberService.update(member);
		return R.ok();
	}
	
	@RequestMapping("/modifyPwd")
	public R modifyPwd(@RequestAttribute Long userId, String oldPassword, String password){
		MemberEntity member = memberService.queryObject(userId);
		if(!member.getPassword().equals(oldPassword)) {
			return R.error("原始密码不对");
		}
		memberService.updatePwd(userId, password);
		return R.ok();
	}
}
