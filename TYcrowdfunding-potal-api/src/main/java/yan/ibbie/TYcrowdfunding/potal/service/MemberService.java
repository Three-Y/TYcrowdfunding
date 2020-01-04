package yan.ibbie.TYcrowdfunding.potal.service;

import java.util.List;
import java.util.Map;

import yan.ibbie.TYcrowdfunding.bean.Member;

public interface MemberService {

	Member queryMemberLogin(Map<String, Object> paraMap);

	Integer updateAccttype(Member member);

	int updBasicInfo(Member loginMember);

	void updateEmail(Member member);

	void updateAuthStatus(Member member);

	Member queryMemberById(Integer memberId);

	List<Map<String, Object>> queryCertByMemberId(Integer memberId);

}
