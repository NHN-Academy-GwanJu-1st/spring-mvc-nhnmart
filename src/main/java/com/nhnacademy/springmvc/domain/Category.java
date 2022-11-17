package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    Dissatisfaction("dissatisfaction", "불만접수"),
    Proposal("proposal", "제안"),
    Refund("refund", "환뷸/교환"),
    Compliment("compliment", "칭찬해요"),
    Etc("etc", "기타문의");

    private final String key;
    private final String value;

}
