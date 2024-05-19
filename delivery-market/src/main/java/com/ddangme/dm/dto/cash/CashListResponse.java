package com.ddangme.dm.dto.cash;

import com.ddangme.dm.model.cash.CashCharging;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CashListResponse {

    private Long id;
    private Long amount;
    private String status;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;


    public static CashListResponse fromEntity(CashCharging entity) {
        return new CashListResponse(
                entity.getId(),
                entity.getAmount(),
                entity.getStatus().getStatus(),
                entity.getRequestAt(),
                entity.getResponseAt()
        );
    }

}
