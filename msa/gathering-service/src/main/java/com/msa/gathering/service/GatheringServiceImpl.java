package com.msa.gathering.service;

import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.GatheringAccountRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.controller.request.GatheringRegisterRequest;
import com.msa.gathering.controller.response.AccountInfoResponse;
import com.msa.gathering.entity.Gathering;
import com.msa.gathering.entity.GatheringApplication;
import com.msa.gathering.entity.GatheringMember;
import com.msa.gathering.repository.GatheringApplicationRepository;
import com.msa.gathering.repository.GatheringMemberRepository;
import com.msa.gathering.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final GatheringApplicationRepository gatheringApplicationRepository;




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

    @Transactional
    @Override
    public void register(GatheringRegisterRequest registerRequest) {


        try {
            Gathering newGathering = new Gathering(registerRequest.getAccountId(), registerRequest.getTeamName(),
                    registerRequest.getTitle(), registerRequest.getDescription(), registerRequest.getMaxMemberCount(),
                    registerRequest.getCurrentMemberCount());

            gatheringRepository.save(newGathering);

            GatheringMember newGatheringMember = new GatheringMember(registerRequest.getAccountId(),
                    registerRequest.getRole(), newGathering, true);

            gatheringMemberRepository.save(newGatheringMember);

        } catch (DataAccessException e) {
            throw new RuntimeException("모임 등록중 오류 발생", e);
        }
    }



}
