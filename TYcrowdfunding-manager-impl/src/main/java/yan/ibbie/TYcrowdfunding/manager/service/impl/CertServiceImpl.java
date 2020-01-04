package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Cert;
import yan.ibbie.TYcrowdfunding.bean.MemberCert;
import yan.ibbie.TYcrowdfunding.manager.dao.CertMapper;
import yan.ibbie.TYcrowdfunding.manager.service.CertService;

@Service
public class CertServiceImpl implements CertService {
	
	@Autowired
	CertMapper certMapper;
	
	

	public List<Cert> selectAll() {
		return certMapper.selectAll();
	}

	public int saveMemberCert(List<MemberCert> memberCerts) {
		return certMapper.saveMemberCert(memberCerts);
	}
	
	
}
