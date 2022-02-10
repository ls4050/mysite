package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	//새글, 답글 달기 
	public Boolean addContents(BoardVo vo) {
		return boardRepository.insert(vo);
	}
	
	//글보기 
	public BoardVo getContents(Long no){
		boardRepository.hitUp(no);
		return boardRepository.findByBno(no);
	}
	
	
	//글 수정
	public Boolean updateContents(BoardVo vo) {
		return boardRepository.update(vo);
	}
	
	//글 삭제
	public Boolean deleteContents(Long no) {
		return boardRepository.delete(no);
	}
	
	//글 리스트 (찾기결과)
	public Map<String, Object> getContentsList(Integer currentPage, String keyword) {
		Integer tempPage = currentPage;
		if (tempPage == null || tempPage == 0) {
			currentPage = 1;
		}
		Integer splitNum = 5; // 한 페이지에 들어가는 데이터 개수
		Integer start = (currentPage - 1) * splitNum;
		String limit = "limit " + start + ", " + splitNum;
		if (keyword == null) {
			keyword="";
		}
		List<BoardVo> list = boardRepository.findAll(keyword, limit);
		
		Integer pageLength = 5; // 한 블럭에 들어가는 페이지 개수
		Integer totalRows = boardRepository.getTotalRows(keyword); // 총 데이터 수
		Integer cPageBlock = (currentPage % pageLength == 0) ? currentPage / pageLength : (currentPage / pageLength) + 1; // 현재 페이지 블록
		Integer totalPages = totalRows % splitNum == 0 ? totalRows / splitNum : (totalRows / splitNum) + 1; // 총 페이지 수
		Integer startPage = 1 + (cPageBlock - 1) * pageLength;
		Integer endPage = startPage + pageLength - 1;
		
		if (totalPages == 0) {
			totalPages = 1;
		}
		if (currentPage > totalPages) {
			currentPage = 1;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("totalRows", totalRows);
		map.put("totalPages", totalPages);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("currentPage", currentPage);
		map.put("keyword", keyword);
		return map;
	}
	

	

}
