package com.example.xing.service.impl;

import com.example.xing.common.SysConstant;
import com.example.xing.dao.SysMenuDao;
import com.example.xing.dao.SysRoleMenuDao;
import com.example.xing.dto.MenuDTO;
import com.example.xing.dto.mapper.SysMenuMapper;
import com.example.xing.entity.QSysMenu;
import com.example.xing.entity.QSysRoleMenu;
import com.example.xing.entity.QSysUserRole;
import com.example.xing.entity.SysMenu;
import com.example.xing.entity.SysRoleMenu;
import com.example.xing.form.SaveMenuForm;
import com.example.xing.service.SysMenuService;
import com.example.xing.vo.SysMenuVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu, Long, SysMenuDao> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    //顶级菜单id
    private static final Long ROOT_MENU_ID = new Long(0l);

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    /**
     * 获得用户权限菜单树
     *
     * @param userId
     * @return
     */
    public Object getRoleMenuTree(Long userId) {
        QSysRoleMenu sysRoleMenu = QSysRoleMenu.sysRoleMenu;
        QSysMenu sysMenu = QSysMenu.sysMenu;
        QSysUserRole sysUserRole = QSysUserRole.sysUserRole;

        JPAQueryFactory queryFactory = getQueryFactory();

        //查询用户权限菜单(此处需要去重)
        List<SysMenu> userMenus = queryFactory.select(sysMenu).from(sysMenu)
                .leftJoin(sysRoleMenu)
                .on(sysMenu.menuId.eq(sysRoleMenu.menuId))
                .leftJoin(sysUserRole)
                .on(sysUserRole.roleId.eq(sysRoleMenu.roleId))
                .where(sysUserRole.userId.eq(userId).and(sysMenu.parentId.ne(ROOT_MENU_ID)))
                .orderBy(sysMenu.orderNum.asc())
                .distinct()
                .fetch();

        //整理父级菜单和子集菜单对应关系
        HashMap<Long, List<MenuDTO>> map = Maps.newHashMap();

        if (null != userMenus) {
            userMenus.stream().forEachOrdered(menu -> {
                MenuDTO.Meta meta = new MenuDTO.Meta();
                meta.setIcon(menu.getIcon());
                meta.setTitle(menu.getTitle());
                meta.setHideInMenu(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(menu.getHideMenu())));
                MenuDTO menuDTO = new MenuDTO();
                menuDTO.setName(menu.getName());
                menuDTO.setPath(menu.getPath());
                menuDTO.setComponent(menu.getUrl());
                menuDTO.setMeta(meta);

                List<MenuDTO> list = map.getOrDefault(menu.getParentId(), Lists.newArrayList());
                list.add(menuDTO);
                map.put(menu.getParentId(), list);
            });
        }

        //查询一级菜单
        Set<Long> rootIds = map.keySet();

        List<SysMenu> rootMenu = findAll(sysMenu.menuId.in(rootIds), new Sort(Sort.Direction.ASC, "orderNum"));

        //给一级菜单填充有权限的子菜单

        List<MenuDTO> treeMenus = Lists.newArrayList();
        rootMenu.stream().forEachOrdered(m -> {

            MenuDTO.Meta meta = new MenuDTO.Meta();
            meta.setIcon(m.getIcon());
            meta.setTitle(m.getTitle());
            meta.setHideInMenu(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(m.getHideMenu())));

            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setName(m.getName());
            menuDTO.setPath(m.getPath());
            menuDTO.setComponent(m.getUrl());
            menuDTO.setMeta(meta);

            List<MenuDTO> childrenMenu = map.get(m.getMenuId());
            menuDTO.setChildren(childrenMenu);

            treeMenus.add(menuDTO);
        });

        return treeMenus;
    }

    /**
     * 无权限的菜单树
     *
     * @return
     */
    @Override
    public Object getTreeMenu() {

        BooleanExpression expression = QSysMenu.sysMenu.type.eq(1).or(QSysMenu.sysMenu.type.eq(0));
        List<SysMenu> menus = findAll(expression, new Sort(Sort.Direction.ASC, "orderNum"));
        List<MenuDTO> list = menus.stream().filter((i) -> i.getParentId() == 0).map((item) -> {
            MenuDTO.Meta meta = new MenuDTO.Meta();
            meta.setIcon(item.getIcon());
            meta.setTitle(item.getTitle());
            meta.setHideInMenu(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(item.getHideMenu())));

            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setName(item.getName());
            menuDTO.setPath(item.getPath());
            menuDTO.setComponent(item.getUrl());
            menuDTO.setMeta(meta);

            List<MenuDTO> Children = getChildren(item.getMenuId(), menus);
            menuDTO.setChildren(Children);
            return menuDTO;
        }).collect(Collectors.toList());

        return list;
    }

    private List<MenuDTO> getChildren(Long parentId, List<SysMenu> menus) {
        return menus.stream().filter((i) -> parentId == i.getParentId()).map((item) -> {
            MenuDTO.Meta meta = new MenuDTO.Meta();
            meta.setIcon(item.getIcon());
            meta.setTitle(item.getTitle());
            meta.setHideInMenu(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(item.getHideMenu())));
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setName(item.getName());
            menuDTO.setPath(item.getPath());
            menuDTO.setComponent(item.getUrl());
            menuDTO.setMeta(meta);
            return menuDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuVO> findAllMenus() {
        try {
            BooleanExpression expression = QSysMenu.sysMenu.type.eq(1).or(QSysMenu.sysMenu.type.eq(0));
            List<SysMenu> menus = findAll(expression, new Sort(Sort.Direction.ASC, "orderNum"));
            List<SysMenuVO> menuVO = Lists.newArrayList();
            return createTreeMenu(menus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<SysMenuVO> createTreeMenu(List<SysMenu> menus) {
        List<SysMenuVO> menuVOs = Lists.newArrayList();

        if (CollectionUtils.isEmpty(menus)) {
            return menuVOs;
        }

        for (SysMenu tmpMenu : menus) {
            if (ROOT_MENU_ID.equals(tmpMenu.getParentId())) {
                loopMenu(null, menuVOs, tmpMenu, menus);
            }
        }

        return menuVOs;

    }

    private void loopMenu(SysMenuVO parent, List<SysMenuVO> menuVOs, SysMenu tmpMenu,
                          List<SysMenu> allMenus) {

        SysMenuVO item = new SysMenuVO();
        menuVOs.add(item);
        item.setId(tmpMenu.getMenuId().toString());
        item.setPerms(tmpMenu.getPerms());
        item.setTitle(tmpMenu.getTitle());
        item.setIcon(tmpMenu.getIcon());
        item.setOpen(false);
        item.setUrl(tmpMenu.getUrl());
        item.setShow(true);
        item.setHidemenu(tmpMenu.getHideMenu());
        item.setOrdernum(tmpMenu.getOrderNum());
        item.setParentid(tmpMenu.getParentId());
        item.setPath(tmpMenu.getPath());
        item.setName(tmpMenu.getName());
        //去查找子集菜单
        List<SysMenu> children = findChildren(tmpMenu, allMenus);
        if (!CollectionUtils.isEmpty(children)) {
            List<SysMenuVO> childrenList = Lists.newArrayList();
            item.setChildren(childrenList);
            for (SysMenu child : children) {
                loopMenu(item, childrenList, child, allMenus);
            }
        }

    }

    private List<SysMenu> findChildren(SysMenu tmpMenu, List<SysMenu> allMenus) {
        List<SysMenu> children = Lists.newArrayList();
        for (SysMenu prvMenu : allMenus) {
            if (prvMenu.getParentId().longValue() == tmpMenu.getMenuId().longValue()) {
                children.add(prvMenu);
            }
        }

        return children;
    }

    /**
     * 新增修改菜单
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public void save(SaveMenuForm form) throws Exception {
        Assert.notNull(form, "菜单信息不能为空");
        if (null != form.getMenuId()) {

            SysMenu sysmenu = this.findOne(form.getMenuId());
            //修改数据

            sysMenuMapper.copyTo(form, sysmenu);

            //更新
            save(sysmenu);

        } else {
            //新增菜单
            SysMenu tbSysMenu = sysMenuMapper.from(form);
            if (null == form.getParentId()) {
                tbSysMenu.setParentId(ROOT_MENU_ID);
            }
            tbSysMenu = save(tbSysMenu);

            // 每新增一个菜单, 自动给超级管理员角色赋权
            SysRoleMenu menu = new SysRoleMenu();
            menu.setMenuId(tbSysMenu.getMenuId());
            menu.setRoleId(SysConstant.SUPER_ROLE_ID);
            sysRoleMenuDao.save(menu);

        }

    }
}
