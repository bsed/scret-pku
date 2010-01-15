package easyJ.database.session;

import java.util.ArrayList;
import easyJ.database.dao.Page;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.command.UpdateCommand;

public interface Session {
    /**
     * <p>
     * 用来在数据库中新增一条记录，
     * </p>
     * 
     * @param object
     *                Object 要创建的对象，需要符合命名规范
     * @see <a href="../NameRule.html">命名规范 </a>
     * @throws EasyJException
     * @return Object 创建之后具有主键的对象。
     */
    public Object create(Object object) throws easyJ.common.EasyJException;

    /**
     * 设置查询时哪些字段要进行精确查询。如果不设置，则都是使用模糊匹配。
     * 
     * @param properties
     */
    public void setAccurateProperties(String[] properties);

    /**
     * <p>
     * 以参数object作为条件，用来从数据库中取得一条记录
     * </p>
     * 
     * @param object
     *                Object 此Object的主键值不能为空，因为此方法是依据object的主键值从数据库中取得数据的。
     * @throws EasyJException
     *                 如果根据主键没有找到数据则抛出数据不存在的异常。
     * @return Object 返回从数据库中得到的数据。
     */
    public Object get(Object object) throws easyJ.common.EasyJException;

    /**
     * <p>
     * 以参数object的主键值作为更新条件，更新所有的属性。
     * </p>
     * <p>
     * 需要注意的是因为要更新所有的属性，所以用户在使用此方法前应该先调用get(Object
     * object)方法。然后再将新的数据设置到从数据库中得到的object中，然后再执行update(Object object)方法
     * </p>
     * 
     * @param object
     *                Object
     *                此Object的主键值不能为空，因为此方法是依据object的主键值更新数据，其他属性不作为更新的条件。
     * @throws EasyJException
     *                 如果主键值为空则抛出异常
     */
    public void update(Object object) throws easyJ.common.EasyJException;

    /**
     * <p>
     * 只有当用户需要特殊的更新语句时才使用此方法，否则只是依据主键更新一条数据的话，推荐使用update(Object object)方法。
     * </p>
     * 
     * @param ucmd
     *                UpdateCommand 更新语句，依据此语句对数据库进行更新。
     * @throws EasyJException
     */
    public void update(UpdateCommand ucmd) throws easyJ.common.EasyJException;

    /**
     * <p>
     * 以参数object的主键值作为删除条件，删除一条记录。
     * </p>
     * 
     * @param object
     *                Object 此object的主键值不能为空，其他属性值不作为删除的条件
     * @throws EasyJException
     *                 如果主键值为空则抛出异常
     */
    public void delete(Object object) throws easyJ.common.EasyJException;

    /**
     * 此方法进行批量删除数据，可以提高效率。"
     * 
     * @param clazz
     *                Class 要删除的表对应的class
     * @param primaryKeys
     *                Object[] 要删除的主键数组
     * @throws EasyJException
     */
    public void deleteBatch(Class clazz, Object[] primaryKeys)
            throws easyJ.common.EasyJException;

    /**
     * 这个是真正的数据库删除，只在进行多数据选择的时候使用，慎重，慎重。。。。
     * 参见SingleDataAction中的multiSelectConfirm。
     * 
     * @param condition
     *                要删除的条件
     * @throws easyJ.common.EasyJException
     */
    public void deleteBatch(Object condition)
            throws easyJ.common.EasyJException;

    /**
     * 如果需要多次对数据库操作，则需要开启事务，此方法用来开始一个数据库事务。
     * 
     * @throws EasyJException
     * @return Transaction 返回用于事务控制的Transaction对象。
     */
    public Transaction beginTransaction() throws easyJ.common.EasyJException;

    /**
     * session完成之后需要关闭，因为session会占用链接是宝贵的资源，此方法用来关闭一个session。
     * 
     * @throws EasyJException
     */

    public void close() throws easyJ.common.EasyJException;

    /**
     * 此方法用来对数据进行查询
     * 
     * @param className
     *                String 进行查询的数据库表对应的类
     * @param condition
     *                String 进行查询的条件
     *                里面是直接执行的条件语句，用的是数据库字段名而不是属性名，例如：student_name like '%liu%'
     * @param orderbyClauses
     *                String 进行排序的语句，用的也是数据库字段名，例如：order by student_name
     * @param currentPageNo
     *                int 用来标识需要的第几页数据
     * @throws EasyJException
     * @return Page 返回符合条件的处于第currentPageNo页的数据。
     */
    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo) throws EasyJException;

    /**
     * 此方法用来对数据进行查询
     * 
     * @param className
     *                String 进行查询的数据库表对应的类
     * @param conditions
     *                String 进行查询的条件
     *                里面是直接执行的条件语句，用的是数据库字段名而不是属性名，例如：student_name like '%liu%'
     * @param orderbyClauses
     *                String 进行排序的语句，用的也是数据库字段名，例如：order by student_name
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合。
     */
    public ArrayList query(String className, String conditions,
            String orderbyClauses) throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param object
     *                Object object封装了所有的查询条件。
     * @throws EasyJException
     * @return ArrayList 返回满足条件的数据集合
     */
    public ArrayList query(Object object) throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param object
     *                Object object封装了所有的查询条件。
     * @param orderRules
     *                OrderRule[] 用来进行排序的OrderRule数组
     * @see <a href="../dao/OrderRule.html">OrderRule</a>
     * @throws EasyJException
     * @return ArrayList 返回满足条件的数据集合
     */
    public ArrayList query(Object object, OrderRule[] orderRules)
            throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param lower
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @param upper
     *                Object 用来封装查询条件的上界
     *                例如：如果查询年龄在30到40岁之间的人，那么在upper的age属性中存放值为40
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合。
     */
    public ArrayList query(Object lower, Object upper) throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param lower
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @param upper
     *                Object 用来封装查询条件的上界
     *                例如：如果查询年龄在30到40岁之间的人，那么在upper的age属性中存放值为40
     * @param orderRules
     *                OrderRule[] 用来进行排序的OrderRule数组
     * @see <a href="../dao/OrderRule.html">OrderRule</a>
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合。
     */
    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param object
     *                Object object封装了所有的查询条件。
     * @param page
     *                int 用来指示当前要查看位于第page页的数据。
     * @throws EasyJException
     * @return Page 返回一个Page对象，对象中包含了第page页的数据，以及总数据条数等。
     * @see <a href="../dao/Page.html">Page</a>
     */
    public Page query(Object object, int page) throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param object
     *                Object object封装了所有的查询条件。
     * @param page
     *                int 用来指示当前要查看位于第page页的数据。
     * @param orderRules
     *                OrderRule[] 用来进行排序的OrderRule数组
     * @see <a href="../dao/OrderRule.html">OrderRule</a>
     * @throws EasyJException
     * @return Page 返回一个Page对象，对象中包含了第page页的数据，以及总数据条数等。
     * @see <a href="../dao/Page.html">Page</a>
     */
    public Page query(Object object, int page, OrderRule[] orderRules)
            throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param lower
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @param upper
     *                Object 用来封装查询条件的上界
     *                例如：如果查询年龄在30到40岁之间的人，那么在upper的age属性中存放值为40
     * @param page
     *                int 用来指示当前要查看位于第page页的数据。
     * @throws EasyJException
     * @return Page 返回一个Page对象，对象中包含了第page页的数据，以及总数据条数等。
     * @see <a href="../dao/Page.html">Page</a>
     */
    public Page query(Object lower, Object upper, int page)
            throws EasyJException;

    /**
     * 此方法对数据进行查询
     * 
     * @param lower
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @param upper
     *                Object 用来封装查询条件的上界
     *                例如：如果查询年龄在30到40岁之间的人，那么在upper的age属性中存放值为40
     * @param currentPageNo
     *                int 用来标识需要的第几页数据
     * @param orderRules
     *                OrderRule[] 用来进行排序的OrderRule数组
     * @see <a href="../dao/OrderRule.html">OrderRule</a>
     * @throws EasyJException
     * @return Page 返回一个Page对象，对象中包含了第page页的数据，以及总数据条数等。
     * @see <a href="../dao/Page.html">Page</a>
     */
    public Page query(Object lower, Object upper, int currentPageNo,
            OrderRule[] orderRules) throws EasyJException;

    /**
     * 用来查询数据，用于一些特殊条件的查询，如果指示简单的查询，推荐使用query(Object)
     * 
     * @param scmd
     *                SelectCommand 进行查询的查询条件。
     * @see <a href="../dao/command/SelectCommand.html">SelectCommand</a>
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合
     */
    public ArrayList query(SelectCommand scmd)
            throws easyJ.common.EasyJException;

    /**
     * 用来查询数据，用于一些特殊条件的查询，如果指示简单的查询，推荐使用query(Object)
     * 
     * @param scmd
     *                SelectCommand 进行查询的查询条件。
     * @see <a href="../dao/command/SelectCommand.html">SelectCommand</a>
     * @see <a href="../dao/command/Filter.html">Filter</a>
     * @param orderRules
     *                OrderRule[] 用来进行排序的OrderRule数组
     * @see <a href="../dao/OrderRule.html">OrderRule</a>
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合
     */
    public ArrayList query(SelectCommand scmd, OrderRule[] orderRules)
            throws easyJ.common.EasyJException;

    /**
     * 用来得到进行特殊更新的更新命令
     * 
     * @param clazz
     *                Class 需要更新的数据所对应的class
     * @throws EasyJException
     * @return UpdateCommand 更新命令。
     * @see <a href="../dao/command/UpdateCommand.html">UpdateCommand</a>
     * @see <a href="../dao/command/Filter.html">Filter</a>
     */
    public UpdateCommand getUpdateCommand(Class clazz)
            throws easyJ.common.EasyJException;

    /**
     * 用来得到进行特殊查询的查询命令
     * 
     * @param clazz
     *                Class
     * @throws EasyJException
     * @return SelectCommand 查询命令
     * @see <a href="../dao/command/SelectCommand.html">SelectCommand</a>
     * @see <a href="../dao/command/Filter.html">Filter</a>
     */
    public SelectCommand getSelectCommand(Class clazz)
            throws easyJ.common.EasyJException;

    /**
     * 根据查询命令看符合查询条件的数据有多少个。
     * 
     * @param scmd
     *                SelectCommand 要执行的查询命令
     * @throws EasyJException
     * @return Long 返回的条数
     */
    public Long getCount(SelectCommand scmd) throws EasyJException;

    /**
     * 根据查询的上下界看符合查询条件的数据有多少个。
     * 
     * @param lower
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @param upper
     *                Object 用来封装查询条件的上界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为40
     * @throws EasyJException
     * @return Long 返回的条数
     */
    public Long getCount(Object lower, Object upper) throws EasyJException;

    /**
     * 根据查询条件所处的对象查看符合查询条件的数据有多少个。
     * 
     * @param object
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @throws EasyJException
     * @return Long 返回的条数
     */
    public Long getCount(Object object) throws EasyJException;

}
