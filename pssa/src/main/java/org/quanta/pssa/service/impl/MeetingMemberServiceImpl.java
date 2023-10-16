package org.quanta.pssa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.pssa.entity.MeetingMember;
import org.quanta.pssa.mapper.MeetingMemberMapper;
import org.quanta.pssa.service.MeetingMemberService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Service
public class MeetingMemberServiceImpl extends ServiceImpl<MeetingMemberMapper, MeetingMember> implements MeetingMemberService {
}
