package com.douzone.mysite.vo;

public class PageVo {
	private Long count;
	private Long page;
	private Long totalSize;
	private Long startPage;
	private Long lastPage;
	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public Long getStartPage() {
		return startPage;
	}

	public void setStartPage(Long startPage) {
		this.startPage = startPage;
	}

	public Long getLastPage() {
		return lastPage;
	}

	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;
	}

	@Override
	public String toString() {
		return "PageVo [count=" + count + ", page=" + page + ", totalSize=" + totalSize + ", startPage=" + startPage
				+ ", lastPage=" + lastPage + "]";
	}

}
