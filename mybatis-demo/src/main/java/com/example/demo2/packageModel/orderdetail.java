package packageModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
*
*  @author author
*/
public class orderdetail implements Serializable {

    private static final long serialVersionUID = 1559618299708L;


    /**
    * 主键
    * 
    * isNullAble:0
    */
    private Integer id;

    /**
    * 
    * isNullAble:1
    */
    private Integer orderId;

    /**
    * 
    * isNullAble:1
    */
    private String goodsName;


    public void setId(Integer id){this.id = id;}

    public Integer getId(){return this.id;}

    public void setOrderId(Integer orderId){this.orderId = orderId;}

    public Integer getOrderId(){return this.orderId;}

    public void setGoodsName(String goodsName){this.goodsName = goodsName;}

    public String getGoodsName(){return this.goodsName;}
    @Override
    public String toString() {
        return "orderdetail{" +
                "id='" + id + '\'' +
                "orderId='" + orderId + '\'' +
                "goodsName='" + goodsName + '\'' +
            '}';
    }

    public static Builder Build(){return new Builder();}

    public static ConditionBuilder ConditionBuild(){return new ConditionBuilder();}

    public static UpdateBuilder UpdateBuild(){return new UpdateBuilder();}

    public static QueryBuilder QueryBuild(){return new QueryBuilder();}

    public static class UpdateBuilder {

        private orderdetail set;

        private ConditionBuilder where;

        public UpdateBuilder set(orderdetail set){
            this.set = set;
            return this;
        }

        public orderdetail getSet(){
            return this.set;
        }

        public UpdateBuilder where(ConditionBuilder where){
            this.where = where;
            return this;
        }

        public ConditionBuilder getWhere(){
            return this.where;
        }

        public UpdateBuilder build(){
            return this;
        }
    }

    public static class QueryBuilder extends orderdetail{
        /**
        * 需要返回的列
        */
        private Map<String,Object> fetchFields;

        public Map<String,Object> getFetchFields(){return this.fetchFields;}

        private List<Integer> idList;

        public List<Integer> getIdList(){return this.idList;}

        private Integer idSt;

        private Integer idEd;

        public Integer getIdSt(){return this.idSt;}

        public Integer getIdEd(){return this.idEd;}

        private List<Integer> orderIdList;

        public List<Integer> getOrderIdList(){return this.orderIdList;}

        private Integer orderIdSt;

        private Integer orderIdEd;

        public Integer getOrderIdSt(){return this.orderIdSt;}

        public Integer getOrderIdEd(){return this.orderIdEd;}

        private List<String> goodsNameList;

        public List<String> getGoodsNameList(){return this.goodsNameList;}


        private List<String> fuzzyGoodsName;

        public List<String> getFuzzyGoodsName(){return this.fuzzyGoodsName;}

        private List<String> rightFuzzyGoodsName;

        public List<String> getRightFuzzyGoodsName(){return this.rightFuzzyGoodsName;}
        private QueryBuilder (){
            this.fetchFields = new HashMap<>();
        }

        public QueryBuilder idBetWeen(Integer idSt,Integer idEd){
            this.idSt = idSt;
            this.idEd = idEd;
            return this;
        }

        public QueryBuilder idGreaterEqThan(Integer idSt){
            this.idSt = idSt;
            return this;
        }
        public QueryBuilder idLessEqThan(Integer idEd){
            this.idEd = idEd;
            return this;
        }


        public QueryBuilder id(Integer id){
            setId(id);
            return this;
        }

        public QueryBuilder idList(Integer ... id){
            this.idList = solveNullList(id);
            return this;
        }

        public QueryBuilder idList(List<Integer> id){
            this.idList = id;
            return this;
        }

        public QueryBuilder fetchId(){
            setFetchFields("fetchFields","id");
            return this;
        }

        public QueryBuilder excludeId(){
            setFetchFields("excludeFields","id");
            return this;
        }

        public QueryBuilder orderIdBetWeen(Integer orderIdSt,Integer orderIdEd){
            this.orderIdSt = orderIdSt;
            this.orderIdEd = orderIdEd;
            return this;
        }

        public QueryBuilder orderIdGreaterEqThan(Integer orderIdSt){
            this.orderIdSt = orderIdSt;
            return this;
        }
        public QueryBuilder orderIdLessEqThan(Integer orderIdEd){
            this.orderIdEd = orderIdEd;
            return this;
        }


        public QueryBuilder orderId(Integer orderId){
            setOrderId(orderId);
            return this;
        }

        public QueryBuilder orderIdList(Integer ... orderId){
            this.orderIdList = solveNullList(orderId);
            return this;
        }

        public QueryBuilder orderIdList(List<Integer> orderId){
            this.orderIdList = orderId;
            return this;
        }

        public QueryBuilder fetchOrderId(){
            setFetchFields("fetchFields","orderId");
            return this;
        }

        public QueryBuilder excludeOrderId(){
            setFetchFields("excludeFields","orderId");
            return this;
        }

        public QueryBuilder fuzzyGoodsName (List<String> fuzzyGoodsName){
            this.fuzzyGoodsName = fuzzyGoodsName;
            return this;
        }

        public QueryBuilder fuzzyGoodsName (String ... fuzzyGoodsName){
            this.fuzzyGoodsName = solveNullList(fuzzyGoodsName);
            return this;
        }

        public QueryBuilder rightFuzzyGoodsName (List<String> rightFuzzyGoodsName){
            this.rightFuzzyGoodsName = rightFuzzyGoodsName;
            return this;
        }

        public QueryBuilder rightFuzzyGoodsName (String ... rightFuzzyGoodsName){
            this.rightFuzzyGoodsName = solveNullList(rightFuzzyGoodsName);
            return this;
        }

        public QueryBuilder goodsName(String goodsName){
            setGoodsName(goodsName);
            return this;
        }

        public QueryBuilder goodsNameList(String ... goodsName){
            this.goodsNameList = solveNullList(goodsName);
            return this;
        }

        public QueryBuilder goodsNameList(List<String> goodsName){
            this.goodsNameList = goodsName;
            return this;
        }

        public QueryBuilder fetchGoodsName(){
            setFetchFields("fetchFields","goodsName");
            return this;
        }

        public QueryBuilder excludeGoodsName(){
            setFetchFields("excludeFields","goodsName");
            return this;
        }
        private <T>List<T> solveNullList(T ... objs){
            if (objs != null){
            List<T> list = new ArrayList<>();
                for (T item : objs){
                    if (item != null){
                        list.add(item);
                    }
                }
                return list;
            }
            return null;
        }

        public QueryBuilder fetchAll(){
            this.fetchFields.put("AllFields",true);
            return this;
        }

        public QueryBuilder addField(String ... fields){
            List<String> list = new ArrayList<>();
            if (fields != null){
                for (String field : fields){
                    list.add(field);
                }
            }
            this.fetchFields.put("otherFields",list);
            return this;
        }
        @SuppressWarnings("unchecked")
        private void setFetchFields(String key,String val){
            Map<String,Boolean> fields= (Map<String, Boolean>) this.fetchFields.get(key);
            if (fields == null){
                fields = new HashMap<>();
            }
            fields.put(val,true);
            this.fetchFields.put(key,fields);
        }

        public orderdetail build(){return this;}
    }


    public static class ConditionBuilder{
        private List<Integer> idList;

        public List<Integer> getIdList(){return this.idList;}

        private Integer idSt;

        private Integer idEd;

        public Integer getIdSt(){return this.idSt;}

        public Integer getIdEd(){return this.idEd;}

        private List<Integer> orderIdList;

        public List<Integer> getOrderIdList(){return this.orderIdList;}

        private Integer orderIdSt;

        private Integer orderIdEd;

        public Integer getOrderIdSt(){return this.orderIdSt;}

        public Integer getOrderIdEd(){return this.orderIdEd;}

        private List<String> goodsNameList;

        public List<String> getGoodsNameList(){return this.goodsNameList;}


        private List<String> fuzzyGoodsName;

        public List<String> getFuzzyGoodsName(){return this.fuzzyGoodsName;}

        private List<String> rightFuzzyGoodsName;

        public List<String> getRightFuzzyGoodsName(){return this.rightFuzzyGoodsName;}

        public ConditionBuilder idBetWeen(Integer idSt,Integer idEd){
            this.idSt = idSt;
            this.idEd = idEd;
            return this;
        }

        public ConditionBuilder idGreaterEqThan(Integer idSt){
            this.idSt = idSt;
            return this;
        }
        public ConditionBuilder idLessEqThan(Integer idEd){
            this.idEd = idEd;
            return this;
        }


        public ConditionBuilder idList(Integer ... id){
            this.idList = solveNullList(id);
            return this;
        }

        public ConditionBuilder idList(List<Integer> id){
            this.idList = id;
            return this;
        }

        public ConditionBuilder orderIdBetWeen(Integer orderIdSt,Integer orderIdEd){
            this.orderIdSt = orderIdSt;
            this.orderIdEd = orderIdEd;
            return this;
        }

        public ConditionBuilder orderIdGreaterEqThan(Integer orderIdSt){
            this.orderIdSt = orderIdSt;
            return this;
        }
        public ConditionBuilder orderIdLessEqThan(Integer orderIdEd){
            this.orderIdEd = orderIdEd;
            return this;
        }


        public ConditionBuilder orderIdList(Integer ... orderId){
            this.orderIdList = solveNullList(orderId);
            return this;
        }

        public ConditionBuilder orderIdList(List<Integer> orderId){
            this.orderIdList = orderId;
            return this;
        }

        public ConditionBuilder fuzzyGoodsName (List<String> fuzzyGoodsName){
            this.fuzzyGoodsName = fuzzyGoodsName;
            return this;
        }

        public ConditionBuilder fuzzyGoodsName (String ... fuzzyGoodsName){
            this.fuzzyGoodsName = solveNullList(fuzzyGoodsName);
            return this;
        }

        public ConditionBuilder rightFuzzyGoodsName (List<String> rightFuzzyGoodsName){
            this.rightFuzzyGoodsName = rightFuzzyGoodsName;
            return this;
        }

        public ConditionBuilder rightFuzzyGoodsName (String ... rightFuzzyGoodsName){
            this.rightFuzzyGoodsName = solveNullList(rightFuzzyGoodsName);
            return this;
        }

        public ConditionBuilder goodsNameList(String ... goodsName){
            this.goodsNameList = solveNullList(goodsName);
            return this;
        }

        public ConditionBuilder goodsNameList(List<String> goodsName){
            this.goodsNameList = goodsName;
            return this;
        }

        private <T>List<T> solveNullList(T ... objs){
            if (objs != null){
            List<T> list = new ArrayList<>();
                for (T item : objs){
                    if (item != null){
                        list.add(item);
                    }
                }
                return list;
            }
            return null;
        }

        public ConditionBuilder build(){return this;}
    }

    public static class Builder {

        private orderdetail obj;

        public Builder(){
            this.obj = new orderdetail();
        }

        public Builder id(Integer id){
            this.obj.setId(id);
            return this;
        }
        public Builder orderId(Integer orderId){
            this.obj.setOrderId(orderId);
            return this;
        }
        public Builder goodsName(String goodsName){
            this.obj.setGoodsName(goodsName);
            return this;
        }
        public orderdetail build(){return obj;}
    }

}
