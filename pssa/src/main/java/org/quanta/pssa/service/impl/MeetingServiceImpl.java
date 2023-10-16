package org.quanta.pssa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.pssa.entity.Meeting;
import org.quanta.pssa.mapper.MeetingMapper;
import org.quanta.pssa.service.MeetingService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements MeetingService {
}
