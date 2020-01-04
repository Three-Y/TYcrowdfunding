package yan.ibbie.TYcrowdfunding.manager.service;

import java.util.List;

import yan.ibbie.TYcrowdfunding.bean.Cert;
import yan.ibbie.TYcrowdfunding.bean.MemberCert;

public interface CertService {

	List<Cert> selectAll();

	int saveMemberCert(List<MemberCert> memberCerts);

}
