-- 创建宠物管理系统数据库
-- 数据库名: petManager

-- 创建主人表
DROP TABLE IF EXISTS owner;
CREATE TABLE owner (
    id INT PRIMARY KEY AUTO_INCREMENT,
    owner_name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建宠物表
DROP TABLE IF EXISTS pet;
CREATE TABLE pet (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pet_name VARCHAR(50) NOT NULL,
    pet_age INT,
    pet_type VARCHAR(20),
    owner_id INT,
    CONSTRAINT fk_pet_owner FOREIGN KEY (owner_id) REFERENCES owner(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
INSERT INTO owner (id, owner_name) VALUES (1, '张三');
INSERT INTO owner (id, owner_name) VALUES (2, '李四');

INSERT INTO pet (pet_name, pet_age, pet_type, owner_id) VALUES ('旺财', 3, '狗', 1);
INSERT INTO pet (pet_name, pet_age, pet_type, owner_id) VALUES ('咪咪', 2, '猫', 1);
INSERT INTO pet (pet_name, pet_age, pet_type, owner_id) VALUES ('小金', 1, '鱼', 2);
