package cn.glor.xiaolun.app.common.page;

/**
 * Created by caosh on 2015/10/31.
 */
public class PageQuery {

    private int pageSize;

    private int pageNum;

    private PageQuery() {
    }

    public static PageQuery newPageQuery(int pageSize, int pageNum) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.pageSize = pageSize;
        pageQuery.pageNum = pageNum;
        return pageQuery;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getFirstResult() {
        return (pageNum - 1) * pageSize;
    }
}
