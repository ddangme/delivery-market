package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.category.CategoryIdNameResponse;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.dto.goods.response.GoodsResponse;
import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.service.PaginationService;
import com.ddangme.dmadmin.service.goods.CategoryService;
import com.ddangme.dmadmin.service.goods.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final PaginationService paginationService;
    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model,
                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<GoodsListResponse> goods = goodsService.search(pageable);
        List<Integer> pages = paginationService.getPaginationLength(pageable.getPageNumber(), goods.getTotalPages());

        model.addAttribute("goods", goods);
        model.addAttribute("pages", pages);
        return "goods/goods-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        addAndEditFormModel(model);
        return "goods/goods-add";
    }

    @GetMapping("/{goodsId}")
    public String editForm(Model model, @PathVariable Long goodsId) {
        GoodsResponse good = goodsService.findByGoodsId(goodsId);
        List<CategoryIdNameResponse> childCategories = categoryService.findChild(good.getCategory().getParentId());

        model.addAttribute("good", good);
        model.addAttribute("childCategories", childCategories);
        log.info("good={}", good);
        addAndEditFormModel(model);
        return "goods/goods-edit";
    }

    private void addAndEditFormModel(Model model) {
        model.addAttribute("saleStatus", SaleStatus.values());
        model.addAttribute("packagingType", PackagingType.values());
        model.addAttribute("categories",categoryService.findParent());
    }

}
