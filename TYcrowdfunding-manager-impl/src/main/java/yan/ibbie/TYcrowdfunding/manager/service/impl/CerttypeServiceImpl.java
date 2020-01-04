package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.AccountTypeCert;
import yan.ibbie.TYcrowdfunding.bean.Cert;
import yan.ibbie.TYcrowdfunding.manager.dao.AccountTypeCertMapper;
import yan.ibbie.TYcrowdfunding.manager.dao.CertMapper;
import yan.ibbie.TYcrowdfunding.manager.service.CerttypeService;

@Service
public class CerttypeServiceImpl implements CerttypeService {
	
	@Autowired
	AccountTypeCertMapper accountTypeCertMapper;
	@Autowired
	CertMapper certMapper;
	
	public List<AccountTypeCert> selectAll() {
		return accountTypeCertMapper.selectAll();
	}

	public int insertAccttypeCert(AccountTypeCert accountTypeCert) {
		return accountTypeCertMapper.insert(accountTypeCert);
	}

	public int deleteAccttypeCert(AccountTypeCert accountTypeCert) {
		return accountTypeCertMapper.deleteAccttypeCert(accountTypeCert);
	}

	public List<Cert> selectCertByAccttype(String accttype) {
		return certMapper.selectByAccttype(accttype);
	}
	
}
