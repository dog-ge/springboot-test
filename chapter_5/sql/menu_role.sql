-- 菜单表
CREATE TABLE `menu` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `pattern` VARCHAR(255) NOT NULL COMMENT '访问路径模式'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 角色表
CREATE TABLE `role` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL COMMENT '角色英文名',
    `name_zh` VARCHAR(255) NOT NULL COMMENT '角色中文名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单角色关联表（带自增ID）
CREATE TABLE `menu_role` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    `mid` INT NOT NULL COMMENT '菜单ID',
    `rid` INT NOT NULL COMMENT '角色ID',
    UNIQUE KEY `uk_mid_rid` (`mid`, `rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单角色关联表';

INSERT INTO `menu` (`id`, `pattern`) VALUES
(1, '/admin/**'),
(2, '/user/**'),
(3, '/guest/**');

INSERT INTO `role` (`id`, `name`, `name_zh`) VALUES
(1, 'ROLE_ADMIN', '管理员'),
(2, 'ROLE_USER', '普通用户'),
(3, 'ROLE_GUEST', '访客');

INSERT INTO `menu_role` (`mid`, `rid`) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3);