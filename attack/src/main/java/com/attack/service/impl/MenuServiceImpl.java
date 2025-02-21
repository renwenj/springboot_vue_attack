package com.attack.service.impl;

import com.attack.entity.Menu;
import com.attack.mapper.MenuMapper;
import com.attack.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}