package com.msa.gathering.service;

import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.GatheringAccountRequest;
import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.controller.response.AccountInfoResponse;
import com.msa.gathering.entity.Gathering;
import com.msa.gathering.entity.GatheringMember;
import com.msa.gathering.repository.GatheringMemberRepository;
import com.msa.gathering.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatheringServiceImpl implements GatheringService {

    private final AccountClient accountClient;
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;




    @Override
    public List<GatheringListRequest> getList() {
        List<Gathering> allGatherings = gatheringRepository.findAll();

        List<Long> gatheringIds = allGatherings.stream()
                .map(Gathering::getId)
                .toList();

        List<GatheringMember> allMembers = gatheringMemberRepository.findByGatheringIdIn(gatheringIds);

        List<GatheringAccountRequest> accountRequests = allMembers.stream()
                .map(m -> new GatheringAccountRequest(
                        m.getAccountId(),
                        m.getGathering().getId(),
                        m.getRole(),
                        m.isHost()))
                .toList();

        List<AccountInfoResponse> accountInfos = accountClient.getList(accountRequests);

        Map<Long, List<AccountInfoResponse>> groupedByGatheringId = accountInfos.stream()
                .collect(Collectors.groupingBy(AccountInfoResponse::getGatheringId));

        Map<Long, Gathering> gatheringMap = allGatherings.stream()
                .collect(Collectors.toMap(Gathering::getId, Function.identity()));

        List<GatheringListRequest> result = groupedByGatheringId.entrySet().stream()
                .map(entry -> {
                    Long gatheringId = entry.getKey();
                    List<AccountInfoResponse> members = entry.getValue();

                    Gathering g = gatheringMap.get(gatheringId);
                    if (g == null) throw new RuntimeException("Gathering not found");

                    return new GatheringListRequest(
                            gatheringId,
                            members,
                            g.getTeamName(),
                            g.getTitle(),
                            g.getMaxMemberCount(),
                            g.getCurrentMemberCount()
                    );
                })
                .toList();

        return result;
    }

}
