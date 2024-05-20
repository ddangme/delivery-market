package com.ddangme.dmadmin.dto.cash;

import com.ddangme.dmadmin.model.constants.CashStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CashChangeRequest {

    List<Long> ids = new ArrayList<>();
    CashStatus status;
}
