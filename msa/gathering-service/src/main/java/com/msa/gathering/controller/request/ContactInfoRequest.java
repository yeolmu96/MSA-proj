package com.msa.gathering.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoRequest {
    private Long gatheringId;
    private Long accountId;
    private String phoneNumber;
    private String email;
    private String openChatLink;
    private String additionalInfo;
}
