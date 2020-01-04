package yan.ibbie.TYcrowdfunding.manager.service;

import java.util.List;

import yan.ibbie.TYcrowdfunding.bean.AccountTypeCert;
import yan.ibbie.TYcrowdfunding.bean.Cert;

public interface CerttypeService {

	List<AccountTypeCert> selectAll();

	int insertAccttypeCert(AccountTypeCert accountTypeCert);

	int deleteAccttypeCert(AccountTypeCert accountTypeCert);

	List<Cert> selectCertByAccttype(String accttype);

}
