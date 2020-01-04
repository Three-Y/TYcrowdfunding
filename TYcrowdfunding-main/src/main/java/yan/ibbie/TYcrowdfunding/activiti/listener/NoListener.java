package yan.ibbie.TYcrowdfunding.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class NoListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("nnnnnnnnnnnnnnnnnnnnnnn");
	}

	
}
