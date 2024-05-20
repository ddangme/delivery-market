package com.ddangme.dm.service.cash;

import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.cash.CashCharging;
import com.ddangme.dm.model.constants.CashStatus;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.MemberRepository;
import com.ddangme.dm.repository.cash.CashRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CashServiceTest {

    @InjectMocks
    private CashService cashService;

    @Mock
    private CashRepository cashRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 정상_충전_요청() {
        Long amount = 1000L;
        Long memberId = 1L;
        Member member = new Member();

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));

        cashService.cashCharging(memberId, amount);

        then(cashRepository).should().save(any(CashCharging.class));
    }

    @Test
    void 정상_충전_요청_금액_1_000_000() {
        Long amount = 1_000_000L;
        Long memberId = 1L;
        Member member = new Member();

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));

        cashService.cashCharging(memberId, amount);

        then(cashRepository).should().save(any(CashCharging.class));
    }

    @Test
    void 오류_충전_없는_회원() {
        Long amount = 1000L;
        Long memberId = 1L;

        given(memberRepository.findById(memberId))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> cashService.cashCharging(memberId, amount))
                .isInstanceOf(DMException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ACCOUNT.getMessage());
    }

    @NullSource
    @ParameterizedTest
    void 오류_충전금액_null(Long amount) {
        Long memberId = 1L;
        Member member = new Member();

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));

        assertThatThrownBy(() -> cashService.cashCharging(memberId, amount))
                .isInstanceOf(DMException.class)
                .hasMessage(ErrorCode.IS_NULL_CASH_CHARGING_AMOUNT.getMessage());
    }

    @Test
    void 오류_충전금액_0() {
        Long amount = 0L;
        Long memberId = 1L;
        Member member = new Member();

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));

        assertThatThrownBy(() -> cashService.cashCharging(memberId, amount))
                .isInstanceOf(DMException.class)
                .hasMessage(ErrorCode.IS_NULL_CASH_CHARGING_AMOUNT.getMessage());
    }

    @Test
    void 오류_충전금액_1_000_000_초과() {
        Long amount = 1_000_001L;
        Long memberId = 1L;
        Member member = new Member();

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));

        assertThatThrownBy(() -> cashService.cashCharging(memberId, amount))
                .isInstanceOf(DMException.class)
                .hasMessage(ErrorCode.MAX_OVER_CASH_CHARGING_AMOUNT.getMessage());
    }

    @Test
    void 정상_요청_취소() {
        Long amount = 1000L;
        Long memberId = 1L;
        Member member = new Member();
        Long cashChargingId = 1L;
        CashCharging cashCharging = new CashCharging(member, amount, CashStatus.ASK);

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));
        given(cashRepository.findById(cashChargingId))
                .willReturn(Optional.of(cashCharging));

        cashService.cancel(memberId, cashChargingId);

        assertThat(cashCharging.getStatus()).isEqualTo(CashStatus.CANCEL);
    }

    @ParameterizedTest
    @MethodSource("NonCancelCashCharging")
    void 오류_취소_상태가_요청_상태가_아닌_경우(CashStatus cashStatus) {
        Long amount = 1000L;
        Long memberId = 1L;
        Member member = new Member();
        Long cashChargingId = 1L;
        CashCharging cashCharging = new CashCharging(member, amount, cashStatus);

        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(member));
        given(cashRepository.findById(cashChargingId))
                .willReturn(Optional.of(cashCharging));

        assertThatThrownBy(() -> cashService.cancel(memberId, cashChargingId))
                .isInstanceOf(DMException.class)
                .hasMessage(ErrorCode.IS_NON_CANCEL_CASH_CHARGING.getMessage());
    }

    static Stream<Arguments> NonCancelCashCharging() {
        return Stream.of(
                Arguments.of(CashStatus.CANCEL),
                Arguments.of(CashStatus.YES),
                Arguments.of(CashStatus.NO),
                Arguments.of(CashStatus.HOLD)
        );
    }

}