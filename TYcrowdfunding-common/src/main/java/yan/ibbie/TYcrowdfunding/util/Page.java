package yan.ibbie.TYcrowdfunding.util;

import java.util.List;

public class Page {
	private int pageno;
	private int pageSize;
	private int totalno;
	private int totalSize;
	private List<?> datas;

	public Page(int pageno, int pageSize) {
		
		if (pageno<=0) {
			this.pageno = 1;
		}else {
			this.pageno = pageno;
		}
		
		if (pageSize<=0) {
			this.pageSize = 10;
		}else {
			this.pageSize = pageSize;
		}
		
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalno() {
		return totalno;
	}

	private void setTotalno(int totalno) {
		this.totalno = totalno;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		this.totalno = (totalSize%pageSize)==0?(totalSize/pageSize):(totalSize/pageSize+1);
	}

	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	public int getStartIndex() {
		return (this.pageno-1)*pageSize;
	}
}
