package com.itwillbs.domain;

// 페이징 처리를 위해서 생성한 객체

public class PageVO {
	
	private int totalCount; 	 // 총 글의 개수
	private int startPage;  	 // (페이징블럭) 시작 페이지 번호
	private int endPage;    	 // (페이징블럭) 끝 페이지 번호
	
	private boolean prev;   	 // 이전페이지 사용여부 
	private boolean next;		 // 다음페이지 사용여부
	
	private int pageBlock = 10;  // 페이징블럭의 크기 1...10, 2...10
	
	// private int page;			 // 페이지 번호
	// private int pageSize;	     // 페이지 출력 개수/크기
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		// 주소줄에서 파라메터를 수집해서 처리
		this.cri = cri;
	}
	public void setTotalCount(int totalCount) {
		// DB에서 select count() 처리
		this.totalCount = totalCount;
		// 페이징처리에 필요한 정보계산 메서드를 호출
		calcPageData();
	}
	
	private void calcPageData() {
		System.out.println(" 페이징처리 하단블럭정보 계산 - 시작 ");
		
		// endPage 계산
		endPage = (int)(Math.ceil(cri.getPage()/(double)pageBlock) * pageBlock);
		
		// startPage 계산
		startPage = (endPage - pageBlock) + 1;
		
		// endPage 다시 계산(실제 페이지 정보가 없을 수 있기 때문)
		int tmpEndPage = (int)Math.ceil(this.totalCount / (double)cri.getPageSize());
		
		// endPage 재설정
		if(endPage > tmpEndPage) {
			endPage = tmpEndPage;
		}
		
		// prev 계산 (시작페이지를 1인지 아닌지 체크)
		prev = startPage != 1;
	
		// next 계산
		next = endPage * cri.getPageSize() < totalCount;
		
		System.out.println(" 페이징처리 하단블럭정보 계산 - 끝 ");
	}
	
	////////////////////////////////////////////////////////////////////////////

	public int getTotalCount() {
		return totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public Criteria getCri() {
		return cri;
	}

	

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
	
	@Override
	public String toString() {
		return "PageVO [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", pageBlock=" + pageBlock + ", cri=" + cri + "]";
	}

	
	
	
	
}
