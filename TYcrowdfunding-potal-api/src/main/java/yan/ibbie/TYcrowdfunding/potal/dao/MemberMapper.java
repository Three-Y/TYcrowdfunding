package yan.ibbie.TYcrowdfunding.potal.dao;

import yan.ibbie.TYcrowdfunding.bean.Member;
import java.util.List;
import java.util.Map;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);

	Member queryMebmerlogin(Map<String, Object> paramMap);

	void updateBasicinfo(Member loginMember);

	int updateAccttype(Member member);

	int updBasicInfo(Member loginMember);

	void updateEmail(Member member);

	void updateAuthStatus(Member member);

	List<Map<String, Object>> queryCertByMemberId(Integer memberId);
}