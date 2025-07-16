package com.msa.gathering.controller.response;

import com.msa.gathering.entity.ContactInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoResponse {
    private Long id;
    private Long gatheringId;
    private Long accountId;
    private String phoneNumber;
    private String email;
    private String openChatLink;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static ContactInfoResponse from(ContactInfo contactInfo) {
        return ContactInfoResponse.builder()
                .id(contactInfo.getId())
                .gatheringId(contactInfo.getGathering().getId())
                .accountId(contactInfo.getAccountId())
                .phoneNumber(contactInfo.getPhoneNumber())
                .email(contactInfo.getEmail())
                .openChatLink(contactInfo.getOpenChatLink())
                .additionalInfo(contactInfo.getAdditionalInfo())
                .createdAt(contactInfo.getCreatedAt())
                .updatedAt(contactInfo.getUpdatedAt())
                .build();
    }
}
