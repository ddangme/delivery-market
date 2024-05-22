package com.ddangme.dmadmin.service.good;

import com.ddangme.dmadmin.dto.good.request.*;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodValidateService {

    public void valid(GoodRequest request) {
        validGood(request);
        validDetail(request.getGoodsDetail());
        validOption(request.getGoodsOptions());
    }

    public void validIds(GoodRequest request) {
        if (request.getId() == null) {
            throw new DMAdminException(ErrorCode.NOT_EXIST_GOOD);
        }
        if (request.getGoodsDetail().getId() == null) {
            throw new DMAdminException(ErrorCode.NOT_EXIST_GOOD_DETAIL);
        }
        for (GoodOptionRequest goodsOption : request.getGoodsOptions()) {
            if (goodsOption.getId() == null) {
                throw new DMAdminException(ErrorCode.NOT_EXIST_GOOD_OPTION);
            }
        }
    }

    private void validGood(GoodRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new DMAdminException(ErrorCode.GOOD_NAME_IS_NULL);
        }
        if (request.getCategoryId() == null) {
            throw new DMAdminException(ErrorCode.NOT_CHOICE_CATEGORY);
        }
        if (request.getSummary() == null || request.getSummary().isEmpty()) {
            throw new DMAdminException(ErrorCode.GOOD_SUMMARY_IS_NULL);
        }
        if (request.getSaleStatus() == null) {
            throw new DMAdminException(ErrorCode.GOOD_SALE_STATUS_IS_NULL);
        }
        if (request.getPrice() == null) {
            throw new DMAdminException(ErrorCode.GOOD_PRICE_IS_NULL);
        }
        if (request.getGoodsOptions() == null || request.getGoodsOptions().isEmpty()) {
            throw new DMAdminException(ErrorCode.GOOD_OPTION_IS_NULL);
        }

    }

    private void validDetail(GoodDetailRequest request) {
        if (request.getPackagingType() == null) {
            throw new DMAdminException(ErrorCode.GOOD_PACKAGING_TYPE_IS_NULL);
        }
        if (request.getWeightVolume() == null || request.getWeightVolume().isEmpty()) {
            throw new DMAdminException(ErrorCode.GOOD_WEIGHT_VOLUME_IS_NULL);
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new DMAdminException(ErrorCode.GOOD_DESCRIPTION_IS_NULL);
        }
    }

    private void validOption(List<GoodOptionRequest> requests) {
        for (GoodOptionRequest request : requests) {
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new DMAdminException(ErrorCode.GOOD_OPTION_NAME_IS_NULL);
            }
            if (request.getSaleStatus() == null) {
                throw new DMAdminException(ErrorCode.GOOD_OPTION_SALE_STATUS_IS_NULL);
            }
            if (request.getPrice() == null) {
                throw new DMAdminException(ErrorCode.GOOD_OPTION_PRICE_IS_NULL);
            }
            if (request.getQuantity() == null) {
                throw new DMAdminException(ErrorCode.GOOD_OPTION_AMOUNT_IS_NULL);
            }
        }
    }

}
