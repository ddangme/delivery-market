package com.ddangme.dm.controller.member;


import com.ddangme.dm.dto.address.AddressDTO;
import com.ddangme.dm.dto.address.AddressRequest;
import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.dto.member.ModifyMemberRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.service.address.AddressService;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MemberInfoController {

    private final MemberService memberService;
    private final AddressService addressService;
    private final PasswordEncoder encoder;


    @GetMapping({"", "/info/modify"})
    public String modifyInfo(Model model, @AuthenticationPrincipal MemberPrincipal principal) {
//      TODO:  mypage/인 경우 회원, 적립금, DM 캐시, 쿠폰 수량, 찜한 상품 수량 출력되도록 !
        log.info("principal={}", principal);
        ModifyMemberRequest modifyMemberRequest = memberService.findByLoginId(principal.getLoginId())
                .map(ModifyMemberRequest::fromDTO)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_MEMBER));
        model.addAttribute("member", modifyMemberRequest);

        return "member/my-page/modify-info";
    }

    @PostMapping("/info/modify")
    public String modifyInfo(@Valid @ModelAttribute("member") ModifyMemberRequest request, BindingResult bindingResult,
                             @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("request={}", request);
        if (!principal.getLoginId().equals(request.getLoginId())) {
            // TODO: 권한이 없습니다. error 페이지로 이동하기
        }

        if (!encoder.matches(request.getPassword(), principal.getPassword())) {
            bindingResult.rejectValue("password", "NotSame");
        } else if (request.getPassword().equals(request.getNewPassword())) {
            bindingResult.rejectValue("newPassword", "Unable");
        }

        if (request.notEqualsPasswordCheck()) {
            bindingResult.rejectValue("newPassword", "NotSame");
        }


        memberService.findByLoginId(request.getLoginId());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return "member/my-page/modify-info";
        }

        memberService.modifyMember(request, principal.getId());

        return "redirect:/my-page";
    }

    @GetMapping("/address")
    public String addressList(Model model, @AuthenticationPrincipal MemberPrincipal principal) {
        List<AddressDTO> addresses = addressService.findAllByMemberId(principal.getId());

        model.addAttribute("addresses", addresses);
        return "member/my-page/address-list";
    }

    @PostMapping("/address")
    public String addAddress(AddressRequest request, @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("request={}", request.toString());
        addressService.addAddress(request, principal.getId());

        return "redirect:/my-page/address";
    }

    @PostMapping("/address/edit")
    public String EditAddress(AddressRequest request, @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("request={}", request.toString());

        addressService.editAddress(request, principal.getId());

        return "redirect:/my-page/address";
    }
}
