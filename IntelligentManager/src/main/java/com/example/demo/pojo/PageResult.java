package com.example.demo.pojo;

import java.util.List;

public class PageResult {
	
	/**
	 * 状态码
	 */
	private int status;
	
	/**
	 * 信息
	 */
	private String message;
	
	/**
     * 当前页码
     */
    private int pageNum;
    
    /**
     * 每页数量
     */
    private int pageSize;
    
    /**
     * 记录总数
     */
    private int totalSize;
    
    /**
     * 页码总数
     */
    private int totalPages;
    
    /**
     * 数据模型
     */
    private List<?> content;
    
    
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
    
    
    public long getTotalSize() {
        return totalSize;
    }
    
    
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
    
    
    public int getTotalPages() {
        return totalPages;
    }
    
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    
    public List<?> getContent() {
        return content;
    }
    
    
    public void setContent(List<?> content) {
        this.content = content;
    }


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}
