package org.quanta.demo.controller;

import lombok.RequiredArgsConstructor;
import org.quanta.api.controller.IGoodsController;
import org.quanta.api.entity.Goods;
import org.quanta.controller.BaseController;
import org.quanta.core.beans.Response;
import org.quanta.demo.service.IGoodsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@RestController
@RequiredArgsConstructor
public class GoodsController extends BaseController implements IGoodsController {
    private final IGoodsService goodsService;

    @Override
    public Response<?> getGoodsList() {
        return Response.success(goodsService.list());
    }

    @Override
    public Response<?> getGoodsById(Long id) {
        return Response.success(goodsService.lambdaQuery().eq(Goods::getId,id).one());
    }
}
