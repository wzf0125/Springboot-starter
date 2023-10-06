package org.quanta.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.api.entity.Goods;
import org.quanta.demo.mapper.GoodsMapper;
import org.quanta.demo.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
}
