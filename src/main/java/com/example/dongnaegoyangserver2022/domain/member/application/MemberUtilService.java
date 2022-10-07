package com.example.dongnaegoyangserver2022.domain.member.application;

import com.example.dongnaegoyangserver2022.domain.member.dao.MemberRepository;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.domain.member.model.MemberServiceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberUtilService {

    private final MemberRepository memberRepository;

    public MemberResponse.MemberTownResponse updateTown(Member member, MemberServiceModel.MemberUpdateTownModel model){
        member.updateMemberTown(model.getSido(), model.getGugun());
        Member updatedMember = memberRepository.save(member);
        return updatedMember.toMemberTownResponse();
    }

    public MemberResponse.MemberNicknameResponse updateNickname(Member member, MemberServiceModel.MemberUpdateNicknameModel model){
        member.updateMemberNickname(model.getNickname());
        Member updatedMember = memberRepository.save(member);
        return updatedMember.toMemberNicknameResponse();
    }
}
