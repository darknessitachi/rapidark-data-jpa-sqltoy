package com.rapidark.soa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Darkness
 * @date 2020年10月25日 下午3:21:35
 * @version V1.0
 */
@Data
@Entity
@Table(name = "staff") // 注解的name属性值要和schema.sql定义的表名一致。不然启动虽然不报错，但和表映射不上，就取不到任何值。
public class StaffEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键是必须声明的。不然启动会报实体中无定义主键的错： No identifier specified for entity:
	// com.zlf.bo.StaffBo
	@Id
	private String id;
//  @Column(name="name") 如果表中字段名称和这里的属性名称一样，可以不同加Column注解。
	private String name;
	@Column(name = "age")
	private int age;
	
}
