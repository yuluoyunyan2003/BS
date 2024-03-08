package bs.www.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author w雨落云烟w
 * @date 2023/12/14 16:15
 * @description: 数据库集合Goods的实体类
 */
@Data   //lombok的注解,负责自动增加getter和setter注解,重写toString和hashCode方法
@NoArgsConstructor  //lombok的注解,负责自动增加无参构造
@AllArgsConstructor //lombok的注解,负责自动增加有参构造
@Document(collection = "goods") //将该对象声明为要持久化到MongoDB中的文档
public class Goods {
    @Id
    private String _id;
    private String name;
    private String price;
    private String imgSrc;
    private String type;
}
