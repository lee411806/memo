package com.sparta.memo.controller;


import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api")
@RestController
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto creatMemo(@RequestBody MemoRequestDto requestDto) {

        //RequestDto -> entity
        Memo memo = new Memo(requestDto);


        //maxId 구하기
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        //DB저장
        memoList.put(maxId, memo);

        //Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;

    }


    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {


        //memoList의 value값 모두를 MemoResponseDto 객체로 변환한 후, 이를 리스트(List)로 수집
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        //해당 메모가 DB에 있는지 확인
        if (memoList.containsKey(id)) {
            //해당 메모 가져오기
            Memo memo = memoList.get(id);

            // memo 수정
            memo.update(requestDto);

            return memo.getId();
        } else { //IllegalArgument... 유요하지 않은 매개변수 넘겼을 때 발생
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }

    }


    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        //해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            //해당 메모를 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

}


