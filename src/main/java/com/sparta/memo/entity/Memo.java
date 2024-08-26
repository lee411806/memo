package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id;
    private String username;
    private String contents;


    public Memo(MemoRequestDto requestDto) {
        this.setUsername(requestDto.getUsername());
        this.setContents(requestDto.getContents());
    }


    public void update(MemoRequestDto requestDto) {
        this.setUsername(requestDto.getUsername());
        this.setContents(requestDto.getContents());
    }
}