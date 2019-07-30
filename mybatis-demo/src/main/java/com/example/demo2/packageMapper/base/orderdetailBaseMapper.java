package packageMapper.base;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import packageModel.orderdetail;
/**
*  @author author
*/
public interface orderdetailBaseMapper {

    int insertorderdetail(orderdetail object);

    int updateorderdetail(orderdetail object);

    int update(orderdetail.UpdateBuilder object);

    List<orderdetail> queryorderdetail(orderdetail object);

    orderdetail queryorderdetailLimit1(orderdetail object);

}