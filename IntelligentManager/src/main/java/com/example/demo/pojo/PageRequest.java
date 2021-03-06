package com.example.demo.pojo;

public class PageRequest {
	/**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 选择的序号
     */
    //private int selectIndex;
    
    public int getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	//public int getSelectIndex() {
	//	return selectIndex;
	//}

	//public void setSelectIndex(int selectIndex) {
	//	this.selectIndex = selectIndex;
	//}

	@Override
	public String toString() {
		return "PageRequest [pageNum=" + pageNum + ", pageSize=" + pageSize + ", selectIndex=" + "]";
	}
    
   
}
