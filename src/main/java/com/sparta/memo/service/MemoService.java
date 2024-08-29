package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemoService {


    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }




    public MemoResponseDto creatMemo(MemoRequestDto requestDto) {


        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);


        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }
    public List<MemoResponseDto> getMemos() {


        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();


    }

    public List<MemoResponseDto> getMemosByKeword(String keyword) {
        return memoRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {


        // Reposiotory 반환 값 -> optional
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // memo 내용 수정 (update아니고 jpa자체 변경감지)
        memo.update(requestDto);

        return id;
    }


    public Long deleteMemo(Long id) {


        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);


        // memo 삭제
        memoRepository.delete(memo);


        return id;
    }


    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
