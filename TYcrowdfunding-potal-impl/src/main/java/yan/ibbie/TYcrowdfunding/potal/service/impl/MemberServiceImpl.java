package yan.ibbie.TYcrowdfunding.potal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.potal.dao.MemberMapper;
import yan.ibbie.TYcrowdfunding.potal.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	public Member queryMemberLogin(Map<String, Object> paraMap) {
		return memberMapper.queryMebmerlogin(paraMap);
	}

	public Integer updateAccttype(Member member) {
		return memberMapper.updateAccttype(member);
	}

	public int updBasicInfo(Member loginMember) {
		return memberMapper.updBasicInfo(loginMember);
	}

	public void updateEmail(Member member) {
		memberMapper.updateEmail(member);
	}

	public void updateAuthStatus(Member member) {
		memberMapper.updateAuthStatus(member);
	}

	public Member queryMemberById(Integer memberId) {
		return memberMapper.selectByPrimaryKey(memberId);
	}

	public List<Map<String, Object>> queryCertByMemberId(Integer memberId) {
		return memberMapper.queryCertByMemberId(memberId);
	}

}
