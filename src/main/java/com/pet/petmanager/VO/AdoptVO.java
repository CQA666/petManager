package com.pet.petmanager.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pet.petmanager.entity.Adopt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@EqualsAndHashCode(callSuper = false)是生成hashCode和equals方法的注解，callSuper = false表示不调用父类的equals方法。
@EqualsAndHashCode(callSuper = false)
//Data注解是lombok提供的注解，它会生成get、set方法。
@Data
//NoArgsConstructor注解是lombok提供的注解，它会生成无参构造器。
@NoArgsConstructor
//AllArgsConstructor注解是lombok提供的注解，它会生成全参构造器。
@AllArgsConstructor
//AdoptVO类继承自Adopt类，并添加了userName、animalImg、animalName三个属性。
public class AdoptVO extends Adopt {

    /**
     * 用户表外键
     * 对应的是user表的id字段
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 宠物表外键
     * 对应的是animal表的img字段
     */
    @TableField(exist = false)
    private String animalImg;

    /**
     * 宠物表外键
     * 对应的是animal表的name字段
     */
    @TableField(exist = false)
    private String animalName;

}
