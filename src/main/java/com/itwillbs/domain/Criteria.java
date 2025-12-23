package com.itwillbs.domain;

public class Criteria {
	
	private int page;		// 페이지 정보
	private int pageSize;   // 페이지 크기
	private String search;  // 검색
	
	
	public Criteria() {	    // 기본값 설정: 1페이지 10개씩 출력
		this.page = 1;
		this.pageSize = 10;
	}

	// SET
	public void setPage(int page) {
		if(page <= 0) {			
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		if(pageSize <= 0 || pageSize >= 100) {			
			this.pageSize = 10;
		}
		this.pageSize = pageSize;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	// GET
	public int getPage() {
		return page;
	}

	// 페이지 정보를 받아서 mapper에서 사용할 인덱스로 전환
	public int getPageStart() {
		return (this.page - 1) * this.pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public String getSearch() {
		return search;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", pageSize=" + pageSize + ", search=" + search + "]";
	}



}
